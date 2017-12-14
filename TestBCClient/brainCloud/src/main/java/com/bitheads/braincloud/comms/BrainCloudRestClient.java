package com.bitheads.braincloud.comms;

import com.bitheads.braincloud.client.BrainCloudClient;
import com.bitheads.braincloud.client.IEventCallback;
import com.bitheads.braincloud.client.IFileUploadCallback;
import com.bitheads.braincloud.client.IGlobalErrorCallback;
import com.bitheads.braincloud.client.INetworkErrorCallback;
import com.bitheads.braincloud.client.IRewardCallback;
import com.bitheads.braincloud.client.ReasonCodes;
import com.bitheads.braincloud.client.ServiceName;
import com.bitheads.braincloud.client.ServiceOperation;
import com.bitheads.braincloud.client.StatusCodes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;

public class BrainCloudRestClient implements Runnable {

    private static long NO_PACKET_EXPECTED = -1;

    private BrainCloudClient _client;
    private String _serverUrl;
    private String _uploadUrl;
    private String _appId;
    private String _secretKey;
    private String _sessionId;
    private long _packetId;
    private long _expectedPacketId;
    private boolean _isAuthenticated = false;
    private boolean _isInitialized = false;
    private boolean _loggingEnabled = false;
    private int _authenticationTimeoutMillis = 15000;
    private boolean _oldStyleStatusMessageErrorCallback = false;
    private boolean _cacheMessagesOnNetworkError = false;
    private long _lastSendTime;

    private int _uploadLowTransferTimeout = 120;
    private int _uploadLowTransferThreshold = 50;

    /// This flag is set when _cacheMessagesOnNetworkError is true
    /// and a network error occurs. It is reset when a call is made
    /// to either retryCachedMessages or flushCachedMessages
    private boolean _blockingQueue = false;
    private boolean _networkErrorCallbackReadyToBeSent = false;

    private IEventCallback _eventCallback = null;
    private IRewardCallback _rewardCallback = null;
    private IFileUploadCallback _fileUploadCallback = null;
    private IGlobalErrorCallback _globalErrorCallback = null;
    private INetworkErrorCallback _networkErrorCallback = null;

    private Thread _thread;
    private final Object _lock = new Object();

    private long _heartbeatIntervalMillis = 30000;
    private int _maxBundleSize = 10;
    private int _retryCount;
    private ArrayList<Integer> _packetTimeouts = new ArrayList<>();

    //kill switch
    private int _killSwitchThreshold = 11;
    private boolean _killSwitchEngaged;
    private int _killSwitchErrorCount;
    private String _killSwitchService;
    private String _killSwitchOperation;

    private LinkedBlockingQueue<ServerCall> _waitingQueue = new LinkedBlockingQueue<>();
    private LinkedBlockingQueue<ServerCall> _messageQueue = new LinkedBlockingQueue<>();
    private LinkedBlockingQueue<ServerCall> _bundleQueue = new LinkedBlockingQueue<>();
    private LinkedList<ServerCall> _networkErrorMessageQueue = new LinkedList<>();
    private LinkedList<ServerResponse> _serverResponses = new LinkedList<>();
    private LinkedList<JSONObject> _eventResponses = new LinkedList<>();
    private LinkedList<JSONObject> _rewardResponses = new LinkedList<>();
    private ArrayList<FileUploader> _fileUploads = new ArrayList<>();

    private int _statusCodeCache;
    private int _reasonCodeCache;
    private String _statusMessageCache;

    public BrainCloudRestClient(BrainCloudClient client) {
        _client = client;
        setPacketTimeoutsToDefault();
        resetErrorCache();
    }

