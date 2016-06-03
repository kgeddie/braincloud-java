package com.bitheads.braincloud.services;

import com.bitheads.braincloud.client.BrainCloudClient;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by prestonjennings on 15-09-02.
 */
public class GlobalStatisticsServiceTest extends TestFixtureBase
{

    @Test
    public void testReadAllGlobalStats() throws Exception
    {
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getGlobalStatisticsService().readAllGlobalStats(
                tr);

        tr.Run();
    }

    @Test
    public void testReadGlobalStatsSubset() throws Exception
    {
        TestResult tr = new TestResult();

        String[] stats = {"TestStat"};

        BrainCloudClient.getInstance().getGlobalStatisticsService().readGlobalStatsSubset(
                stats, tr);

        tr.Run();
    }

    @Test
    public void testReadGlobalStatsForCategory() throws Exception
    {
        TestResult tr = new TestResult();
        BrainCloudClient.getInstance().getGlobalStatisticsService().readGlobalStatsForCategory("Test", tr);
        tr.Run();
    }

    @Test
    public void testIncrementGlobalStats() throws Exception
    {
        TestResult tr = new TestResult();

        JSONObject stats = new JSONObject();
        stats.put("TestStat", "RESET");

        BrainCloudClient.getInstance().getGlobalStatisticsService().incrementGlobalStats(
                stats.toString(),
                tr);

        tr.Run();
    }
}