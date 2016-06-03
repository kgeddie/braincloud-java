package com.bitheads.braincloud.services;

import com.bitheads.braincloud.client.BrainCloudClient;
import com.bitheads.braincloud.client.IServerCallback;
import com.bitheads.braincloud.client.ServiceName;
import com.bitheads.braincloud.client.ServiceOperation;
import com.bitheads.braincloud.comms.ServerCall;

import org.json.JSONException;
import org.json.JSONObject;

public class GlobalEntityService {
    private BrainCloudClient _client;

    public GlobalEntityService(BrainCloudClient client) {
        _client = client;
    }

    private enum Parameter {
        entityId,
        entityType,
        entityIndexedId,
        data,
        acl,
        version,
        maxReturn,
        where,
        orderBy,
        timeToLive,
        context,
        pageOffset,
        returnData
    }

    /**
     * Method creates a new entity on the server.
     *
     * @param in_entityType The entity type as defined by the user
     * @param in_timeToLive Sets expiry time in millis for entity if > 0
     * @param in_jsonEntityAcl The entity's access control list as json. A null acl implies default
     * @param in_jsonEntityData The entity's data as a json String
     * @param callback Callback.
     */
    public void createEntity(String in_entityType, long in_timeToLive,
                             String in_jsonEntityAcl, String in_jsonEntityData,
                             IServerCallback callback) {
        try {
            JSONObject data = new JSONObject();
            data.put(Parameter.entityType.name(), in_entityType);
            data.put(Parameter.timeToLive.name(), in_timeToLive);

            JSONObject jsonData = new JSONObject(in_jsonEntityData);
            data.put(Parameter.data.name(), jsonData);

            if (StringUtil.IsOptionalParameterValid(in_jsonEntityAcl)) {
                JSONObject jsonAcl = new JSONObject(in_jsonEntityAcl);
                data.put(Parameter.acl.name(), jsonAcl);
            }

            ServerCall serverCall = new ServerCall(ServiceName.globalEntity,
                    ServiceOperation.CREATE, data, callback);
            _client.sendRequest(serverCall);

        } catch (JSONException ignored) {
        }
    }

    /**
     * Method creates a new entity on the server with an indexed id.
     *
     * @param in_entityType The entity type as defined by the user
     * @param in_indexedId A secondary ID that will be indexed
     * @param in_timeToLive Sets expiry time in millis for entity if > 0
     * @param in_jsonEntityAcl The entity's access control list as json. A null acl implies default
     * @param in_jsonEntityData The entity's data as a json String
     * @param callback Callback.
     */
    public void createEntityWithIndexedId(String in_entityType,
                                          String in_indexedId, long in_timeToLive, String in_jsonEntityAcl,
                                          String in_jsonEntityData, IServerCallback callback) {
        try {
            JSONObject data = new JSONObject();
            data.put(Parameter.entityType.name(), in_entityType);
            data.put(Parameter.entityIndexedId.name(), in_indexedId);
            data.put(Parameter.timeToLive.name(), in_timeToLive);

            JSONObject jsonData = new JSONObject(in_jsonEntityData);
            data.put(Parameter.data.name(), jsonData);

            if (StringUtil.IsOptionalParameterValid(in_jsonEntityAcl)) {
                JSONObject jsonAcl = new JSONObject(in_jsonEntityAcl);
                data.put(Parameter.acl.name(), jsonAcl);
            }

            ServerCall serverCall = new ServerCall(ServiceName.globalEntity,
                    ServiceOperation.CREATE_WITH_INDEXED_ID, data, callback);
            _client.sendRequest(serverCall);

        } catch (JSONException ignored) {
        }
    }

    /**
     * Method updates an existing entity on the server.
     *
     * @param in_entityId The entity ID
     * @param in_version Current version of the entity. If the version of the entity on
     *            the server does not match the version passed in, the server
     *            operation will fail. Use -1 to skip version checking.
     * @param in_jsonEntityData The entity's data as a json String
     * @param callback Callback.
     */
    public void updateEntity(String in_entityId, int in_version,
                             String in_jsonEntityData, IServerCallback callback) {
        try {

            JSONObject data = new JSONObject();
            data.put(Parameter.entityId.name(), in_entityId);
            data.put(Parameter.version.name(), in_version);

            JSONObject jsonData = new JSONObject(in_jsonEntityData);
            data.put(Parameter.data.name(), jsonData);

            ServerCall serverCall = new ServerCall(ServiceName.globalEntity,
                    ServiceOperation.UPDATE, data, callback);
            _client.sendRequest(serverCall);

        } catch (JSONException ignored) {
        }
    }

