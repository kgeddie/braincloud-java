package com.bitheads.braincloud.services;

import com.bitheads.braincloud.client.BrainCloudClient;
import com.bitheads.braincloud.client.IServerCallback;
import com.bitheads.braincloud.client.ServiceName;
import com.bitheads.braincloud.client.ServiceOperation;
import com.bitheads.braincloud.comms.ServerCall;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Deprecated - Removal after June 21 2016
 */
@Deprecated
public class TwitterService {

    private BrainCloudClient _client;

    public TwitterService(BrainCloudClient client) {
        _client = client;
    }

    public enum Parameter {
        token,
        verifier,
        secret,
        tweet,
        pic
    }

    /**
     * Deprecated - Removal after June 21 2016
     */
    @Deprecated
    public void authorizeTwitter(IServerCallback callback) {
        ServerCall sc = new ServerCall(ServiceName.twitter, ServiceOperation.AUTHENTICATE, null, callback);
        _client.sendRequest(sc);
    }

    /**
     * Deprecated - Removal after June 21 2016
     */
    @Deprecated
    public void verifyTwitter(String in_token, String in_verifier, IServerCallback callback) {
        try {
            JSONObject data = new JSONObject();

            data.put(Parameter.token.name(), in_token);
            data.put(Parameter.verifier.name(), in_verifier);

            ServerCall sc = new ServerCall(ServiceName.twitter, ServiceOperation.VERIFY, data, callback);
            _client.sendRequest(sc);
        } catch (JSONException je) {
        }
    }

    /**
     * Deprecated - Removal after June 21 2016
     */
    @Deprecated
    public void tweet(String in_token, String in_secret, String in_tweet, String in_picture, IServerCallback callback) {
        try {
            JSONObject data = new JSONObject();

            data.put(Parameter.token.name(), in_token);
            data.put(Parameter.secret.name(), in_secret);
            data.put(Parameter.tweet.name(), in_tweet);
            if (StringUtil.IsOptionalParameterValid(in_picture)) {
                data.put(Parameter.pic.name(), in_picture);
            }

            ServerCall sc = new ServerCall(ServiceName.twitter, ServiceOperation.TWEET, data, callback);
            _client.sendRequest(sc);
        } catch (JSONException je) {
        }
    }
}
