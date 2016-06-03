package com.bitheads.braincloud.services;

import com.bitheads.braincloud.client.BrainCloudClient;
import com.bitheads.braincloud.client.IEventCallback;

import junit.framework.Assert;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by prestonjennings on 15-09-02.
 */
public class EventServiceTest extends TestFixtureBase implements IEventCallback
{
    private final String _eventType = "test";
    private final String _eventDataKey = "testData";

    private boolean _callbackRan = false;

    @Test
    public void testSendEvent() throws Exception
    {
        TestResult tr = new TestResult();
        long eventId = 0;

        BrainCloudClient.getInstance().registerEventCallback(this);

        BrainCloudClient.getInstance().getEventService().sendEvent(
                getUser(Users.UserA).profileId,
                _eventType,
                Helpers.createJsonPair(_eventDataKey, 117),
                true,
                tr);

        if (tr.Run())
        {
            eventId = tr.m_response.getJSONObject("data").getLong("eventId");
        }

        Assert.assertTrue(_callbackRan);

        cleanupIncomingEvent(eventId);
        BrainCloudClient.getInstance().deregisterEventCallback();
    }

    @Override
    public void eventsReceived(JSONObject events)
    {
        //Console.WriteLine("Events received: " + jsonResponse);
        int numEvents = 0;
        try
        {
            numEvents = events.getJSONArray("events").length();
        }
        catch(JSONException je)
        {
            je.printStackTrace();
        }

        Assert.assertEquals(numEvents, 1);
        _callbackRan = true;
    }

    @Test
    public void testUpdateIncomingEventData() throws Exception
    {
        TestResult tr = new TestResult();

        long eventId = sendDefaultMessage(false);

        BrainCloudClient.getInstance().getEventService().updateIncomingEventData(
                getUser(Users.UserA).profileId,
                eventId,
                Helpers.createJsonPair(_eventDataKey, 343),
                tr);

        tr.Run();

        cleanupIncomingEvent(eventId);
    }

    @Test
    public void testDeleteIncomingEvent() throws Exception
    {
        TestResult tr = new TestResult();

        long eventId = sendDefaultMessage(false);

        BrainCloudClient.getInstance().getEventService().deleteIncomingEvent(
                getUser(Users.UserA).profileId,
                eventId,
                tr);

        tr.Run();
    }

    @Test
    public void testDeleteSentEvent() throws Exception
    {
        TestResult tr = new TestResult();

        long eventId = sendDefaultMessage(true);

        BrainCloudClient.getInstance().getEventService().deleteSentEvent(
                getUser(Users.UserA).profileId,
                eventId,
                tr);

        tr.Run();

        cleanupIncomingEvent(eventId);
    }

    @Test
    public void testGetEvents() throws Exception
    {
        TestResult tr = new TestResult();

        long eventId = sendDefaultMessage(true);

        BrainCloudClient.getInstance().getEventService().getEvents(
                true,
                true,
                tr);

        tr.Run();

        cleanupIncomingEvent(eventId);
    }
    
    
    
    /// helpers

    private long sendDefaultMessage() throws Exception
    {
        return sendDefaultMessage(false);
    }
    
    private long sendDefaultMessage(boolean recordLocally) throws Exception
    {
        TestResult tr = new TestResult();
        long eventId = 0;

        BrainCloudClient.getInstance().getEventService().sendEvent(
                getUser(Users.UserA).profileId,
                _eventType,
                Helpers.createJsonPair(_eventDataKey, 117),
                recordLocally,
                tr);

        if (tr.Run())
        {
            eventId = tr.m_response.getJSONObject("data").getLong("eventId");
        }

        return eventId;
    }

    private void cleanupIncomingEvent(long eventId) throws Exception
    {
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getEventService().deleteIncomingEvent(
                getUser(Users.UserA).profileId,
                eventId,
                tr);

        tr.Run();
    }
}