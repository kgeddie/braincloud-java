package com.bitheads.braincloud.services;

import com.bitheads.braincloud.client.AuthenticationType;
import com.bitheads.braincloud.client.BrainCloudClient;

import org.junit.Test;

/**
 * Created by prestonjennings on 15-09-02.
 */
public class IdentityServiceTest extends TestFixtureBase {

    @Test
    public void testAttachEmailIdentity() throws Exception {
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getIdentityService().attachEmailIdentity(
                "id_" + getUser(Users.UserA).email,
                getUser(Users.UserA).password,
                tr);

        tr.Run();
    }

    @Test
    public void testMergeEmailIdentity() throws Exception {

    }

    @Test
    public void testDetachEmailIdentity() throws Exception {

    }

    @Test
    public void testSwitchToChildProfile() throws Exception {
        TestResult tr = new TestResult();
        BrainCloudClient.getInstance().getIdentityService().switchToChildProfile(null, m_childAppId, true, tr);
        tr.Run();
    }

    @Test
    public void testSwitchToSingletonChildProfile() throws Exception {
        Logout();

        TestResult tr = new TestResult();
        BrainCloudClient.getInstance().getAuthenticationService().authenticateEmailPassword(
                getUser(Users.UserC).email,
                getUser(Users.UserC).password,
                true,
                tr);
        tr.Run();
        tr.Reset();

        BrainCloudClient.getInstance().getIdentityService().switchToSingletonChildProfile(m_childAppId, true, tr);
        tr.Run();
    }

    @Test
    public void testSwitchToParentProfile() throws Exception {
        TestResult tr = new TestResult();
        BrainCloudClient.getInstance().getIdentityService().switchToChildProfile(null, m_childAppId, true, tr);
        tr.Run();

        tr.Reset();
        BrainCloudClient.getInstance().getIdentityService().switchToParentProfile(m_parentLevelName, tr);
        tr.Run();
    }

    @Test
    public void testGetChildProfiles() throws Exception {
        TestResult tr = new TestResult();
        BrainCloudClient.getInstance().getIdentityService().getChildProfiles(true, tr);
        tr.Run();
    }

    @Test
    public void testGetIdentities() throws Exception {
        TestResult tr = new TestResult();
        BrainCloudClient.getInstance().getIdentityService().getIdentities(tr);
        tr.Run();
    }

    @Test
    public void testGetExpiredIdentities() throws Exception {
        TestResult tr = new TestResult();
        BrainCloudClient.getInstance().getIdentityService().getExpiredIdentities(tr);
        tr.Run();
    }

    @Test
    public void testRefreshIdentity() throws Exception {
        TestResult tr = new TestResult();
        BrainCloudClient.getInstance().getIdentityService().refreshIdentity(
                getUser(Users.UserA).id,
                getUser(Users.UserA).password,
                AuthenticationType.Universal,
                tr);
        tr.RunExpectFail(400, 40464);
    }
}