    public void initialize(String serverUrl, String appId, String secretKey) {
        _packetId = 0;
        _expectedPacketId = NO_PACKET_EXPECTED;
        _serverUrl = serverUrl;
        _appId = appId;
        _secretKey = secretKey;
        _sessionId = "";
        _retryCount = 0;
        _isInitialized = true;

        String suffix = "/dispatcherv2";
        _uploadUrl = _serverUrl.endsWith(suffix) ? _serverUrl.substring(0, _serverUrl.length() - suffix.length()) : _serverUrl;
        _uploadUrl += "/uploader";

        if (_thread == null) {
            _thread = new Thread(this);
            _thread.start();
        }
    }

    public void addToQueue(ServerCall serverCall) {
        _waitingQueue.add(serverCall);
    }

    public void runCallbacks() {
        if (_blockingQueue) {
            if (_networkErrorCallbackReadyToBeSent) {
                if (_networkErrorCallback != null) {
                    _networkErrorCallback.networkError();
                }
                _networkErrorCallbackReadyToBeSent = false;
            }
            return;
        }


        synchronized (_lock) {
            //push waiting calls onto queue that the thread will use
            if(_messageQueue.peek() == null) {
                _messageQueue.addAll(_waitingQueue);
                _waitingQueue.clear();
            }

            ServerResponse response;
            while ((response = _serverResponses.poll()) != null) {
                ServerCall sc = response._serverCall;

                // handle response
                if (sc.getCallback() != null) {
                    if (response._isError) {
                        String jsonError;
                        if (_oldStyleStatusMessageErrorCallback) {
                            jsonError = response._statusMessage;
                        } else {
                            jsonError = response._data.toString();
                        }
                        sc.getCallback().serverError(sc.getServiceName(), sc.getServiceOperation(), response._statusCode, response._reasonCode, jsonError);

                        if (_globalErrorCallback != null) {
                            _globalErrorCallback.globalError(sc.getServiceName(), sc.getServiceOperation(), response._statusCode, response._reasonCode, jsonError);
                        }
                    } else {
                        sc.getCallback().serverCallback(sc.getServiceName(), sc.getServiceOperation(), response._data);
                    }
                }
            }

            JSONObject rewards;
            while ((rewards = _rewardResponses.poll()) != null) {
                _rewardCallback.rewardCallback(rewards);
            }

            JSONObject events;
            while ((events = _eventResponses.poll()) != null) {
                _eventCallback.eventsReceived(events);
            }

            runFileUploadCallbacks();
        }
    }

    private void runFileUploadCallbacks() {
        Iterator<FileUploader> iter = _fileUploads.iterator();
        while (iter.hasNext()) {
            FileUploader temp = iter.next();
            if (temp.getStatus() == FileUploader.FileUploaderStatus.CompleteSuccess) {
                if (_fileUploadCallback != null)
                    _fileUploadCallback.fileUploadCompleted(temp.getUploadId(), temp.getResponse());
                LogString("Upload success: " + temp.getUploadId() + " | " + temp.getStatusCode() + "\n" + temp.getResponse());
                iter.remove();
            } else if (temp.getStatus() == FileUploader.FileUploaderStatus.CompleteFailed) {
                if (_fileUploadCallback != null)
                    _fileUploadCallback.fileUploadFailed(temp.getUploadId(), temp.getStatusCode(), temp.getReasonCode(), temp.getResponse());
                LogString("Upload failed: " + temp.getUploadId() + " | " + temp.getStatusCode() + "\n" + temp.getResponse());
                iter.remove();
            }
        }
    }

    public int getUploadLowTransferRateTimeout() {
        return _uploadLowTransferTimeout;
    }

    public void setUploadLowTransferRateTimeout(int timeoutSecs) {
        _uploadLowTransferTimeout = timeoutSecs;
    }

    public int getUploadLowTransferRateThreshold() {
        return _uploadLowTransferThreshold;
    }

    public void setUploadLowTransferRateThreshold(int bytesPerSec) {
        _uploadLowTransferThreshold = bytesPerSec;
    }

    public void cancelUpload(String uploadFileId) {
        FileUploader uploader = getFileUploader(uploadFileId);
        if (uploader != null) uploader.cancel();
    }

