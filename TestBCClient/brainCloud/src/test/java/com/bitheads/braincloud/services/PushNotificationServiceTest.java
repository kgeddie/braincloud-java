package com.bitheads.braincloud.services;

import com.bitheads.braincloud.client.BrainCloudClient;
import com.bitheads.braincloud.client.Platform;

import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by prestonjennings on 15-09-02.
 */
public class PushNotificationServiceTest extends TestFixtureBase
{
    @Test
    public void testDeregisterAllPushNotificationDeviceTokens() throws Exception
    {
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getPushNotificationService().deregisterAllPushNotificationDeviceTokens(tr);

        tr.Run();
    }

    @Test
    public void testDeregisterPushNotificationDeviceToken() throws Exception
    {
        TestResult tr = new TestResult();
        BrainCloudClient.getInstance().getPushNotificationService().registerPushNotificationToken(
                Platform.GooglePlayAndroid, "GARBAGE_TOKEN", tr);

        tr.Reset();
        BrainCloudClient.getInstance().getPushNotificationService().deregisterPushNotificationDeviceToken(
                Platform.GooglePlayAndroid, "GARBAGE_TOKEN", tr);
        tr.Run();
    }

    @Test
    public void testRegisterPushNotificationToken() throws Exception
    {
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getPushNotificationService().registerPushNotificationToken(
                Platform.GooglePlayAndroid, "GARBAGE_TOKEN", tr);

        tr.Run();
    }

    @Test
    public void testSendSimplePushNotification() throws Exception
    {

    }

    @Test
    public void testSendRichPushNotification() throws Exception
    {

    }

    @Test
    public void testSendTemplatedPushNotificationToGroup() throws Exception
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

        BrainCloudClient.getInstance().getPushNotificationService().sendTemplatedPushNotificationToGroup(
                groupId,
                1,
                Helpers.createJsonPair("1", "asdf"),
                tr);
        tr.Run();

        BrainCloudClient.getInstance().getGroupService().deleteGroup(
                groupId,
                -1,
                tr);
        tr.Run();
    }

    @Test
    public void testSendNormalizedPushNotificationToGroup() throws Exception
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

        BrainCloudClient.getInstance().getPushNotificationService().sendNormalizedPushNotificationToGroup(
                groupId,
                "{ \"body\": \"content of message\", \"title\": \"message title\" }",
                Helpers.createJsonPair("1", "asdf"),
                tr);
        tr.Run();

        BrainCloudClient.getInstance().getGroupService().deleteGroup(
                groupId,
                -1,
                tr);
        tr.Run();
    }
}