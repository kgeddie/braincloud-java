package com.bitheads.braincloud.services;

import com.bitheads.braincloud.client.BrainCloudClient;
import com.bitheads.braincloud.client.BrainCloudWrapper;

import org.json.JSONException;

/**
 * Created by prestonjennings on 15-09-01.
 */

/// <summary>
/// Holds data for a randomly generated user
/// </summary>
public class TestUser
{
    public String id = "";
    public String password = "";
    public String profileId = "";
    public String email = "";

    BrainCloudWrapper _wrapper;


    public TestUser(BrainCloudWrapper wrapper, String idPrefix, int suffix)
    {
        _wrapper = wrapper;
        id = idPrefix + suffix;
        password = id;
        email = id + "@bctestuser.com";
        Authenticate();
    }

    private void Authenticate()
    {
        TestResult tr = new TestResult(_wrapper);
        _wrapper.getAuthenticationService().authenticateUniversal(
                id,
                password,
                true,
                tr);
        tr.Run();
        profileId = _wrapper.getAuthenticationService().getProfileId();

        try
        {
            if (tr.m_response.getJSONObject("data").getBoolean("newUser") == true)
            {
                _wrapper.getMatchMakingService().enableMatchMaking(tr);
                tr.Run();
                _wrapper.getPlayerStateService().updateUserName(id, tr);
                tr.Run();
                _wrapper.getPlayerStateService().updateContactEmail("braincloudunittest@gmail.com", tr);
                tr.Run();
            }

            _wrapper.getPlayerStateService().logout(tr);
            tr.Run();
        }
        catch(JSONException je)
        {
            je.printStackTrace();
        }
    }
}
