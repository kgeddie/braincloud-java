package com.bitheads.braincloud.services;

import android.util.JsonWriter;

import com.bitheads.braincloud.client.BrainCloudClient;

import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by prestonjennings on 15-09-02.
 */
public class GlobalEntityServiceTest extends TestFixtureBase
{
    private final String _defaultEntityType = "testGlobalEntity";
    private final String _defaultEntityValueName = "globalTestName";
    private final String _defaultEntityValue = "Test Name 01";

    @Test
    public void testCreateEntity() throws Exception
    {
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getGlobalEntityService().createEntity(
                _defaultEntityType,
                0,
                null,
                Helpers.createJsonPair(_defaultEntityValueName, _defaultEntityValue),
                tr);

        tr.Run();
    }

    @Test
    public void testCreateEntityWithIndexedId() throws Exception
    {
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getGlobalEntityService().createEntityWithIndexedId(
                _defaultEntityType,
                "indexedIdTest",
                0,
                null,
                Helpers.createJsonPair(_defaultEntityValueName, _defaultEntityValue),
                tr);

        tr.Run();
    }

    @Test
    public void testUpdateEntity() throws Exception
    {
        TestResult tr = new TestResult();

        String entityId = createDefaultGlobalEntity();

        BrainCloudClient.getInstance().getGlobalEntityService().updateEntity(
                entityId,
                1,
                Helpers.createJsonPair(_defaultEntityValueName, "Test Name 02 Changed"),
                true,
                tr);

        tr.Run();
    }

    @Test
    public void testUpdateEntityAcl() throws Exception
    {
        TestResult tr = new TestResult();

        String entityId = createDefaultGlobalEntity();

        BrainCloudClient.getInstance().getGlobalEntityService().updateEntityAcl(
                entityId,
                1,
                ACL.readWriteOther().toJsonString(),
                true,
            tr);

        tr.Run();
    }

    @Test
    public void testUpdateEntityTimeToLive() throws Exception
    {
        TestResult tr = new TestResult();

        String entityId = createDefaultGlobalEntity();

        BrainCloudClient.getInstance().getGlobalEntityService().updateEntityTimeToLive(
                entityId,
                1,
                1000,
                true,
                tr);

        tr.Run();
    }

    @Test
    public void testDeleteEntity() throws Exception
    {
        TestResult tr = new TestResult();

        String entityId = createDefaultGlobalEntity();

        BrainCloudClient.getInstance().getGlobalEntityService().deleteEntity(
                entityId,
                1,
                tr);

        tr.Run();
    }

    @Test
    public void testReadEntity() throws Exception
    {
        TestResult tr = new TestResult();

        String entityId = createDefaultGlobalEntity();

        BrainCloudClient.getInstance().getGlobalEntityService().readEntity(
                entityId,
                tr);

        tr.Run();
    }

    @Test
    public void testGetList() throws Exception
    {
        TestResult tr = new TestResult();

        createDefaultGlobalEntity();
        createDefaultGlobalEntity();

        BrainCloudClient.getInstance().getGlobalEntityService().getList(
                Helpers.createJsonPair("entityType", _defaultEntityType),
                Helpers.createJsonPair("data.name", 1),
                1000,
                tr);

        tr.Run();
    }

    @Test
    public void testGetListByIndexedId() throws Exception
    {
        TestResult tr = new TestResult();

        String indexedId = "testIndexedId";

        createDefaultGlobalEntity(ACL.Access.None, indexedId);
        createDefaultGlobalEntity(ACL.Access.None, indexedId);

        BrainCloudClient.getInstance().getGlobalEntityService().getListByIndexedId(
                indexedId,
                100,
                tr);

        tr.Run();
    }

    @Test
    public void testGetListCount() throws Exception
    {
        TestResult tr = new TestResult();

        createDefaultGlobalEntity();
        createDefaultGlobalEntity();

        BrainCloudClient.getInstance().getGlobalEntityService().getListCount(
                Helpers.createJsonPair("entityType", _defaultEntityType),
                tr);

        tr.Run();
    }

