package com.bitheads.braincloud.services;

import com.bitheads.braincloud.client.BrainCloudClient;
import com.bitheads.braincloud.client.IFileUploadCallback;

import junit.framework.Assert;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.RandomAccessFile;

/**
 * Created by bradleyh on 3/30/2016.
 */
public class FileServiceTest extends TestFixtureBase implements IFileUploadCallback {

    private int _returnCount = 0;
    private int _failCount = 0;

    @Before
    public void Setup() {
        _returnCount = 0;
        _failCount = 0;
        BrainCloudClient.getInstance().registerFileUploadCallback(this);
    }

    @After
    public void Teardown() {
        BrainCloudClient.getInstance().deregisterFileUploadCallback();
    }

    @Test
    public void testListUserFiles() throws Exception {
        TestResult tr = new TestResult();
        BrainCloudClient.getInstance().getFileService().listUserFiles(tr);
        tr.Run();
    }

    @Test
    public void testListUserFilesDirectory() throws Exception {
        TestResult tr = new TestResult();
        BrainCloudClient.getInstance().getFileService().listUserFiles(
                "/test/", true,
                tr);
        tr.Run();
    }

    @Test
    public void testDeleteUserFile() throws Exception {
        TestResult tr = new TestResult();
        String localPath = createFile(1);
        String fileName = "testFile";

        BrainCloudClient.getInstance().getFileService().uploadFile(
                "", fileName, true, true, localPath, tr);
        tr.Run();
        if (tr.m_result) {
            String id = getUploadId(tr.m_response);
            waitForReturn(new String[]{id}, false);
        }

        Assert.assertEquals(0, _failCount);
        Assert.assertEquals(1, _returnCount);

        BrainCloudClient.getInstance().getFileService().deleteUserFile(
                "", fileName, tr);
        tr.Run();
    }

    @Test
    public void testGetCdnUrl() throws Exception {
        TestResult tr = new TestResult();
        String localPath = createFile(1);
        String fileName = "testFile";

        BrainCloudClient.getInstance().getFileService().uploadFile(
                "", fileName, true, true, localPath, tr);
        tr.Run();
        if (tr.m_result) {
            String id = getUploadId(tr.m_response);
            waitForReturn(new String[]{id}, false);
        }

        Assert.assertEquals(0, _failCount);
        Assert.assertEquals(1, _returnCount);

        BrainCloudClient.getInstance().getFileService().getCDNUrl(
                "", fileName, tr);
        tr.Run();

        BrainCloudClient.getInstance().getFileService().deleteUserFile(
                "", fileName, tr);
        tr.Run();
    }

    @Test
    public void testDeleteUserFiles() throws Exception {
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getFileService().deleteUserFiles(
                "", true, tr);
        tr.Run();
    }

    @Test
    public void testUploadSingleFile() throws Exception {
        TestResult tr = new TestResult();

        String path = createFile(10);

        BrainCloudClient.getInstance().getFileService().uploadFile(
                "", "test.dat", true, true, path, tr);

        tr.Run();
        if (tr.m_result) {
            String id = getUploadId(tr.m_response);
            waitForReturn(new String[]{id}, false);
        }

        Assert.assertEquals(0, _failCount);
        Assert.assertEquals(1, _returnCount);
    }

    @Test
    public void testUploadCancel() throws Exception {
        TestResult tr = new TestResult();

        String path = createFile(10);

        BrainCloudClient.getInstance().getFileService().uploadFile(
                "", "test.dat", true, true, path, tr);

        tr.Run();
        if (tr.m_result) {
            String id = getUploadId(tr.m_response);
            waitForReturn(new String[]{id}, true);
        }

        Assert.assertEquals(1, _failCount);
        Assert.assertEquals(1, _returnCount);
    }

    private void waitForReturn(String[] uploadIds, Boolean cancelUpload) throws Exception {
        FileService service = BrainCloudClient.getInstance().getFileService();
        int count = 0;
        Boolean sw = true;

        while (_returnCount < uploadIds.length && count < 1000 * 30) {
            BrainCloudClient.getInstance().runCallbacks();
            for (String id : uploadIds) {
                double progress = service.getUploadProgress(id);

                if (progress > -1 && sw) {
                    String logStr = "File " + id + " Progress: " +
                            progress + " | " +
                            service.getUploadBytesTransferred(id) + "/" +
                            service.getUploadTotalBytesToTransfer(id);
                    System.out.println(logStr);
                }

                if (cancelUpload && progress > 0.05)
                    service.cancelUpload(id);
            }

            sw = !sw;
            count += 100;
            Thread.sleep(100);
        }
    }

    private String getUploadId(JSONObject response) throws Exception {
        return response.getJSONObject("data").getJSONObject("fileDetails").getString("uploadId");
    }

    private String createFile(int fileSizeMb) throws Exception {
        File tempFile = File.createTempFile("test", ".dat");
        tempFile.deleteOnExit();

        RandomAccessFile raf = new RandomAccessFile(tempFile, "rw");

        int fileSize = fileSizeMb * 1024 * 1024;
        raf.setLength(fileSize);
        raf.close();

        return tempFile.getCanonicalPath();
    }

    @Override
    public void fileUploadCompleted(String fileUploadId, String jsonResponse) {
        _returnCount++;
    }

    @Override
    public void fileUploadFailed(String fileUploadId, int statusCode, int reasonCode, String jsonResponse) {
        _returnCount++;
        _failCount++;
    }
}
