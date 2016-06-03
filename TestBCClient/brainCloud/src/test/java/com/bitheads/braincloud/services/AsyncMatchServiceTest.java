package com.bitheads.braincloud.services;

import com.bitheads.braincloud.client.BrainCloudClient;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.*;

/**
 * Created by prestonjennings on 15-09-02.
 */
public class AsyncMatchServiceTest extends TestFixtureBase
{
    private final String _platform = "BC";
    
    @Test
    public void testCreateMatch() throws Exception
    {
        TestResult tr = new TestResult();

        JSONArray players = new JSONArray();
        JSONObject player = new JSONObject();
        player.put("platform", _platform);
        player.put("id", getUser(Users.UserB).profileId);
        players.put(player);

        BrainCloudClient.getInstance().getAsyncMatchService().createMatch(
                players.toString(),
                null,
                null,
                tr);

        String matchId = "";

        if (tr.Run())
        {
            matchId = tr.m_response.getJSONObject("data").getString("matchId");
        }

        abandonMatch(matchId);
    }

    @Test
    public void testCreateMatchWithInitialTurn() throws Exception
    {
        TestResult tr = new TestResult();

        JSONArray players = new JSONArray();
        JSONObject player = new JSONObject();
        player.put("platform", _platform);
        player.put("id", getUser(Users.UserB).profileId);
        players.put(player);

        BrainCloudClient.getInstance().getAsyncMatchService().createMatchWithInitialTurn(
                players.toString(),
                Helpers.createJsonPair("blob", 1),
                null,
                null,
                null,
                Helpers.createJsonPair("map", "level1"),
                tr);

        String matchId = "";

        if (tr.Run())
        {
            matchId = tr.m_response.getJSONObject("data").getString("matchId");
        }

        abandonMatch(matchId);
    }

    @Test
    public void testSubmitTurn() throws Exception
    {
        String matchId = createMatch();
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getAsyncMatchService().submitTurn(
                getUser(Users.UserA).profileId,
                matchId,
                BigInteger.valueOf(0),
                Helpers.createJsonPair("blob", 1),
                null,
                getUser(Users.UserB).profileId,
                Helpers.createJsonPair("map", "level1"),
                Helpers.createJsonPair("map", "level1"),
                tr);

        tr.Run();

        abandonMatch(matchId);
    }

    @Test
    public void testUpdateMatchSummaryData() throws Exception
    {
        String matchId = createMatch();
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getAsyncMatchService().updateMatchSummaryData(
                getUser(Users.UserA).profileId,
                matchId,
                BigInteger.valueOf(0),
                Helpers.createJsonPair("map", "level1"),
                tr);

        tr.Run();

        abandonMatch(matchId);
    }

    @Test
    public void testCompleteMatch() throws Exception
    {
        String matchId = createMatchWithInitialTurn();
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getAsyncMatchService().completeMatch(
                getUser(Users.UserA).profileId,
                matchId,
                tr);

        tr.Run();
    }

    @Test
    public void testReadMatch() throws Exception
    {
        String matchId = createMatch();
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getAsyncMatchService().readMatch(
                getUser(Users.UserA).profileId,
                matchId,
                tr);

        tr.Run();

        abandonMatch(matchId);
    }

    @Test
    public void testReadMatchHistory() throws Exception
    {
        String matchId = createMatch();
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getAsyncMatchService().readMatch(
                getUser(Users.UserA).profileId,
                matchId,
                tr);

        tr.Run();

        abandonMatch(matchId);
    }

    @Test
    public void testFindMatches() throws Exception
    {
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getAsyncMatchService().findMatches(
                tr);

        tr.Run();
    }

    @Test
    public void testFindCompleteMatches() throws Exception
    {
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getAsyncMatchService().findCompleteMatches(
                tr);

        tr.Run();
    }

    @Test
    public void testAbandonMatch() throws Exception
    {
        String matchId = createMatch();
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getAsyncMatchService().abandonMatch(
                getUser(Users.UserA).profileId,
                matchId,
                tr);

        tr.Run();
    }

    @Test
    public void testDeleteMatch() throws Exception
    {
        String matchId = createMatch();
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getAsyncMatchService().deleteMatch(
                getUser(Users.UserA).profileId,
                matchId,
                tr);

        tr.Run();
    }
    
    
    ///// helper fns

    private String createMatch() throws Exception
    {
        TestResult tr = new TestResult();

        JSONArray players = new JSONArray();
        JSONObject player = new JSONObject();
        player.put("platform", _platform);
        player.put("id", getUser(Users.UserB).profileId);
        players.put(player);

        BrainCloudClient.getInstance().getAsyncMatchService().createMatch(
                players.toString(),
                null,
                tr);

        String matchId = "";

        if (tr.Run())
        {
            matchId = tr.m_response.getJSONObject("data").getString("matchId");
        }

        return matchId;
    }

    private String createMatchWithInitialTurn() throws Exception
    {
        TestResult tr = new TestResult();

        JSONArray players = new JSONArray();
        JSONObject player = new JSONObject();
        player.put("platform", _platform);
        player.put("id", getUser(Users.UserB).profileId);
        players.put(player);
        
        BrainCloudClient.getInstance().getAsyncMatchService().createMatchWithInitialTurn(
                players.toString(),
                Helpers.createJsonPair("map", "level1"),
                null,
                null,
                Helpers.createJsonPair("map", "level1"),
                tr);

        String matchId = "";

        if (tr.Run())
        {
            matchId = tr.m_response.getJSONObject("data").getString("matchId");
        }

        return matchId;
    }

    private void abandonMatch(String matchId) throws Exception
    {
        TestResult tr = new TestResult();
        BrainCloudClient.getInstance().getAsyncMatchService().abandonMatch(
                getUser(Users.UserA).profileId,
                matchId,
                tr);
        tr.Run();
    }
}