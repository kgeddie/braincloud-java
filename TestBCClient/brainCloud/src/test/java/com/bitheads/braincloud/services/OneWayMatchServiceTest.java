package com.bitheads.braincloud.services;

import com.bitheads.braincloud.client.BrainCloudClient;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by prestonjennings on 15-09-02.
 */
public class OneWayMatchServiceTest extends TestFixtureBase
{

    @Test
    public void testStartMatch() throws Exception
    {
        String streamId = startMatch();
        cancelMatch(streamId);
    }

    @Test
    public void testCancelMatch() throws Exception
    {
        String streamId = startMatch();
        cancelMatch(streamId);
    }

    @Test
    public void testCompleteMatch() throws Exception
    {
        String streamId = startMatch();
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getOneWayMatchService().completeMatch(
                streamId,
                tr);

        tr.Run();
    }

    private String startMatch() throws Exception
    {
        TestResult tr = new TestResult();
        String streamId = "";

        BrainCloudClient.getInstance().getOneWayMatchService().startMatch(
                getUser(Users.UserB).profileId,
                1000,
                tr);

        if (tr.Run())
        {
            streamId = tr.m_response.getJSONObject("data").getString("playbackStreamId");
        }

        return streamId;
    }

    private void cancelMatch(String streamId)
    {
        TestResult tr = new TestResult();
        BrainCloudClient.getInstance().getOneWayMatchService().cancelMatch(
                streamId,
                tr);
        tr.Run();
    }

}