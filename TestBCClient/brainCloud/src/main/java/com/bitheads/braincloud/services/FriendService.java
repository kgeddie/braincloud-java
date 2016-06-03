package com.bitheads.braincloud.services;

import com.bitheads.braincloud.client.BrainCloudClient;
import com.bitheads.braincloud.client.IServerCallback;
import com.bitheads.braincloud.client.ServiceName;
import com.bitheads.braincloud.client.ServiceOperation;
import com.bitheads.braincloud.comms.ServerCall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FriendService {

    private enum Parameter {
        externalId,
        authenticationType,
        profileId,
        searchText,
        maxResults,
        includeSummaryData,
        friendId,
        entityId,
        entityType,
        friendPlatform,
        profileIds
    }

    private BrainCloudClient _client;

    public FriendService(BrainCloudClient client) {
        _client = client;
    }

    public enum FriendPlatform {
        All,
        brainCloud,
        Facebook
    }

    /**
     * Retrieves profile information for the specified user.
     *
     * Service Name - Friend
     * Service Operation - GetFriendProfileInfoForExternalId
     *
     * @param externalId The friend's external id e.g. Facebook id
     * @param authenticationType The authentication type of the friend id e.g. Facebook
      */
    public void getFriendProfileInfoForExternalId(String externalId, String authenticationType, IServerCallback callback) {
        JSONObject data = new JSONObject();
        try {
            data.put(Parameter.externalId.name(), externalId);
            data.put(Parameter.authenticationType.name(), authenticationType);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ServerCall sc = new ServerCall(ServiceName.friend, ServiceOperation.GET_FRIEND_PROFILE_INFO_FOR_EXTERNAL_ID, data, callback);
        BrainCloudClient.getInstance().sendRequest(sc);
    }

    /**
     * Retrieves the external ID for the specified user profile ID on the specified social platform.
     *
     * Service Name - Friend
     * Service Operation - GET_EXTERNAL_ID_FOR_PROFILE_ID
     *
     * @param profileId Profile (player) ID.
     * @param authenticationType The authentication type e.g. Facebook
      */
    public void getExternalIdForProfileId(String profileId, String authenticationType, IServerCallback callback) {
        JSONObject data = new JSONObject();
        try {
            data.put(Parameter.profileId.name(), profileId);
            data.put(Parameter.authenticationType.name(), authenticationType);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ServerCall sc = new ServerCall(ServiceName.friend, ServiceOperation.GET_EXTERNAL_ID_FOR_PROFILE_ID, data, callback);
        BrainCloudClient.getInstance().sendRequest(sc);
    }

    /**
     * Finds a list of players matching the search text by performing a substring
     * search of all player names.
     * If the number of results exceeds maxResults the message
     * "Too many results to return." is received and no players are returned
     *
     * Service Name - Friend
     * Service Operation - FindPlayerByName
     *
     * @param searchText The substring to search for. Minimum length of 3 characters.
     * @param maxResults Maximum number of results to return. If there are more the message
     * @param callback The callback
     */
    public void findPlayerByName(String searchText, int maxResults, IServerCallback callback) {
        JSONObject data = new JSONObject();
        try {
            data.put(Parameter.searchText.name(), searchText);
            data.put(Parameter.maxResults.name(), maxResults);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ServerCall sc = new ServerCall(ServiceName.friend, ServiceOperation.FIND_PLAYER_BY_NAME, data, callback);
        BrainCloudClient.getInstance().sendRequest(sc);
    }

    /**
     * Retrieves profile information for the partial matches of the specified text.
     *
     * @param searchText Universal ID text on which to search.
     * @param maxResults Maximum number of results to return.
     */
    public void findPlayerByUniversalId(String searchText, int maxResults, IServerCallback callback) {
        JSONObject data = new JSONObject();
        try {
            data.put(Parameter.searchText.name(), searchText);
            data.put(Parameter.maxResults.name(), maxResults);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ServerCall sc = new ServerCall(ServiceName.friend, ServiceOperation.FIND_PLAYER_BY_UNIVERSAL_ID, data, callback);
        BrainCloudClient.getInstance().sendRequest(sc);
    }

    /**
     * @deprecated Use listFriends method instead - removal after June 21 2016
     */
    @Deprecated
    public void readFriendsWithApplication(boolean includeSummaryData, IServerCallback callback) {
        JSONObject data = new JSONObject();
        try {
            data.put(Parameter.includeSummaryData.name(), includeSummaryData);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ServerCall sc = new ServerCall(ServiceName.friend, ServiceOperation.READ_FRIENDS_WITH_APPLICATION, data, callback);
        BrainCloudClient.getInstance().sendRequest(sc);
    }

    /**
     * Returns a particular entity of a particular friend.
     *
     * Service Name - Friend
     * Service Operation - ReadFriendEntity
     *
     * @param friendId Profile Id of friend who owns entity.
     * @param entityId Id of entity to retrieve.
     * @param callback The callback handler
     */
    public void readFriendEntity(String friendId, String entityId, IServerCallback callback) {
        JSONObject data = new JSONObject();
        try {
            data.put(Parameter.friendId.name(), friendId);
            data.put(Parameter.entityId.name(), entityId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ServerCall sc = new ServerCall(ServiceName.friend, ServiceOperation.READ_FRIEND_ENTITY, data, callback);
        BrainCloudClient.getInstance().sendRequest(sc);
    }

    /**
     * Returns entities of all friends based on type
     *
     * Service Name - Friend
     * Service Operation - ReadFriendsEntities
     *
     * @param entityType Types of entities to retrieve.
     * @param callback The callback handler
     */
    public void readFriendsEntities(String entityType, IServerCallback callback) {
        JSONObject data = new JSONObject();
        try {
            if (StringUtil.IsOptionalParameterValid(entityType)) {
                data.put(Parameter.entityType.name(), entityType);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ServerCall sc = new ServerCall(ServiceName.friend, ServiceOperation.READ_FRIENDS_ENTITIES, data, callback);
        BrainCloudClient.getInstance().sendRequest(sc);
    }

    /**
     * Read a friend's player state.
     *
     * Service Name - PlayerState
     * Service Operation - ReadFriendsPlayerState
     *
     * @param friendId Target friend
     * @param callback The callback handler
     */
    public void readFriendPlayerState(String friendId, IServerCallback callback) {
        JSONObject data = new JSONObject();
        try {
            data.put(Parameter.friendId.name(), friendId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ServerCall sc = new ServerCall(ServiceName.friend, ServiceOperation.READ_FRIEND_PLAYER_STATE, data, callback);
        BrainCloudClient.getInstance().sendRequest(sc);
    }

    /**
     * Retrieves a list of player and friend platform information for all friends of the current player.
     *
     * Service Name - Friend
     * Service Operation - LIST_FRIENDS
     *
     * @param friendPlatform Friend platform to query.
     * @param includeSummaryData  True if including summary data; false otherwise.
     * @param callback Method to be invoked when the server response is received.
     */
    public void listFriends(FriendPlatform friendPlatform, Boolean includeSummaryData, IServerCallback callback) {
        JSONObject data = new JSONObject();
        try {
            data.put(Parameter.friendPlatform.name(), friendPlatform.name());
            data.put(Parameter.includeSummaryData.name(), includeSummaryData);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ServerCall sc = new ServerCall(ServiceName.friend, ServiceOperation.LIST_FRIENDS, data, callback);
        BrainCloudClient.getInstance().sendRequest(sc);
    }

    /**
     * Links the current player and the specified players as brainCloud friends.
     *
     * Service Name - Friend
     * Service Operation - ADD_FRIENDS
     *
     * @param profileIds Collection of player IDs.
     * @param callback Method to be invoked when the server response is received.
     */
    public void addFriends(String[] profileIds, IServerCallback callback) {
        JSONArray profiles = new JSONArray();
        for (String achId : profileIds) {
            profiles.put(achId);
        }

        JSONObject data = new JSONObject();
        try {
            data.put(Parameter.profileIds.name(), profiles);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ServerCall sc = new ServerCall(ServiceName.friend, ServiceOperation.ADD_FRIENDS, data, callback);
        BrainCloudClient.getInstance().sendRequest(sc);
    }

    /**
     * Unlinks the current player and the specified players as brainCloud friends.
     *
     * Service Name - Friend
     * Service Operation - REMOVE_FRIENDS
     *
     * @param profileIds Collection of player IDs.
     * @param callback Method to be invoked when the server response is received.
     */
    public void removeFriends(String[] profileIds, IServerCallback callback) {
        JSONArray profiles = new JSONArray();
        for (String achId : profileIds) {
            profiles.put(achId);
        }

        JSONObject data = new JSONObject();
        try {
            data.put(Parameter.profileIds.name(), profiles);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ServerCall sc = new ServerCall(ServiceName.friend, ServiceOperation.REMOVE_FRIENDS, data, callback);
        BrainCloudClient.getInstance().sendRequest(sc);
    }
}
