package com.bitheads.braincloud.services;

import com.bitheads.braincloud.client.BrainCloudClient;
import com.bitheads.braincloud.client.IServerCallback;
import com.bitheads.braincloud.client.ServiceName;
import com.bitheads.braincloud.client.ServiceOperation;
import com.bitheads.braincloud.comms.ServerCall;

import org.json.JSONException;
import org.json.JSONObject;

public class PlaybackStreamService {

    private enum Parameter {
        targetPlayerId,
        includeSharedData,
        playbackStreamId,
        eventData,
        summary,
        initiatingPlayerId
    }

    private BrainCloudClient _client;

    public PlaybackStreamService(BrainCloudClient client) {
        _client = client;
    }

    /**
     * Starts a stream
     *
     * Service Name - PlaybackStream
     * Service Operation - StartStream
     *
     * @param in_targetPlayerId    The player to start a stream with
     * @param in_includeSharedData Whether to include shared data in the stream
     * @param callback The callback.
     */
    public void startStream(
            String in_targetPlayerId,
            boolean in_includeSharedData,
            IServerCallback callback) {
        try {
            JSONObject data = new JSONObject();
            data.put(Parameter.targetPlayerId.name(), in_targetPlayerId);
            data.put(Parameter.includeSharedData.name(), in_includeSharedData);

            ServerCall sc = new ServerCall(ServiceName.playbackStream, ServiceOperation.START_STREAM, data, callback);
            _client.sendRequest(sc);
        } catch (JSONException je) {
        }
    }

    /**
     * Reads a stream
     *
     * Service Name - PlaybackStream
     * Service Operation - ReadStream
     *
     * @param in_playbackStreamId Identifies the stream to read
     * @param callback The callback.
     */
    public void readStream(
            String in_playbackStreamId,
            IServerCallback callback) {
        try {
            JSONObject data = new JSONObject();
            data.put(Parameter.playbackStreamId.name(), in_playbackStreamId);

            ServerCall sc = new ServerCall(ServiceName.playbackStream, ServiceOperation.READ_STREAM, data, callback);
            _client.sendRequest(sc);
        } catch (JSONException je) {
        }
    }

    /**
     * Ends a stream
     *
     * Service Name - PlaybackStream
     * Service Operation - EndStream
     *
     * @param in_playbackStreamId Identifies the stream to read
     * @param callback The callback.
     */
    public void endStream(
            String in_playbackStreamId,
            IServerCallback callback) {
        try {
            JSONObject data = new JSONObject();
            data.put(Parameter.playbackStreamId.name(), in_playbackStreamId);

            ServerCall sc = new ServerCall(ServiceName.playbackStream, ServiceOperation.END_STREAM, data, callback);
            _client.sendRequest(sc);
        } catch (JSONException je) {
        }
    }

    /**
     * Deletes a stream
     *
     * Service Name - PlaybackStream
     * Service Operation - DeleteStream
     *
     * @param in_playbackStreamId Identifies the stream to read
     * @param callback The callback.
     */
    public void deleteStream(
            String in_playbackStreamId,
            IServerCallback callback) {
        try {
            JSONObject data = new JSONObject();
            data.put(Parameter.playbackStreamId.name(), in_playbackStreamId);

            ServerCall sc = new ServerCall(ServiceName.playbackStream, ServiceOperation.DELETE_STREAM, data, callback);
            _client.sendRequest(sc);
        } catch (JSONException je) {
        }
    }

    /**
     * Adds a stream event
     *
     * Service Name - PlaybackStream
     * Service Operation - AddEvent
     *
     * @param in_playbackStreamId Identifies the stream to read
     * @param in_eventData Describes the event
     * @param in_summary Current summary data as of this event
     * @param callback The callback.
     */
    public void addEvent(
            String in_playbackStreamId,
            String in_eventData,
            String in_summary,
            IServerCallback callback) {
        try {
            JSONObject data = new JSONObject();
            data.put(Parameter.playbackStreamId.name(), in_playbackStreamId);
            data.put(Parameter.eventData.name(), new JSONObject(in_eventData));
            data.put(Parameter.summary.name(), new JSONObject(in_summary));

            ServerCall sc = new ServerCall(ServiceName.playbackStream, ServiceOperation.ADD_EVENT, data, callback);
            _client.sendRequest(sc);
        } catch (JSONException je) {
        }
    }

    /**
     * Gets stream summaries for initiating player
     *
     * Service Name - PlaybackStream
     * Service Operation - GetStreamSummariesForInitiatingPlayer
     *
     * @param in_initiatingPlayerId The player that started the stream
     * @param callback The callback.
     */
    public void getStreamSummariesForInitiatingPlayer(
            String in_initiatingPlayerId,
            IServerCallback callback) {
        try {
            JSONObject data = new JSONObject();
            data.put(Parameter.initiatingPlayerId.name(), in_initiatingPlayerId);

            ServerCall sc = new ServerCall(ServiceName.playbackStream, ServiceOperation.GET_STREAM_SUMMARIES_FOR_INITIATING_PLAYER, data, callback);
            _client.sendRequest(sc);
        } catch (JSONException je) {
        }
    }

    /**
     * Gets stream summaries for target player
     *
     * Service Name - PlaybackStream
     * Service Operation - GetStreamSummariesForTargetPlayer
     *
     * @param in_targetPlayerId The player that started the stream
     * @param callback The callback.
     */
    public void getStreamSummariesForTargetPlayer(
            String in_targetPlayerId,
            IServerCallback callback) {
        try {
            JSONObject data = new JSONObject();
            data.put(Parameter.targetPlayerId.name(), in_targetPlayerId);

            ServerCall sc = new ServerCall(ServiceName.playbackStream, ServiceOperation.GET_STREAM_SUMMARIES_FOR_TARGET_PLAYER, data, callback);
            _client.sendRequest(sc);
        } catch (JSONException je) {
        }
    }

}