    public double getUploadProgress(String uploadFileId) {
        FileUploader uploader = getFileUploader(uploadFileId);
        if (uploader != null) return uploader.getProgress();
        else return -1;
    }

    public long getUploadBytesTransferred(String uploadFileId) {
        FileUploader uploader = getFileUploader(uploadFileId);
        if (uploader != null) return uploader.getBytesTransferred();
        else return -1;
    }

    public long getUploadTotalBytesToTransfer(String uploadFileId) {
        FileUploader uploader = getFileUploader(uploadFileId);
        if (uploader != null) return uploader.getTotalBytesToTransfer();
        else return -1;
    }

    private FileUploader getFileUploader(String uploadId) {
        for (FileUploader temp : _fileUploads) {
            if (temp.getUploadId().equals(uploadId)) return temp;
        }
        LogString("GetUploadProgress could not find upload ID " + uploadId);
        return null;
    }

    public void resetCommunication() {
        synchronized (_lock) {
            _waitingQueue.clear();
            _messageQueue.clear();
            _bundleQueue.clear();
            _networkErrorMessageQueue.clear();
            _serverResponses.clear();
            _eventResponses.clear();
            _rewardResponses.clear();
            _isAuthenticated = false;
            _sessionId = "";
            _blockingQueue = false;
            _networkErrorCallbackReadyToBeSent = false;

            _client.getAuthenticationService().clearSavedProfileId();
        }
    }

    public void registerEventCallback(IEventCallback callback) {
        synchronized (_lock) {
            _eventCallback = callback;
        }
    }

    public void deregisterEventCallback() {
        synchronized (_lock) {
            _eventCallback = null;
        }
    }

    public void registerRewardCallback(IRewardCallback in_rewardCallback) {
        synchronized (_lock) {
            _rewardCallback = in_rewardCallback;
        }
    }

    public void deregisterRewardCallback() {
        synchronized (_lock) {
            _rewardCallback = null;
        }
    }

    /**
     * Registers a file upload callback handler to listen for status updates on uploads
     *
     * @param fileUploadCallback The file upload callback handler.
     */
    public void registerFileUploadCallback(IFileUploadCallback fileUploadCallback) {
        _fileUploadCallback = fileUploadCallback;
    }

    /**
     * Deregisters the file upload callback
     */
    public void deregisterFileUploadCallback() {
        _fileUploadCallback = null;
    }

    public void registerGlobalErrorCallback(IGlobalErrorCallback in_globalErrorCallback) {
        synchronized (_lock) {
            _globalErrorCallback = in_globalErrorCallback;
        }
    }

    public void deregisterGlobalErrorCallback() {
        synchronized (_lock) {
            _globalErrorCallback = null;
        }
    }

    public void registerNetworkErrorCallback(INetworkErrorCallback in_networkErrorCallback) {
        synchronized (_lock) {
            _networkErrorCallback = in_networkErrorCallback;
        }
    }

    public void deregisterNetworkErrorCallback() {
        synchronized (_lock) {
            _networkErrorCallback = null;
        }
    }

    public ArrayList<Integer> getPacketTimeouts() {
        return _packetTimeouts;
    }

    public void setPacketTimeouts(ArrayList<Integer> in_packetTimeouts) {
        _packetTimeouts = in_packetTimeouts;
    }

    public void setPacketTimeoutsToDefault() {
        _packetTimeouts = new ArrayList<>();
        _packetTimeouts.add(10);
        _packetTimeouts.add(10);
        _packetTimeouts.add(10);
    }

    public int getAuthenticationPacketTimeout() {
        return _authenticationTimeoutMillis / 1000;
    }

    public void setAuthenticationPacketTimeout(int timeoutSecs) {
        if (timeoutSecs > 0) {
            _authenticationTimeoutMillis = timeoutSecs * 1000;
        }
    }

    public void setOldStyleStatusMessageErrorCallback(boolean in_enabled) {
        _oldStyleStatusMessageErrorCallback = in_enabled;
    }

