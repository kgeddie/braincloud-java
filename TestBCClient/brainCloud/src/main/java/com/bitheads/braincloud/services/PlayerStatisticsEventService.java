package com.bitheads.braincloud.services;

import com.bitheads.braincloud.client.BrainCloudClient;
import com.bitheads.braincloud.client.IServerCallback;
import com.bitheads.braincloud.client.ServiceName;
import com.bitheads.braincloud.client.ServiceOperation;
import com.bitheads.braincloud.comms.ServerCall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PlayerStatisticsEventService {

    private enum Parameter {
        eventName,
        eventMultiplier,
        events
    }

    private BrainCloudClient _client;

    public PlayerStatisticsEventService(BrainCloudClient client) {
        _client = client;
    }

    /**
     * @deprecated Use triggerUserStatsEvent() instead - Removal after September 1 2017
     */
    public void triggerPlayerStatisticsEvent(String in_eventName, int in_eventMultiplier, IServerCallback callback) {
        try {
            JSONObject data = new JSONObject();
            data.put(Parameter.eventName.name(), in_eventName);
            data.put(Parameter.eventMultiplier.name(), in_eventMultiplier);

            ServerCall sc = new ServerCall(ServiceName.playerStatisticsEvent, ServiceOperation.TRIGGER, data, callback);
            _client.sendRequest(sc);

        } catch (JSONException ignored) {
        }
    }

    /**
     * Trigger an event server side that will increase the user statistics.
     * This may cause one or more awards to be sent back to the user -
     * could be achievements, experience, etc. Achievements will be sent by this
     * client library to the appropriate awards service (Apple Game Center, etc).
     *
     * This mechanism supercedes the PlayerStatisticsService API methods, since
     * PlayerStatisticsService API method only update the raw statistics without
     * triggering the rewards.
     *
     * Service Name - PlayerStatisticsEvent
     * Service Operation - Trigger
     *
     * @see PlayerStatisticsService
     */
    public void triggerUserStatsEvent(String in_eventName, int in_eventMultiplier, IServerCallback callback) {
        try {
            JSONObject data = new JSONObject();
            data.put(Parameter.eventName.name(), in_eventName);
            data.put(Parameter.eventMultiplier.name(), in_eventMultiplier);

            ServerCall sc = new ServerCall(ServiceName.playerStatisticsEvent, ServiceOperation.TRIGGER, data, callback);
            _client.sendRequest(sc);

        } catch (JSONException ignored) {
        }
    }

    /**
     * @deprecated Use triggerUserStatsEvents() instead - Removal after September 1 2017
     */
    public void triggerPlayerStatisticsEvents(String in_jsonData, IServerCallback callback) {
        try {
            JSONObject data = new JSONObject();
            JSONArray jsonData = new JSONArray(in_jsonData);
            data.put(Parameter.events.name(), jsonData);

            ServerCall sc = new ServerCall(ServiceName.playerStatisticsEvent, ServiceOperation.TRIGGER_MULTIPLE, data, callback);
            _client.sendRequest(sc);
        } catch (JSONException ignored) {
        }
    }

    /**
     * See documentation for TriggerPlayerStatisticsEvent for more
     * documentation.
     *
     * Service Name - PlayerStatisticsEvent
     * Service Operation - TriggerMultiple
     *
     * @param in_jsonData
     *   [
     *     {
     *       "eventName": "event1",
     *       "eventMultiplier": 1
     *     },
     *     {
     *       "eventName": "event2",
     *       "eventMultiplier": 1
     *     }
     *   ]
     */
    public void triggerUserStatsEvents(String in_jsonData, IServerCallback callback) {
        try {
            JSONObject data = new JSONObject();
            JSONArray jsonData = new JSONArray(in_jsonData);
            data.put(Parameter.events.name(), jsonData);

            ServerCall sc = new ServerCall(ServiceName.playerStatisticsEvent, ServiceOperation.TRIGGER_MULTIPLE, data, callback);
            _client.sendRequest(sc);
        } catch (JSONException ignored) {
        }
    }
}
