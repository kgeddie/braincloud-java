package com.bitheads.braincloud.services;

import com.bitheads.braincloud.client.BrainCloudClient;

import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by prestonjennings on 15-09-02.
 */
public class FriendServiceTest extends TestFixtureBase
{

    @Test
    public void testGetFriendProfileInfoForExternalId() throws Exception
    {

    }

    @Test
    public void testGetExternalIdForProfileId() throws Exception
    {
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getFriendService().getExternalIdForProfileId(
                getUser(Users.UserA).profileId,
                "Facebook",
                tr);

        tr.Run();
    }

    @Test
    public void testGetSummaryDataForProfileId() throws Exception
    {
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getFriendService().getSummaryDataForProfileId(
                getUser(Users.UserA).profileId,
                tr);

        tr.Run();
    }

    @Test
    public void testFindPlayerByName() throws Exception
    {
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getFriendService().findPlayerByName(
                "search",
                10,
                tr);

        tr.Run();
    }

    @Test
    public void testFindPlayerByUniversalId() throws Exception
    {
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getFriendService().findPlayerByUniversalId(
                "search",
                5,
                tr);

        tr.Run();
    }


    @Test
    public void testListFriends() throws Exception
    {
        addFriend();
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getFriendService().listFriends(
                FriendService.FriendPlatform.All, false, tr);

        tr.Run();
    }

    @Test
    public void testAddFriends() throws Exception
    {
        addFriend();
    }

    @Test
    public void testRemoveFriends() throws Exception
    {
        addFriend();

        TestResult tr = new TestResult();
        String[] ids = { getUser(Users.UserB).profileId };
        BrainCloudClient.getInstance().getFriendService().removeFriends(ids, tr);
        tr.Run();
    }

    @Test
    public void testGetPlayersOnlineStatus() throws Exception
    {
        TestResult tr = new TestResult();
        String[] ids = { getUser(Users.UserB).profileId };
        BrainCloudClient.getInstance().getFriendService().getPlayersOnlineStatus(ids, tr);
        tr.Run();
    }

    @Test
    public void testReadFriendEntity() throws Exception
    {

    }

    @Test
    public void testReadFriendsEntities() throws Exception
    {

    }

    @Test
    public void testReadFriendPlayerState() throws Exception
    {

    }

    private void addFriend()
    {
        TestResult tr = new TestResult();
        String[] ids = { getUser(Users.UserB).profileId };
        BrainCloudClient.getInstance().getFriendService().addFriends(ids, tr);
        tr.Run();
    }
}