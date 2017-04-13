package com.bitheads.braincloud.services;

import com.bitheads.braincloud.client.BrainCloudClient;
import com.bitheads.braincloud.client.IServerCallback;
import com.bitheads.braincloud.client.ServiceName;
import com.bitheads.braincloud.client.ServiceOperation;
import com.bitheads.braincloud.comms.ServerCall;

import org.json.JSONException;
import org.json.JSONObject;

public class ProductService {

    private enum Parameter {
        platform,
        user_currency,
        category,
        vc_id,
        vc_amount,
        language,
        productId,
        orderId,
        token,
        levelName
    }

    private BrainCloudClient _client;

    public ProductService(BrainCloudClient client) {
        _client = client;
    }

    /**
     * Gets the player's currency for the given currency type
     * or all currency types if null passed in.
     *
     * Service Name - Product
     * Service Operation - GetPlayerVC
     *
     * @param in_currencyType The currency type to retrieve or null
     * if all currency types are being requested.
     * @param callback The callback.
     */
    public void getCurrency(String in_currencyType, IServerCallback callback) {
        try {
            JSONObject data = new JSONObject();
            data.put(Parameter.vc_id.name(), in_currencyType);

            ServerCall sc = new ServerCall(ServiceName.product, ServiceOperation.GET_PLAYER_VC, data, callback);
            _client.sendRequest(sc);
        } catch (JSONException je) {
            je.printStackTrace();
        }
    }

    /**
     * Method gets the active sales inventory for the passed-in
     * currency type.
     *
     * Service Name - Product
     * Service Operation - GetInventory
     *
     * @param in_platform The store platform. Valid stores are:
     * - itunes
     * - facebook
     * - appworld
     * - steam
     * - windows
     * - windowsPhone
     * - googlePlay
     * @param in_userCurrency The currency type to retrieve the sales inventory for.
     * @param callback The callback.
     */
    public void getSalesInventory(String in_platform,
                                  String in_userCurrency,
                                  IServerCallback callback) {
        getSalesInventoryByCategory(in_platform, in_userCurrency, null, callback);
    }

    /**
     * Method gets the active sales inventory for the passed-in
     * currency type.
     *
     * Service Name - Product
     * Service Operation - GetInventory
     *
     * @param in_platform The store platform. Valid stores are:
     * - itunes
     * - facebook
     * - appworld
     * - steam
     * - windows
     * - windowsPhone
     * - googlePlay
     * @param in_userCurrency The currency type to retrieve the sales inventory for.
     * @param in_category The product category
     * @param callback The callback.
     */
    public void getSalesInventoryByCategory(String in_platform,
                                            String in_userCurrency,
                                            String in_category,
                                            IServerCallback callback) {
        try {
            JSONObject data = new JSONObject();
            data.put(Parameter.platform.name(), in_platform);

            if (StringUtil.IsOptionalParameterValid(in_userCurrency)) {
                data.put(Parameter.user_currency.name(), in_userCurrency);
            }
            if (StringUtil.IsOptionalParameterValid(in_category)) {
                data.put(Parameter.category.name(), in_category);
            }

            ServerCall sc = new ServerCall(ServiceName.product, ServiceOperation.GET_INVENTORY, data, callback);
            _client.sendRequest(sc);
        } catch (JSONException je) {
            je.printStackTrace();
        }
    }

    /**
     * Returns the eligible promotions for the player.
     *
     * Service Name - Product
     * Service Operation - EligiblePromotions
     *
     * @param callback The callback.
     */
    public void getEligiblePromotions(IServerCallback callback) {
        ServerCall sc = new ServerCall(ServiceName.product, ServiceOperation.ELIGIBLE_PROMOTIONS, null, callback);
        _client.sendRequest(sc);
    }

