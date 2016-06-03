package com.bitheads.braincloud.services;

import com.bitheads.braincloud.client.BrainCloudClient;
import com.bitheads.braincloud.client.StatusCodes;

import org.json.JSONObject;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by prestonjennings on 15-09-02.
 */
public class SocialLeaderboardServiceTest extends TestFixtureBase
{
    private final String _globalLeaderboardId = "testLeaderboard";
    private final String _socialLeaderboardId = "testSocialLeaderboard";
    private final String _dynamicLeaderboardId = "testDynamicLeaderboard";
    private final String _eventId = "tournamentRewardTest";
    
    @Test
    public void testGetSocialLeaderboard() throws Exception
    {
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getSocialLeaderboardService().getSocialLeaderboard(
                _globalLeaderboardId,
                true,
                tr);

        tr.Run();
    }

    @Test
    public void testGetMultiSocialLeaderboard() throws Exception
    {
        postScoreToDynamicLeaderboard();
        postScoreToNonDynamicLeaderboard();

        TestResult tr = new TestResult();

        String[] lbIds = new String[] {
                _globalLeaderboardId,
                _dynamicLeaderboardId + "-" + SocialLeaderboardService.SocialLeaderboardType.LAST_VALUE
        };
        BrainCloudClient.getInstance().getSocialLeaderboardService().getMultiSocialLeaderboard(
                lbIds,
                10,
                true,
                tr);

        tr.Run();
    }

    @Test
    public void testGetGlobalLeaderboardPageHigh() throws Exception
    {
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getSocialLeaderboardService().getGlobalLeaderboardPage(
                _globalLeaderboardId,
                SocialLeaderboardService.SortOrder.HIGH_TO_LOW,
                0,
                10,
                true,
                tr);

        tr.Run();
    }

    @Test
    public void testGetGlobalLeaderboardPageLow() throws Exception
    {
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getSocialLeaderboardService().getGlobalLeaderboardPage(
                _globalLeaderboardId,
                SocialLeaderboardService.SortOrder.LOW_TO_HIGH,
                0,
                10,
                true,
                tr);

        tr.Run();
    }

    @Test
    public void testGetGlobalLeaderboardPageFail() throws Exception
    {
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getSocialLeaderboardService().getGlobalLeaderboardPage(
                "thisDoesNotExistLeaderboard",
                SocialLeaderboardService.SortOrder.LOW_TO_HIGH,
                0,
                10,
                true,
                tr);

        tr.RunExpectFail(StatusCodes.INTERNAL_SERVER_ERROR, 0);
    }

    @Test
    public void testGetGlobalLeaderboardPageByVersion() throws Exception
    {
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getSocialLeaderboardService().getGlobalLeaderboardPageByVersion(
                _globalLeaderboardId,
                SocialLeaderboardService.SortOrder.HIGH_TO_LOW,
                0,
                10,
                true,
                1,
                tr);

        tr.Run();
    }

    @Test
    public void testGetGlobalLeaderboardView() throws Exception
    {
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getSocialLeaderboardService().getGlobalLeaderboardView(
                _globalLeaderboardId,
                SocialLeaderboardService.SortOrder.HIGH_TO_LOW,
                5,
                5,
                true,
                tr);

        tr.Run();
    }

    @Test
    public void testGetGlobalLeaderboardViewByVersion() throws Exception
    {
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getSocialLeaderboardService().getGlobalLeaderboardViewByVersion(
                _globalLeaderboardId,
                SocialLeaderboardService.SortOrder.HIGH_TO_LOW,
                5,
                5,
                true,
                1,
                tr);

        tr.Run();
    }

    @Test
    public void testGetGlobalLeaderboardVersions() throws Exception
    {
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getSocialLeaderboardService().getGlobalLeaderboardVersions(
                _globalLeaderboardId,
                tr);

        tr.Run();
    }

    @Test
    public void testPostScoreToLeaderboard() throws Exception
    {
        postScoreToNonDynamicLeaderboard();
    }

    @Test
    public void testPostScoreToDynamicLeaderboard() throws Exception
    {
        postScoreToDynamicLeaderboard();
    }

    @Test
    public void testPostScoreToDynamicLeaderboardLowValue() throws Exception
    {
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getSocialLeaderboardService().postScoreToDynamicLeaderboard(
                _dynamicLeaderboardId + "-" + SocialLeaderboardService.SocialLeaderboardType.LOW_VALUE,
                100,
                Helpers.createJsonPair("testDataKey", 400),
                SocialLeaderboardService.SocialLeaderboardType.LOW_VALUE.toString(),
                SocialLeaderboardService.RotationType.NEVER.toString(),
                null,
                5,
                tr);

        tr.Run();
    }

