package com.bitheads.braincloud.services;

import com.bitheads.braincloud.client.BrainCloudClient;
import com.bitheads.braincloud.client.IServerCallback;
import com.bitheads.braincloud.client.ServiceName;
import com.bitheads.braincloud.client.ServiceOperation;
import com.bitheads.braincloud.comms.ServerCall;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class ScriptService {

    public enum Parameter {
        scriptName,
        scriptData,
        startDateUTC,
        minutesFromNow,
        parentLevel
    }

    private BrainCloudClient _client;

    public ScriptService(BrainCloudClient client) {
        _client = client;
    }

    /**
     * Executes a script on the server.
     *
     * Service Name - Script
     * Service Operation - Run
     *
     * @param in_scriptName The name of the script to be run
     * @param in_jsonScriptData Data to be sent to the script in json format
     * @see The API documentation site for more details on cloud code
     */
    public void runScript(String in_scriptName, String in_jsonScriptData, IServerCallback callback) {

        try {
            JSONObject data = new JSONObject();
            data.put(Parameter.scriptName.name(), in_scriptName);

            if (StringUtil.IsOptionalParameterValid(in_jsonScriptData)) {
                JSONObject jsonData = new JSONObject(in_jsonScriptData);
                data.put(Parameter.scriptData.name(), jsonData);
            }

            ServerCall sc = new ServerCall(ServiceName.script, ServiceOperation.RUN, data, callback);
            _client.sendRequest(sc);
        } catch (JSONException je) {
            je.printStackTrace();
        }
    }

    /**
     * Allows cloud script executions to be scheduled
     *
     * Service Name - Script
     * Service Operation - ScheduleCloudScript
     *
     * @param in_scriptName The name of the script to be run
     * @param in_jsonScriptData JSON bundle to pass to script
     * @param in_startTimeUTC The start date as a Date object
     * @see The API documentation site for more details on cloud code
     */
    public void scheduleRunScriptUTC(String in_scriptName, String in_jsonScriptData, Date in_startTimeUTC, IServerCallback callback) {

        try {
            JSONObject data = new JSONObject();
            data.put(Parameter.scriptName.name(), in_scriptName);

            if (StringUtil.IsOptionalParameterValid(in_jsonScriptData)) {
                JSONObject jsonData = new JSONObject(in_jsonScriptData);
                data.put(Parameter.scriptData.name(), jsonData);
            }

            data.put(Parameter.startDateUTC.name(), in_startTimeUTC.getTime());

            ServerCall sc = new ServerCall(ServiceName.script, ServiceOperation.SCHEDULE_CLOUD_SCRIPT, data, callback);
            _client.sendRequest(sc);
        } catch (JSONException je) {
            je.printStackTrace();
        }
    }

    /**
     * Allows cloud script executions to be scheduled
     *
     * Service Name - Script
     * Service Operation - ScheduleCloudScript
     *
     * @param in_scriptName The name of the script to be run
     * @param in_jsonScriptData JSON bundle to pass to script
     * @param in_minutesFromNow Number of minutes from now to run script
     * @see The API documentation site for more details on cloud code
     */
    public void scheduleRunScriptMinutes(String in_scriptName, String in_jsonScriptData, int in_minutesFromNow, IServerCallback callback) {

        try {
            JSONObject data = new JSONObject();
            data.put(Parameter.scriptName.name(), in_scriptName);

            if (StringUtil.IsOptionalParameterValid(in_jsonScriptData)) {
                JSONObject jsonData = new JSONObject(in_jsonScriptData);
                data.put(Parameter.scriptData.name(), jsonData);
            }

            data.put(Parameter.minutesFromNow.name(), in_minutesFromNow);

            ServerCall sc = new ServerCall(ServiceName.script, ServiceOperation.SCHEDULE_CLOUD_SCRIPT, data, callback);
            _client.sendRequest(sc);
        } catch (JSONException je) {
            je.printStackTrace();
        }
    }


    /**
     * Run a cloud script in a parent app
     *
     * Service Name - Script
     * Service Operation - RUN_PARENT_SCRIPT
     *
     * @param in_scriptName The name of the script to be run
     * @param in_scriptData Data to be sent to the script in json format
     * @param in_parentLevel The level name of the parent to run the script from
     * @param in_callback The method to be invoked when the server response is received
     * @see The API documentation site for more details on cloud code
     */
    public void runParentScript(String in_scriptName,
                                String in_scriptData,
                                String in_parentLevel,
                                IServerCallback callback) {
        try {
            JSONObject data = new JSONObject();
            data.put(Parameter.scriptName.name(), in_scriptName);
            data.put(Parameter.parentLevel.name(), in_parentLevel);

            if (StringUtil.IsOptionalParameterValid(in_scriptData)) {
                JSONObject jsonData = new JSONObject(in_scriptData);
                data.put(Parameter.scriptData.name(), jsonData);
            }

            ServerCall sc = new ServerCall(ServiceName.script, ServiceOperation.RUN_PARENT_SCRIPT, data, callback);
            _client.sendRequest(sc);
        } catch (JSONException je) {
            je.printStackTrace();
        }
    }


}
