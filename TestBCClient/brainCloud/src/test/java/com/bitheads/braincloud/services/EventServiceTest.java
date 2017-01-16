package com.bitheads.braincloud.services;

import com.bitheads.braincloud.client.BrainCloudClient;
import com.bitheads.braincloud.client.IEventCallback;

import junit.framework.Assert;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Test;

/**
 * Created by prestonjennings on 15-09-02.
 */
public class EventServiceTest extends TestFixtureBase implements IEventCallback {
    private final String _eventType = "test";
    private final String _eventDataKey = "testData";

    private boolean _callbackRan = false;
    private String _eventId = null;

    @After
    public void Teardown() throws Exception {
        if (_eventId != null && !_eventId.isEmpty()) {
            cleanupIncomingEvent(_eventId);
        }
    }

    @Test
    public void testSendEvent() throws Exception {
        BrainCloudClient.getInstance().registerEventCallback(this);

        sendDefaultMessage();

        Assert.assertTrue(_callbackRan);

        BrainCloudClient.getInstance().deregisterEventCallback();
    }

    @Override
    public void eventsReceived(JSONObject events) {
        //Console.WriteLine("Events received: " + jsonResponse);
        int numEvents = 0;
        try {
            numEvents = events.getJSONArray("events").length();
        } catch (JSONException je) {
            je.printStackTrace();
        }

        Assert.assertEquals(numEvents, 1);
        _callbackRan = true;
    }

    @Test
    public void testUpdateIncomingEventData() throws Exception {
        TestResult tr = new TestResult();
        sendDefaultMessage();

        BrainCloudClient.getInstance().getEventService().updateIncomingEventData(
                _eventId,
                Helpers.createJsonPair(_eventDataKey, 343),
                tr);

        tr.Run();
    }

    @Test
    public void testDeleteIncomingEvent() throws Exception {
        TestResult tr = new TestResult();

        sendDefaultMessage();

        BrainCloudClient.getInstance().getEventService().deleteIncomingEvent(
                _eventId,
                tr);

        tr.Run();
        _eventId = null;
    }

    @Test
    public void testGetEvents() throws Exception {
        TestResult tr = new TestResult();

        sendDefaultMessage();

        BrainCloudClient.getInstance().getEventService().getEvents(tr);

        tr.Run();
    }


    /// helpers

    private void sendDefaultMessage() throws Exception {
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getEventService().sendEvent(
                getUser(Users.UserA).profileId,
                _eventType,
                Helpers.createJsonPair(_eventDataKey, 117),
                tr);

        if (tr.Run()) {
            _eventId = tr.m_response.getJSONObject("data").getString("evId");
        }
    }

    private void cleanupIncomingEvent(String eventId) throws Exception {
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getEventService().deleteIncomingEvent(
                eventId,
                tr);

        tr.Run();
    }
}