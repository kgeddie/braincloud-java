package com.bitheads.braincloud.services;

import com.bitheads.braincloud.client.BrainCloudClient;
import com.bitheads.braincloud.client.IServerCallback;
import com.bitheads.braincloud.client.ServiceName;
import com.bitheads.braincloud.client.ServiceOperation;
import com.bitheads.braincloud.comms.ServerCall;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by prestonjennings on 2015-10-27.
 */
public class RedemptionCodeService {

    private enum Parameter {
        scanCode,
        codeType,
        customRedemptionInfo
    }

    private BrainCloudClient _client;

    public RedemptionCodeService(BrainCloudClient client) {
        _client = client;
    }

    /**
     * Redeem a code.
     *
     * Service Name - RedemptionCode
     * Service Operation - REDEEM_CODE
     *
     * @param in_scanCode The code to redeem
     * @param in_codeType The type of code
     * @param in_jsonCustomRedemptionInfo Optional - A JSON string containing custom redemption data
     * @param in_callback The method to be invoked when the server response is received
     */
    public void redeemCode(String in_scanCode, String in_codeType, String in_jsonCustomRedemptionInfo, IServerCallback in_callback) {
        try {
            JSONObject data = new JSONObject();
            data.put(Parameter.scanCode.name(), in_scanCode);
            data.put(Parameter.codeType.name(), in_codeType);
            if (StringUtil.IsOptionalParameterValid(in_jsonCustomRedemptionInfo)) {
                JSONObject infoObj = new JSONObject(in_jsonCustomRedemptionInfo);
                data.put(Parameter.customRedemptionInfo.name(), infoObj);
            }

            ServerCall sc = new ServerCall(ServiceName.redemptionCode, ServiceOperation.REDEEM_CODE, data, in_callback);
            _client.sendRequest(sc);
        } catch (JSONException je) {
            je.printStackTrace();
        }
    }

    /**
     * Retrieve the codes already redeemed by player.
     *
     * Service Name - RedemptionCode
     * Service Operation - GET_REDEEMED_CODES
     *
     * @param in_codeType Optional - The type of codes to retrieve. Returns all codes if left unspecified.
     * @param in_callback The method to be invoked when the server response is received
     */
    public void getRedeemedCodes(String in_codeType, IServerCallback in_callback) {
        try {
            JSONObject data = new JSONObject();
            if (StringUtil.IsOptionalParameterValid(in_codeType)) {
                data.put(Parameter.codeType.name(), in_codeType);
            }

            ServerCall sc = new ServerCall(ServiceName.redemptionCode, ServiceOperation.GET_REDEEMED_CODES, data, in_callback);
            _client.sendRequest(sc);
        } catch (JSONException je) {
            je.printStackTrace();
        }
    }
}
