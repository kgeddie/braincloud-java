package com.bitheads.braincloud.services;

import com.bitheads.braincloud.client.BrainCloudClient;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by prestonjennings on 15-09-02.
 */
public class ScriptServiceTest extends TestFixtureBase
{
    private final String _scriptName = "testScript";

    @Test
    public void testRunScript() throws Exception
    {
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getScriptService().runScript(
                _scriptName,
                Helpers.createJsonPair("testParm1", 1),
                tr);

        tr.Run();
    }

    @Test
    public void testScheduleRunScriptUTC() throws Exception
    {
        TestResult tr = new TestResult();

        Date date = new Date();
        date.setTime(date.getTime() + 120 * 1000);
        BrainCloudClient.getInstance().getScriptService().scheduleRunScriptUTC(
                _scriptName,
                Helpers.createJsonPair("testParm1", 1),
                date,
                tr);

        tr.Run();
    }

    @Test
    public void testScheduleRunScriptMinutes() throws Exception
    {
        TestResult tr = new TestResult();

        BrainCloudClient.getInstance().getScriptService().scheduleRunScriptMinutes(
                _scriptName,
                Helpers.createJsonPair("testParm1", 1),
                60,
                tr);

        tr.Run();
    }

    @Test
    public void testRunParentScript() throws Exception
    {
        goToChildProfile();

        TestResult tr = new TestResult();
        BrainCloudClient.getInstance().getScriptService().runParentScript(
                _scriptName,
                Helpers.createJsonPair("testParm1", 1),
                m_parentLevelName,
                tr);
        tr.Run();
    }
}