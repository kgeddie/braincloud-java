package com.bitheads.braincloud.services;

import com.bitheads.braincloud.client.BrainCloudClient;

import org.json.JSONArray;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by prestonjennings on 15-09-02.
 */
public class S3HandlingServiceTest extends TestFixtureBase
{
    private final String _category = "test";

    @Test
    public void testGetUpdatedFiles() throws Exception
    {
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getS3HandlingService().getUpdatedFiles(
                _category,
                getModifiedFileDetails(),
                tr);

        tr.Run();
    }

    @Test
    public void testGetFileList() throws Exception
    {
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getS3HandlingService().getFileList(
                _category,
                tr);

        tr.Run();
    }

    private String getModifiedFileDetails() throws Exception
    {
        TestResult tr = new TestResult();
        String fileDetails = "";

        BrainCloudClient.getInstance().getS3HandlingService().getFileList(
                _category,
                tr);

        if (tr.Run())
        {
            JSONArray files = tr.m_response.getJSONObject("data").getJSONArray("fileDetails");

            if (files.length() <= 0) return "";

            files.getJSONObject(0).put("md5Hash", "d41d8cd98f00b204e9800998ecf8427e");
            fileDetails = files.toString();
        }
        return fileDetails;
    }
}