    public void enableNetworkErrorMessageCaching(boolean in_enabled) {
        synchronized (_lock) {
            _cacheMessagesOnNetworkError = in_enabled;
        }
    }

    /**
     * @deprecated Use getAppId instead - removal after September 1 2017
     */
    @Deprecated
    public String getGameId() {
        return _appId;
    }

    public String getAppId() {
        return _appId;
    }

    public String getSessionId() {
        return _sessionId;
    }

    public void setSessionId(String sessionId) {
        _sessionId = sessionId;
    }


    public long getHeartbeatInterval() {
        return _heartbeatIntervalMillis;
    }

    public void setHeartbeatInterval(long heartbeatInterval) {
        _heartbeatIntervalMillis = heartbeatInterval;
    }

    public boolean isAuthenticated() {
        return _isAuthenticated;
    }

    public void setAuthenticated() {
        _isAuthenticated = true;
    }

    public boolean isInitialized() {
        return _isInitialized;
    }

    public boolean getLoggingEnabled() {
        return _loggingEnabled;
    }

    public void enableLogging(boolean isEnabled) {
        _loggingEnabled = isEnabled;
    }

    public void retryCachedMessages() {
        synchronized (_lock) {
            if (!_blockingQueue) {
                return;
            }
            --_packetId;
            _serverResponses.clear();
            _blockingQueue = false;
            _networkErrorCallbackReadyToBeSent = false;
        }
    }

    public void flushCachedMessages(boolean in_sendApiErrorCallbacks) {
        synchronized (_lock) {
            if (!_blockingQueue) {
                return;
            }

            if (!in_sendApiErrorCallbacks) {
                _serverResponses.clear();
            }
            // otherwise serverResponses will be populated and callbacks will be issued
            // from next runCallbacks

            _networkErrorMessageQueue.clear();
            _blockingQueue = false;
            _networkErrorCallbackReadyToBeSent = false;
        }
    }

    public void insertEndOfMessageBundleMarker() {
        ServerCall sc = new ServerCall(null, null, null, null);
        sc.setEndOfBundleMarker(true);
        addToQueue(sc);
    }

    private boolean shouldRetryPacket() {
        for (ServerCall serverCall : _bundleQueue) {
            if (serverCall != null) {
                if (serverCall.getServiceName() == ServiceName.authenticationV2
                        && serverCall.getServiceOperation() == ServiceOperation.AUTHENTICATE) {
                    return false;
                }
            }
        }
        return true;
    }

    private int getRetryTimeoutMillis(int retryAttempt) {
        if (!shouldRetryPacket()) {
            return _authenticationTimeoutMillis;
        }

        return _packetTimeouts.get(retryAttempt >= _packetTimeouts.size() ? _packetTimeouts.size() - 1 : retryAttempt) * 1000;
    }

    private int getMaxSendAttempts() {
        if (!shouldRetryPacket()) {
            return 1;
        }
        return _packetTimeouts.size();
    }