    /**
     * Confirm GooglePlay Purchase. On success, the player will be awarded the
     * associated currencies.
     *
     * Service Name - product
     * Service Operation - CONFIRM_GOOGLEPLAY_PURCHASE
     *
     * @param in_orderId   GooglePlay order id
     * @param in_productId  GooglePlay product id
     * @param in_token   GooglePlay token string
     * @param in_callback  The callback.
     */
    public void confirmGooglePlayPurchase(String in_orderId, String in_productId, String in_token, IServerCallback in_callback) {
        JSONObject data = new JSONObject();
        try {
            data.put(Parameter.orderId.name(), in_orderId);
            data.put(Parameter.productId.name(), in_productId);
            data.put(Parameter.token.name(), in_token);
        } catch (JSONException je) {
            je.printStackTrace();
        }
        ServerCall sc = new ServerCall(ServiceName.product, ServiceOperation.CONFIRM_GOOGLEPLAY_PURCHASE, data, in_callback);
        _client.sendRequest(sc);
    }

    /**
     * Awards currency in a parent app.
     *
     * Service Name - Product
     * Service Operation - AWARD_PARENT_VC
     *
     * @param in_currencyType The ID of the parent currency
     * @param in_amount The amount of currency to award
     * @param in_parentLevel The level of the parent containing the currency
     * @param in_callback The method to be invoked when the server response is received
     */
    public void awardParentCurrency(String in_currencyType,
                                    long in_amount,
                                    String in_parentLevel,
                                    IServerCallback in_callback) {
        try {
            JSONObject data = new JSONObject();
            data.put(Parameter.vc_id.name(), in_currencyType);
            data.put(Parameter.vc_amount.name(), in_amount);
            data.put(Parameter.levelName.name(), in_parentLevel);

            ServerCall sc = new ServerCall(ServiceName.product, ServiceOperation.AWARD_PARENT_VC, data, in_callback);
            _client.sendRequest(sc);
        } catch (JSONException je) {
            je.printStackTrace();
        }
    }

    /**
     * Consumes currency in a parent app.
     *
     * Service Name - Product
     * Service Operation - CONSUME_PARENT_VC
     *
     * @param in_currencyType The ID of the parent currency
     * @param in_amount The amount of currency to consume
     * @param in_parentLevel The level of the parent containing the currency
     * @param in_callback The method to be invoked when the server response is received
     */
    public void consumeParentCurrency(String in_currencyType,
                                      long in_amount,
                                      String in_parentLevel,
                                      IServerCallback in_callback) {
        try {
            JSONObject data = new JSONObject();
            data.put(Parameter.vc_id.name(), in_currencyType);
            data.put(Parameter.vc_amount.name(), in_amount);
            data.put(Parameter.levelName.name(), in_parentLevel);

            ServerCall sc = new ServerCall(ServiceName.product, ServiceOperation.CONSUME_PARENT_VC, data, in_callback);
            _client.sendRequest(sc);
        } catch (JSONException je) {
            je.printStackTrace();
        }
    }

    /**
     * Gets information on a currency in a parent app.
     *
     * Service Name - Product
     * Service Operation - GET_PARENT_VC
     *
     * @param in_currencyType The ID of the parent currency or null to retrieve all parent currencies
     * @param in_parentLevel The level of the parent containing the currency
     * @param in_callback The method to be invoked when the server response is received
     */
    public void getParentCurrency(String in_currencyType,
                                  String in_parentLevel,
                                  IServerCallback in_callback) {
        try {
            JSONObject data = new JSONObject();
            if (StringUtil.IsOptionalParameterValid(in_currencyType)) {
                data.put(Parameter.vc_id.name(), in_currencyType);
            }
            data.put(Parameter.levelName.name(), in_parentLevel);

            ServerCall sc = new ServerCall(ServiceName.product, ServiceOperation.GET_PARENT_VC, data, in_callback);
            _client.sendRequest(sc);
        } catch (JSONException je) {
            je.printStackTrace();
        }
    }

    /**
     * Resets all currencies in a parent app.
     *
     * Service Name - Product
     * Service Operation - RESET_PARENT_VC
     *
     * @param in_parentLevel The level of the parent containing the currencies
     * @param in_callback The method to be invoked when the server response is received
     */
    public void resetParentCurrency(String in_parentLevel,
                                    IServerCallback in_callback) {
        try {
            JSONObject data = new JSONObject();
            data.put(Parameter.levelName.name(), in_parentLevel);

            ServerCall sc = new ServerCall(ServiceName.product, ServiceOperation.RESET_PARENT_VC, data, in_callback);
            _client.sendRequest(sc);
        } catch (JSONException je) {
            je.printStackTrace();
        }
    }

}