    /**
     * Method updates an existing entity's Acl on the server.
     *
     * @param in_entityId The entity ID
     * @param in_version Current version of the entity. If the version of the entity on
     *            the server does not match the version passed in, the server
     *            operation will fail. Use -1 to skip version checking.
     * @param in_jsonEntityAcl The entity's access control list as json.
     * @param callback Callback.
     */
    public void updateEntityAcl(String in_entityId, int in_version,
                                String in_jsonEntityAcl, IServerCallback callback) {
        try {

            JSONObject data = new JSONObject();
            data.put(Parameter.entityId.name(), in_entityId);
            data.put(Parameter.version.name(), in_version);

            if (StringUtil.IsOptionalParameterValid(in_jsonEntityAcl)) {
                JSONObject jsonAcl = new JSONObject(in_jsonEntityAcl);
                data.put(Parameter.acl.name(), jsonAcl);
            }

            ServerCall serverCall = new ServerCall(ServiceName.globalEntity,
                    ServiceOperation.UPDATE_ACL, data, callback);
            _client.sendRequest(serverCall);

        } catch (JSONException ignored) {
        }
    }

    /**
     * Method updates an existing entity's time to live on the server.
     *
     * @param in_entityId The entity id
     * @param in_version Current version of the entity. If the version of the entity on
     *            the server does not match the version passed in, the server
     *            operation will fail. Use -1 to skip version checking.
     * @param in_timeToLive Sets expiry time in millis for entity if > 0
     * @param callback Callback
     */
    public void updateEntityTimeToLive(String in_entityId, int in_version,
                                       long in_timeToLive, IServerCallback callback) {
        try {

            JSONObject data = new JSONObject();
            data.put(Parameter.entityId.name(), in_entityId);
            data.put(Parameter.version.name(), in_version);
            data.put(Parameter.timeToLive.name(), in_timeToLive);

            ServerCall serverCall = new ServerCall(ServiceName.globalEntity,
                    ServiceOperation.UPDATE_TIME_TO_LIVE, data, callback);
            _client.sendRequest(serverCall);

        } catch (JSONException ignored) {
        }
    }

    /**
     * Method deletes an existing entity on the server.
     *
     * @param in_entityId The entity ID
     * @param in_version The version of the entity to delete
     * @param callback Callback.
     */
    public void deleteEntity(String in_entityId, int in_version,
                             IServerCallback callback) {
        try {

            JSONObject data = new JSONObject();
            data.put(Parameter.entityId.name(), in_entityId);
            data.put(Parameter.version.name(), in_version);

            ServerCall serverCall = new ServerCall(ServiceName.globalEntity,
                    ServiceOperation.DELETE, data, callback);
            _client.sendRequest(serverCall);

        } catch (JSONException ignored) {
        }
    }

    /**
     * Method reads an existing entity from the server.
     *
     * @param in_entityId The entity ID
     * @param callback Callback.
     */
    public void readEntity(String in_entityId,
                           IServerCallback callback) {
        try {
            JSONObject data = new JSONObject();
            data.put(Parameter.entityId.name(), in_entityId);

            ServerCall serverCall = new ServerCall(ServiceName.globalEntity,
                    ServiceOperation.READ, data, callback);
            _client.sendRequest(serverCall);
        } catch (JSONException ignored) {
        }
    }

    /**
     * Method gets list of entities from the server base on type and/or where
     * clause
     *
     * @param in_where Mongo style query string
     * @param in_orderBy Specifies the order in which the query returns matching
     *            documents. The sort parameter consists of a field followed by
     *            an ascending(1)/descending flag(-1). eg. { name : 1} sorts by
     *            name in ascending order
     * @param in_maxReturn The maximum number of entities to return
     * @param callback Callback.
     */
    public void getList(String in_where, String in_orderBy, int in_maxReturn,
                        IServerCallback callback) {
        try {
            JSONObject data = new JSONObject();

            if (StringUtil.IsOptionalParameterValid(in_where)) {
                JSONObject where = new JSONObject(in_where);
                data.put(Parameter.where.name(), where);
            }
            if (StringUtil.IsOptionalParameterValid(in_orderBy)) {
                JSONObject orderBy = new JSONObject(in_orderBy);
                data.put(Parameter.orderBy.name(), orderBy);
            }
            data.put(Parameter.maxReturn.name(), in_maxReturn);

            ServerCall serverCall = new ServerCall(ServiceName.globalEntity,
                    ServiceOperation.GET_LIST, data, callback);
            _client.sendRequest(serverCall);

        } catch (JSONException ignored) {
        }
    }

