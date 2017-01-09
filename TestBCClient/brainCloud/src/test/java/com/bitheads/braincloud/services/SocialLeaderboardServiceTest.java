package com.bitheads.braincloud.services;

import com.bitheads.braincloud.client.BrainCloudClient;
import com.bitheads.braincloud.client.StatusCodes;

import org.json.JSONObject;
import org.junit.Test;

import java.util.Calendar;
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
                tr);

        tr.RunExpectFail(StatusCodes.INTERNAL_SERVER_ERROR, 40499);
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
    public void testPostScoreToDynamicLeaderboardDays() throws Exception
    {
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getSocialLeaderboardService().postScoreToDynamicLeaderboardDays(
                _dynamicLeaderboardId + "-" + "DAYS",
                100,
                Helpers.createJsonPair("testDataKey", 400),
                SocialLeaderboardService.SocialLeaderboardType.LOW_VALUE.toString(),
                null,
                5,
                3,
                tr);

        tr.Run();
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

        BrainCloudClient.getInstance().getSocialLeaderboardService().postScoreToDynamicLeaderboard(
                _dynamicLeaderboardId + "-" + SocialLeaderboardService.SocialLeaderboardType.CUMULATIVE,
                100,
                Helpers.createJsonPair("testDataKey", 400),
                SocialLeaderboardService.SocialLeaderboardType.CUMULATIVE.toString(),
                SocialLeaderboardService.RotationType.WEEKLY.toString(),
                addDays(new Date(), 3),
                5,
                tr);

        tr.Run();
    }

    @Test
    public void testPostScoreToDynamicLeaderboardLastValue() throws Exception
    {
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getSocialLeaderboardService().postScoreToDynamicLeaderboard(
                _dynamicLeaderboardId + "-" + SocialLeaderboardService.SocialLeaderboardType.LAST_VALUE,
                100,
                Helpers.createJsonPair("testDataKey", 400),
                SocialLeaderboardService.SocialLeaderboardType.LAST_VALUE.toString(),
                SocialLeaderboardService.RotationType.DAILY.toString(),
                addDays(new Date(), 3),
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

    @Test
    public void testGetPlayersSocialLeaderboard() throws Exception
    {
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getSocialLeaderboardService().getPlayersSocialLeaderboard(
                _socialLeaderboardId,
                new String[] { getUser(Users.UserA).profileId, getUser(Users.UserB).profileId },
                tr);

        tr.Run();
    }

    @Test
    public void testListAllLeaderboards() throws Exception
    {
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getSocialLeaderboardService().listAllLeaderboards(tr);

        tr.Run();
    }

    @Test
    public void getGlobalLeaderboardEntryCount() throws Exception
    {
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getSocialLeaderboardService().getGlobalLeaderboardEntryCount(_globalLeaderboardId, tr);

        tr.Run();
    }

    @Test
    public void getGlobalLeaderboardEntryCountByVersion() throws Exception
    {
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getSocialLeaderboardService().getGlobalLeaderboardEntryCountByVersion(_globalLeaderboardId, 1, tr);

        tr.Run();
    }

    public void postScoreToDynamicLeaderboard() throws Exception
    {
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getSocialLeaderboardService().postScoreToDynamicLeaderboard(
                _dynamicLeaderboardId + "-" + (int)(Math.random() * 10000000),
                100,
                Helpers.createJsonPair("testDataKey", 400),
                SocialLeaderboardService.SocialLeaderboardType.LAST_VALUE.toString(),
                SocialLeaderboardService.RotationType.NEVER.toString(),
                addDays(new Date(), 3),
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

    public static Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }
}