    public void run() {
        while (!_thread.isInterrupted()) {
            if (!_isInitialized || _blockingQueue) {
                try {
                    Thread.sleep(400);
                } catch (InterruptedException ignored) {
                }
            } else {
                if (_networkErrorMessageQueue.size() > 0) {
                    _bundleQueue.addAll(_networkErrorMessageQueue);
                    _networkErrorMessageQueue.clear();
                } else {
                    fillBundle();
                }

                if (_bundleQueue.size() > 0 && _isInitialized) {
                    boolean isAuth = _isAuthenticated;
                    if (!isAuth || _bundleQueue.size() > 1) {
                        Iterator<ServerCall> iter = _bundleQueue.iterator();
                        while (iter.hasNext()) {
                            ServerCall serverCall = iter.next();
                            if (serverCall.getServiceOperation() == ServiceOperation.AUTHENTICATE ||
                                    serverCall.getServiceOperation() == ServiceOperation.RESET_EMAIL_PASSWORD) {
                                isAuth = true;
                            } else if (serverCall.getServiceName() == ServiceName.heartbeat) {
                                iter.remove();
                            }
                        }
                    }

                    if(!_killSwitchEngaged) {
                        if (!isAuth) {
                            fakeErrorResponse(_statusCodeCache, _reasonCodeCache, _statusMessageCache);
                        } else {
                            _retryCount = 0;
                            for (; ; ) {
                                // do this before as retry count will be incremented inside sendBundle()
                                long timeoutMillis = System.currentTimeMillis() + getRetryTimeoutMillis(_retryCount);

                                if (sendBundle()) {
                                    break;
                                }

                                long endMillis = System.currentTimeMillis();
                                if (endMillis < timeoutMillis) {
                                    try {
                                        Thread.sleep(timeoutMillis - endMillis);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                    else
                    {
                        fakeErrorResponse(StatusCodes.CLIENT_NETWORK_ERROR, ReasonCodes.CLIENT_DISABLED,
                                "Client has been disabled due to repeated errors from a single API call");
                    }
                }
            }
        }
    }

    private void fakeErrorResponse(int statusCode, int reasonCode, String statusMessage)
    {
        if (_loggingEnabled) {
            try {
                String body = getDataString();
                JSONObject jlog = new JSONObject(body);
                LogString("OUTGOING" + (_retryCount > 0 ? " retry(" + _retryCount + "): " : ": ") + jlog.toString(2));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        fillWithError(statusCode, reasonCode, statusMessage);

        if (_loggingEnabled) {
            ArrayList<JSONObject> responses = new ArrayList<>(_serverResponses.size());

            for (ServerResponse response : _serverResponses) {
                responses.add(response._data);
            }

            try {
                JSONObject responseBody = new JSONObject();
                responseBody.put("packetId", _expectedPacketId);
                responseBody.put("responses", responses);
                LogString("INCOMING (" + 200 + "): " + responseBody.toString(2));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void LogString(String s) {
        if (_loggingEnabled) {
            // for now use System.out as unit tests do not support android.util.log class
            System.out.println("#BCC " + s);
        }
    }

    private void fillBundle() {
        //waiting for callbacks to be run
        if(_serverResponses.peek() != null) return;

        ServerCall firstCall = _messageQueue.peek();

        //check for heartbeat
        if(firstCall == null) {
            if (System.currentTimeMillis() - _lastSendTime > _heartbeatIntervalMillis && _isAuthenticated) {
                ServerCall serverCall = new ServerCall(ServiceName.heartbeat, ServiceOperation.READ, null, null);
                _bundleQueue.add(serverCall);
                return;
            }
            else return;
        }

        // Handle auth first and alone
        Iterator<ServerCall> it = _messageQueue.iterator();
        while(it.hasNext()){
            ServerCall call = it.next();
            if(call.isEndOfBundleMarker()) break;

            if(call.getServiceOperation() == ServiceOperation.AUTHENTICATE) {
                it.remove();
                _bundleQueue.add(call);
                return;
            }
        }

        //fill bundle
        while (_bundleQueue.size() < _maxBundleSize) {
            ServerCall serverCall = _messageQueue.poll();

            if (serverCall == null)
                return;

            if (serverCall.isEndOfBundleMarker())
                return;

            _bundleQueue.add(serverCall);
        }
    }

    private void fillWithError(int statusCode, int reasonCode, String statusMessage) {
        synchronized (_lock) {
            JSONObject jsonError = new JSONObject();
            try {
                jsonError.put("status", statusCode);
                jsonError.put("reason_code", reasonCode);
                jsonError.put("severity", "ERROR");
                jsonError.put("status_message", statusMessage);
            } catch (JSONException je) {
                je.printStackTrace();
            }

            for (ServerCall serverCall : _bundleQueue) {
                ServerResponse response = new ServerResponse();
                response._serverCall = serverCall;
                response._isError = true;
                response._statusCode = statusCode;
                response._reasonCode = reasonCode;
                response._statusMessage = statusMessage;
                response._data = jsonError;

                _serverResponses.push(response);
            }
            _bundleQueue.clear();
        }
    }

    private boolean sendBundle() {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(_serverUrl).openConnection();
            connection.setConnectTimeout(getRetryTimeoutMillis(_retryCount));
            connection.setReadTimeout(getRetryTimeoutMillis(_retryCount));
            connection.setDoOutput(true);

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");

            String body = getDataString();

            if (_secretKey.length() > 0) {
                connection.setRequestProperty("X-SIG", getSignature(body));
            }

            connection.setRequestProperty("X-APPID", _appId);

            connection.setRequestProperty("charset", "utf-8");
            byte[] postData = body.getBytes("UTF-8");
            connection.setRequestProperty("Content-Length", Integer.toString(postData.length));

            // to avoid taking the json parsing hit even when logging is disabled
            if (_loggingEnabled) {
                try {
                    JSONObject jlog = new JSONObject(body);
                    LogString("OUTGOING" + (_retryCount > 0 ? " retry(" + _retryCount + "): " : ": ") + jlog.toString(2));
                } catch (JSONException e) {
                    // should never happen
                    e.printStackTrace();
                }
            }

            _lastSendTime = System.currentTimeMillis();

            connection.connect();
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            try {
                wr.write(postData);
            } finally {
                wr.close();
            }

            // Get server response
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            String responseBody = builder.toString();

            // to avoid taking the json parsing hit even when logging is disabled
            if (_loggingEnabled) {
                try {
                    JSONObject jlog = new JSONObject(responseBody);
                    LogString("INCOMING (" + connection.getResponseCode() + "): " + jlog.toString(2));
                } catch (JSONException e) {
                    // in case we get a non-json response from the server
                    LogString("INCOMING (" + connection.getResponseCode() + "): " + responseBody);
                }
            }

            // non-200 status, retry
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK || responseBody.length() == 0) {
                _retryCount++;
                if (_retryCount < getMaxSendAttempts()) {
                    // allow retry of this packet
                    return false;
                }

                if (_cacheMessagesOnNetworkError) {
                    _networkErrorMessageQueue.clear();
                    _networkErrorMessageQueue.addAll(_bundleQueue);
                    _networkErrorCallbackReadyToBeSent = true;
                    _blockingQueue = true;
                }
                fillWithError(StatusCodes.CLIENT_NETWORK_ERROR, ReasonCodes.CLIENT_NETWORK_ERROR_TIMEOUT, responseBody);
                return true;
            }

            JSONObject root;
            root = new JSONObject(responseBody);

            long receivedPacketId = root.getLong("packetId");
            if (_expectedPacketId == NO_PACKET_EXPECTED || receivedPacketId != _expectedPacketId) {
                // this is an old packet so ignore it
                LogString("Received packet id " + receivedPacketId + " but expected packet id " + _expectedPacketId);
                return true;
            }
            _expectedPacketId = NO_PACKET_EXPECTED;

            handleBundle(root);
            _bundleQueue.clear();
        } catch (Exception e) {
            e.printStackTrace();
            if (_cacheMessagesOnNetworkError) {
                _networkErrorMessageQueue.clear();
                _networkErrorMessageQueue.addAll(_bundleQueue);
                _networkErrorCallbackReadyToBeSent = true;
                _blockingQueue = true;
            }
            fillWithError(StatusCodes.CLIENT_NETWORK_ERROR, ReasonCodes.CLIENT_NETWORK_ERROR_TIMEOUT, "Network error");
        }

        return true;
    }

    private String getDataString() throws JSONException {
        JSONArray messages = new JSONArray();

        for (ServerCall serverCall : _bundleQueue) {
            messages.put(serverCall.getPayload());
        }

        _expectedPacketId = _packetId++;
        JSONObject allMessages = new JSONObject();
        allMessages.put("messages", messages);
        allMessages.put("gameId", _appId);
        allMessages.put("sessionId", _sessionId);
        allMessages.put("packetId", _expectedPacketId);

        return allMessages.toString() + "\r\n\r\n";
    }

    private String getSignature(String body) throws NoSuchAlgorithmException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(body.getBytes("UTF-8"));
            messageDigest.update(_secretKey.getBytes("UTF-8"));

            return toHexString(messageDigest.digest());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private void resetErrorCache() {
        _statusCodeCache = StatusCodes.FORBIDDEN;
        _reasonCodeCache = ReasonCodes.NO_SESSION;
        _statusMessageCache = "No session";
    }

    private void updateKillSwitch(int statusCode, String service, String operation)
    {
        if (statusCode == StatusCodes.CLIENT_NETWORK_ERROR) return;

        if (_killSwitchService == null)
        {
            _killSwitchService = service;
            _killSwitchOperation = operation;
            _killSwitchErrorCount++;
        }
        else if (service == _killSwitchService && operation == _killSwitchOperation)
            _killSwitchErrorCount++;

        if (!_killSwitchEngaged && _killSwitchErrorCount >= _killSwitchThreshold)
        {
            _killSwitchEngaged = true;
            LogString("Client disabled due to repeated errors from a single API call: " + service + " | " + operation);
        }
    }

    private void resetKillSwitch()
    {
        _killSwitchErrorCount = 0;
        _killSwitchService = null;
        _killSwitchOperation = null;
    }

    private void handleBundle(JSONObject root) throws JSONException {
        JSONArray messages = root.getJSONArray("responses");

        synchronized (_lock) {
            for (int i = 0, ilen = messages.length(); i < ilen; ++i) {
                ServerCall sc = _bundleQueue.poll();
                if (sc != null) {
                    JSONObject message = messages.getJSONObject(i);
                    int status = message.getInt("status");
                    ServerResponse serverResponse = new ServerResponse();
                    serverResponse._serverCall = sc;
                    serverResponse._statusCode = status;

                    if (status == 200) {
                        resetKillSwitch();

                        if (sc.getServiceName().equals(ServiceName.authenticationV2)
                                && sc.getServiceOperation().equals(ServiceOperation.AUTHENTICATE)) {
                            JSONObject data = message.getJSONObject("data");
                            String sessionId = data.getString("sessionId");
                            String profileId = data.getString("profileId");
                            _sessionId = sessionId;
                            _isAuthenticated = true;
                            resetErrorCache();
                            _client.getAuthenticationService().setProfileId(profileId);

                            long sessionExpiry = data.getLong("playerSessionExpiry");
                            _heartbeatIntervalMillis = (long)(sessionExpiry * 1000 * 0.85);
                            _maxBundleSize = data.getInt("maxBundleMsgs");

                            if(data.has("maxKillCount"))
                                _killSwitchThreshold = data.getInt("maxKillCount");

                        } else if (sc.getServiceName().equals(ServiceName.playerState)
                                && sc.getServiceOperation().equals(ServiceOperation.LOGOUT)) {
                            _isAuthenticated = false;
                            _sessionId = "";
                            resetErrorCache();
                            _client.getAuthenticationService().clearSavedProfileId();
                        } else if (sc.getServiceName().equals(ServiceName.file)
                                && sc.getServiceOperation().equals(ServiceOperation.PREPARE_USER_UPLOAD)) {
                            JSONObject data = message.getJSONObject("data").getJSONObject("fileDetails");
                            String uploadId = data.getString("uploadId");
                            String localPath = data.getString("localPath");
                            _fileUploads.add(new FileUploader(uploadId, localPath, _uploadUrl, _sessionId,
                                    _uploadLowTransferTimeout, _uploadLowTransferThreshold));
                        }

                        serverResponse._isError = false;
                        serverResponse._data = message;

                        // handle reward data if present
                        if (_rewardCallback != null) {
                            try {
                                JSONObject data = message.getJSONObject("data");
                                JSONObject rewards = null;
                                if (sc.getServiceName().equals(ServiceName.authenticationV2)
                                        && sc.getServiceOperation().equals(ServiceOperation.AUTHENTICATE)) {
                                    JSONObject outerRewards = data.optJSONObject("rewards");
                                    if (outerRewards != null) {
                                        JSONObject innerRewards = outerRewards.optJSONObject("rewards");
                                        if (innerRewards != null) {
                                            if (innerRewards.length() > 0) {
                                                rewards = outerRewards;
                                            }
                                        }
                                    }
                                } else if ((sc.getServiceName().equals(ServiceName.playerStatistics)
                                        && sc.getServiceOperation().equals(ServiceOperation.UPDATE))
                                        || (sc.getServiceName().equals(ServiceName.playerStatisticsEvent)
                                        && (sc.getServiceOperation().equals(ServiceOperation.TRIGGER)
                                        || (sc.getServiceOperation().equals(ServiceOperation.TRIGGER_MULTIPLE))))) {
                                    JSONObject innerRewards = data.optJSONObject("rewards");
                                    if (innerRewards != null) {
                                        if (innerRewards.length() > 0) {
                                            rewards = data;
                                        }
                                    }
                                }

                                if (rewards != null) {
                                    JSONObject apiReward = new JSONObject();
                                    apiReward.put("service", sc.getServiceName());
                                    apiReward.put("operation", sc.getServiceOperation());
                                    apiReward.put("rewards", rewards);

                                    JSONObject callbackObj = new JSONObject();
                                    JSONArray apiRewards = new JSONArray();
                                    apiRewards.put(apiReward);
                                    callbackObj.put("apiRewards", apiRewards);

                                    _rewardResponses.addLast(callbackObj);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        int reasonCode = 0;
                        if (!message.isNull("reason_code")) {
                            reasonCode = message.getInt("reason_code");
                        }
                        String statusMessage = message.getString("status_message");

                        if (reasonCode == ReasonCodes.USER_SESSION_EXPIRED
                                || reasonCode == ReasonCodes.NO_SESSION
                                || reasonCode == ReasonCodes.USER_SESSION_LOGGED_OUT) {
                            _isAuthenticated = false;
                            _sessionId = "";
                            _statusCodeCache = status;
                            _reasonCodeCache = reasonCode;
                            _statusMessageCache = statusMessage;
                        } else if (sc.getServiceOperation() == ServiceOperation.LOGOUT) {
                            if (reasonCode == ReasonCodes.CLIENT_NETWORK_ERROR_TIMEOUT) {
                                _isAuthenticated = false;
                                _sessionId = "";
                            }
                        }

                        serverResponse._isError = true;
                        serverResponse._reasonCode = reasonCode;
                        serverResponse._statusMessage = statusMessage;
                        serverResponse._data = message;

                        updateKillSwitch(status, sc.getServiceName().toString(), sc.getServiceOperation().toString());
                    }

                    _serverResponses.addLast(serverResponse);
                } else {
                    LogString("missing server call for json response: " + messages.toString());
                }
            }

            if (!root.isNull("events") && _eventCallback != null) {
                try {
                    JSONArray events = root.getJSONArray("events");
                    JSONObject eventsAsJson = new JSONObject();
                    eventsAsJson.put("events", events);
                    _eventResponses.addLast(eventsAsJson);
                } catch (JSONException je) {
                    je.printStackTrace();
                }
            }
        }
    }

    /**
     * Converts the specified byte array into hexadecimal string representation.
     *
     * @param bytes Byte array.
     * @return Hexadecimal string representation of the input argument.
     */
    private String toHexString(byte[] bytes) {
        StringBuilder buffer = new StringBuilder();

        for (byte aByte : bytes) {
            String hex = Integer.toHexString(0xFF & aByte);

            if (hex.length() == 1) {
                buffer.append("0");
            }

            buffer.append(hex);
        }

        return buffer.toString();
    }
}