    /**
     * Method gets list of entities from the server base on indexed id
     *
     * @param in_entityIndexedId The entity indexed Id
     * @param in_maxReturn The maximum number of entities to return
     * @param callback Callback.
     */
    public void getListByIndexedId(String in_entityIndexedId, int in_maxReturn,
                                   IServerCallback callback) {
        try {
            JSONObject data = new JSONObject();
            data.put(Parameter.entityIndexedId.name(), in_entityIndexedId);
            data.put(Parameter.maxReturn.name(), in_maxReturn);

            ServerCall serverCall = new ServerCall(ServiceName.globalEntity,
                    ServiceOperation.GET_LIST_BY_INDEXED_ID, data, callback);
            _client.sendRequest(serverCall);

        } catch (JSONException ignored) {
        }
    }

    /**
     * Method gets a count of entities based on the where clause
     *
     * @param in_where Mongo style query string
     * @param callback Callback.
     */
    public void getListCount(String in_where,
                             IServerCallback callback) {
        try {
            JSONObject data = new JSONObject();

            if (StringUtil.IsOptionalParameterValid(in_where)) {
                JSONObject where = new JSONObject(in_where);
                data.put(Parameter.where.name(), where);
            }

            ServerCall serverCall = new ServerCall(ServiceName.globalEntity,
                    ServiceOperation.GET_LIST_COUNT, data, callback);
            _client.sendRequest(serverCall);

        } catch (JSONException ignored) {
        }
    }

    /**
     * Method uses a paging system to iterate through Global Entities.
     * After retrieving a page of Global Entities with this method,
     * use GetPageOffset() to retrieve previous or next pages.
     *
     * Service Name - GlobalEntity
     * Service Operation - GetPage
     *
     * @param in_jsonContext The json context for the page request.
     * See the portal appendix documentation for format
     * @param callback Callback.
     */
    public void getPage(String in_jsonContext, IServerCallback callback) {
        try {
            JSONObject data = new JSONObject();
            JSONObject context = new JSONObject(in_jsonContext);
            data.put(Parameter.context.name(), context);
            ServerCall serverCall = new ServerCall(ServiceName.globalEntity,
                    ServiceOperation.GET_PAGE, data, callback);
            _client.sendRequest(serverCall);

        } catch (JSONException ignored) {
        }
    }


    /**
     * Method to retrieve previous or next pages after having called
     * the GetPage method.
     *
     * Service Name - GlobalEntity
     * Service Operation - GetPageOffset
     *
     * @param in_context The context string returned from the server from a previous call
     * @param in_pageOffset The positive or negative page offset to fetch. Uses the last page
     * retrieved using the context string to determine a starting point.
     * @param callback Callback.
     */
    public void getPageOffset(String in_context, int in_pageOffset, IServerCallback callback) {
        try {
            JSONObject data = new JSONObject();
            data.put(Parameter.context.name(), in_context);
            data.put(Parameter.pageOffset.name(), in_pageOffset);
            ServerCall serverCall = new ServerCall(ServiceName.globalEntity,
                    ServiceOperation.GET_PAGE_BY_OFFSET, data, callback);
            _client.sendRequest(serverCall);

        } catch (JSONException ignored) {
        }
    }

    /**
     * Partial increment of entity data field items. Partial set of items incremented as specified.
     *
     * Service Name - globalEntity
     * Service Operation - INCREMENT_GLOBAL_ENTITY_DATA
     *
     * @param entityId The id of the entity to update
     * @param jsonData The entity's data object
     * @param returnData Should the entity be returned in the response?
     * @param callback The callback object
     */
    public void incrementGlobalEntityData(String entityId, String jsonData, boolean returnData, IServerCallback callback) {
        try {

            JSONObject data = new JSONObject();
            data.put(Parameter.entityId.name(), entityId);
            data.put(Parameter.data.name(), new JSONObject(jsonData));
            data.put(Parameter.returnData.name(), returnData);

            ServerCall sc = new ServerCall(ServiceName.globalEntity,
                    ServiceOperation.INCREMENT_GLOBAL_ENTITY_DATA, data, callback);
            _client.sendRequest(sc);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