    @Test
    public void testPostScoreToDynamicLeaderboardCumulative() throws Exception
    {
        TestResult tr = new TestResult();

        Date date = new Date();
        date.setTime(date.getTime() + 5 * 24 * 60 * 60 * 1000);
        BrainCloudClient.getInstance().getSocialLeaderboardService().postScoreToDynamicLeaderboard(
                _dynamicLeaderboardId + "-" + SocialLeaderboardService.SocialLeaderboardType.CUMULATIVE,
                100,
                Helpers.createJsonPair("testDataKey", 400),
                SocialLeaderboardService.SocialLeaderboardType.CUMULATIVE.toString(),
                SocialLeaderboardService.RotationType.WEEKLY.toString(),
                date,
                5,
                tr);

        tr.Run();
    }

    @Test
    public void testPostScoreToDynamicLeaderboardLastValue() throws Exception
    {
        TestResult tr = new TestResult();

        Date date = new Date();
        date.setTime(date.getTime() + 15 * 60 * 60 * 1000);
        BrainCloudClient.getInstance().getSocialLeaderboardService().postScoreToDynamicLeaderboard(
                _dynamicLeaderboardId + "-" + SocialLeaderboardService.SocialLeaderboardType.LAST_VALUE,
                100,
                Helpers.createJsonPair("testDataKey", 400),
                SocialLeaderboardService.SocialLeaderboardType.LAST_VALUE.toString(),
                SocialLeaderboardService.RotationType.DAILY.toString(),
                date,
                5,
                tr);

        tr.Run();
    }

    @Test
    public void testPostScoreToDynamicLeaderboardNullRotationTime() throws Exception
    {
        postScoreToDynamicLeaderboard();
    }

    @Test
    public void testResetLeaderboardScore() throws Exception
    {
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getSocialLeaderboardService().resetLeaderboardScore(
                _globalLeaderboardId,
                tr);

        tr.Run();
    }

    @Test
    public void testGetCompletedTournament() throws Exception
    {
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getSocialLeaderboardService().getCompletedTournament(
                _socialLeaderboardId,
                true,
                tr);

        tr.Run();
    }

    @Test
    public void testTriggerSocialLeaderboardTournamentReward() throws Exception
    {
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getSocialLeaderboardService().triggerSocialLeaderboardTournamentReward(
                _socialLeaderboardId,
                _eventId,
                1,
                tr);

        tr.Run();
    }

    @Test
    public void testGetGroupSocialLeaderboard() throws Exception
    {
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getGroupService().createGroup(
                "testGroup",
                "test",
                false,
                new GroupACL(GroupACL.Access.ReadWrite, GroupACL.Access.ReadWrite),
                Helpers.createJsonPair("testInc", 123),
                Helpers.createJsonPair("test", "test"),
                Helpers.createJsonPair("test", "test"),
                tr);

        tr.Run();

        JSONObject data = tr.m_response.getJSONObject("data");
        String groupId = data.getString("groupId");

        BrainCloudClient.getInstance().getSocialLeaderboardService().getGroupSocialLeaderboard(
                _socialLeaderboardId,
                groupId,
                tr);
        tr.Run();

        BrainCloudClient.getInstance().getGroupService().deleteGroup(
                groupId,
                -1,
                tr);
        tr.Run();
    }

    public void postScoreToDynamicLeaderboard() throws Exception
    {
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getSocialLeaderboardService().postScoreToDynamicLeaderboard(
                _dynamicLeaderboardId + "-" + SocialLeaderboardService.SocialLeaderboardType.LAST_VALUE,
                100,
                Helpers.createJsonPair("testDataKey", 400),
                SocialLeaderboardService.SocialLeaderboardType.LAST_VALUE.toString(),
                SocialLeaderboardService.RotationType.NEVER.toString(),
                null,
                5,
                tr);

        tr.Run();
    }

    public void postScoreToNonDynamicLeaderboard() throws Exception
    {
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getSocialLeaderboardService().postScoreToLeaderboard(
                _globalLeaderboardId,
                1000,
                Helpers.createJsonPair("testDataKey", 400),
                tr);

        tr.Run();
    }

}