    @Test
    public void testGetPage() throws Exception
    {
        TestResult tr = new TestResult();

        generateDefaultEntitites(200);

        BrainCloudClient.getInstance().getGlobalEntityService().getPage(
                createContext(125, 1, _defaultEntityType),
                tr);

        tr.Run();
    }

    @Test
    public void testGetPageOffset() throws Exception
    {
        TestResult tr = new TestResult();
        generateDefaultEntitites(200);

        BrainCloudClient.getInstance().getGlobalEntityService().getPage(
                createContext(50, 1, _defaultEntityType),
                tr);
        tr.Run();

        int page = 0;
        page = tr.m_response.getJSONObject("data").getJSONObject("results").getInt("page");

        String context = tr.m_response.getJSONObject("data").getString("context");

        BrainCloudClient.getInstance().getGlobalEntityService().getPageOffset(
                context,
                page,
                tr);

        tr.Run();
    }

    @Test
    public void testIncrementGlobalEntityData() throws Exception {
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getGlobalEntityService().createEntity(
                _defaultEntityType,
                10000,
                "",
                Helpers.createJsonPair("test", 1234),
                tr);
        tr.Run();

        String entityId = getEntityId(tr.m_response);

        BrainCloudClient.getInstance().getGlobalEntityService().incrementGlobalEntityData(
                entityId,
                Helpers.createJsonPair("test", 1234),
                true,
                true,
                tr);
        tr.Run();
    }
    
    
    ////// helpers

    private static String getEntityId(JSONObject json) throws Exception
    {
        return json.getJSONObject("data").getString("entityId");
    }

    private String createDefaultGlobalEntity() throws Exception
    {
        return createDefaultGlobalEntity(ACL.Access.None);
    }

    private String createDefaultGlobalEntity(ACL.Access accessLevel) throws Exception
    {
        return createDefaultGlobalEntity(accessLevel, "");
    }

    /// <summary>
    /// Creates a default entity on the server
    /// </summary>
    /// <param name="accessLevel"> accessLevel for entity </param>
    /// <returns> The ID of the entity </returns>
    private String createDefaultGlobalEntity(ACL.Access accessLevel, String indexedId) throws Exception
    {
        TestResult tr = new TestResult();

        ACL access = new ACL();
        access.setOther(accessLevel);
        String entityId = "";

        //Create entity
        if (indexedId.length() <= 0)
        {
            BrainCloudClient.getInstance().getGlobalEntityService().createEntity(
                    _defaultEntityType,
                    0,
                    access.toJsonString(),
                    Helpers.createJsonPair(_defaultEntityValueName, _defaultEntityValue),
                    tr);
        }
        else
        {
            BrainCloudClient.getInstance().getGlobalEntityService().createEntityWithIndexedId(
                    _defaultEntityType,
                    indexedId,
                    0,
                    access.toJsonString(),
                    Helpers.createJsonPair(_defaultEntityValueName, _defaultEntityValue),
                    tr);
        }

        if (tr.Run())
        {
            entityId = getEntityId(tr.m_response);
        }

        return entityId;
    }

    private String createContext(int numberOfEntitiesPerPage, int startPage, String entityType) throws Exception
    {
        JSONObject context = new JSONObject();

        JSONObject pagination = new JSONObject();
        pagination.put("rowsPerPage", numberOfEntitiesPerPage);
        pagination.put("pageNumber", startPage);
        context.put("pagination", pagination);

        JSONObject searchCriteria = new JSONObject();
        searchCriteria.put("entityType", entityType);
        context.put("searchCriteria", searchCriteria);

        return context.toString();
    }

    private void generateDefaultEntitites(int numberOfEntites) throws Exception
    {
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getGlobalEntityService().getListCount(
                Helpers.createJsonPair("entityType", _defaultEntityType),
                tr);

        tr.Run();

        int existing = tr.m_response.getJSONObject("data").getInt("entityListCount");

        numberOfEntites -= existing;
        if (numberOfEntites <= 0) return;

        for (int i = 0; i < numberOfEntites; i++)
        {
            createDefaultGlobalEntity(ACL.Access.ReadWrite);
        }
    }

}