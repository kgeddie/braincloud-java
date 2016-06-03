package com.bitheads.braincloud.services;

import com.bitheads.braincloud.client.BrainCloudClient;
import com.bitheads.braincloud.client.IServerCallback;
import com.bitheads.braincloud.client.ServiceName;
import com.bitheads.braincloud.client.ServiceOperation;
import com.bitheads.braincloud.comms.ServerCall;

import org.json.JSONException;
import org.json.JSONObject;

public class EventService {

    private enum Parameter {
        toId,
        eventType,
        eventData,
        recordLocally,
        fromId,
        eventId,
        includeIncomingEvents,
        includeSentEvents
    }

    private BrainCloudClient _client;

    public EventService(BrainCloudClient client) {
        _client = client;
    }

    /**
     * Sends an event to the designated player id with the attached json data.
     * Any events that have been sent to a player will show up in their incoming
     * event mailbox. If the in_recordLocally flag is set to true, a copy of
     * this event (with the exact same event id) will be stored in the sending
     * player's "sent" event mailbox.
     *
     * Note that the list of sent and incoming events for a player is returned
     * in the "ReadPlayerState" call (in the BrainCloudPlayer module).
     *
     * Service Name - Event Service Operation - Send
     *
     * @param in_toPlayerId The id of the player who is being sent the event
     * @param in_eventType The user-defined type of the event.
     * @param in_jsonEventData The user-defined data for this event encoded in
     *            JSON.
     * @param in_recordLocally If true, a copy of this event will be saved in
     *            the user's sent events mailbox.
     * @param callback The callback.
     */
    public void sendEvent(String in_toPlayerId, String in_eventType, String in_jsonEventData, boolean in_recordLocally, IServerCallback callback) {
        try {
            JSONObject data = new JSONObject();

            data.put(Parameter.toId.name(), in_toPlayerId);
            data.put(Parameter.eventType.name(), in_eventType);

            JSONObject jsonData = new JSONObject(in_jsonEventData);
            data.put(Parameter.eventData.name(), jsonData);

            data.put(Parameter.recordLocally.name(), in_recordLocally);

            ServerCall sc = new ServerCall(ServiceName.event, ServiceOperation.SEND, data, callback);
            _client.sendRequest(sc);

        } catch (JSONException e) {
        }
    }

    /**
     * Updates an event in the player's incoming event mailbox.
     *
     * Service Name - Event Service Operation - UpdateEventData
     *
     * @param in_fromPlayerId The id of the player who sent the event
     * @param in_eventId The event id
     * @param in_jsonEventData The user-defined data for this event encoded in
     *            JSON.
     * @param callback The  callback.
     */
    public void updateIncomingEventData(String in_fromPlayerId, long in_eventId, String in_jsonEventData, IServerCallback callback) {
        try {
            JSONObject data = new JSONObject();
            data.put(Parameter.fromId.name(), in_fromPlayerId);
            data.put(Parameter.eventId.name(), in_eventId);

            JSONObject jsonData = new JSONObject(in_jsonEventData);
            data.put(Parameter.eventData.name(), jsonData);

            ServerCall sc = new ServerCall(ServiceName.event, ServiceOperation.UPDATE_EVENT_DATA, data, callback);
            _client.sendRequest(sc);

        } catch (JSONException e) {
        }
    }

    /**
     * Delete an event out of the player's incoming mailbox.
     *
     * Service Name - Event Service Operation - DeleteIncoming
     *
     * @param in_fromPlayerId The id of the player who sent the event
     * @param in_eventId The event id
     * @param callback The callback.
     */
    public void deleteIncomingEvent(String in_fromPlayerId, long in_eventId, IServerCallback callback) {
        try {
            JSONObject data = new JSONObject();
            data.put(Parameter.fromId.name(), in_fromPlayerId);
            data.put(Parameter.eventId.name(), in_eventId);

            ServerCall sc = new ServerCall(ServiceName.event, ServiceOperation.DELETE_INCOMING, data, callback);
            _client.sendRequest(sc);

        } catch (JSONException e) {
        }
    }

    /**
     * Delete an event from the player's sent mailbox.
     *
     * Note that only events sent with the "recordLocally" flag set to true will
     * be added to a player's sent mailbox.
     *
     * Service Name - Event Service Operation - DeleteSent
     *
     * @param in_toPlayerId The id of the player who is being sent the event
     * @param in_eventId The event id
     * @param callback The callback.
     */
    public void deleteSentEvent(String in_toPlayerId, long in_eventId, IServerCallback callback) {
        try {
            JSONObject data = new JSONObject();
            data.put(Parameter.toId.name(), in_toPlayerId);
            data.put(Parameter.eventId.name(), in_eventId);

            ServerCall sc = new ServerCall(ServiceName.event, ServiceOperation.DELETE_SENT, data, callback);
            _client.sendRequest(sc);
        } catch (JSONException e) {
        }
    }

    /**
     * Get the events currently queued for the player.
     *
     * Service Name - Event
     * Service Operation - GetEvents
     *
     * @param in_includeIncomingEvents Get events sent to the player
     * @param in_includeSentEvents Get events sent from the player
     * @param in_callback The method to be invoked when the server response is received
     */
    public void getEvents(boolean in_includeIncomingEvents, boolean in_includeSentEvents, IServerCallback in_callback) {
        try {
            JSONObject data = new JSONObject();
            data.put(Parameter.includeIncomingEvents.name(), in_includeIncomingEvents);
            data.put(Parameter.includeSentEvents.name(), in_includeSentEvents);

            ServerCall sc = new ServerCall(ServiceName.event, ServiceOperation.GET_EVENTS, data, in_callback);
            _client.sendRequest(sc);
        } catch (JSONException e) {
        }
    }

}
