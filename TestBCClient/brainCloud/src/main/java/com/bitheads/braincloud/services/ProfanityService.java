package com.bitheads.braincloud.services;

import com.bitheads.braincloud.client.BrainCloudClient;
import com.bitheads.braincloud.client.IServerCallback;
import com.bitheads.braincloud.client.ServiceName;
import com.bitheads.braincloud.client.ServiceOperation;
import com.bitheads.braincloud.comms.ServerCall;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by prestonjennings on 15-12-14.
 */
public class ProfanityService {

    private enum Parameter {
        text,
        languages,
        flagEmail,
        flagPhone,
        flagUrls,
        replaceSymbol
    }

    private BrainCloudClient _client;

    public ProfanityService(BrainCloudClient client) {
        _client = client;
    }

    /**
     * Checks supplied text for profanity.
     *
     * Service Name - Profanity
     * Service Operation - ProfanityCheck
     *
     * @param in_text The text to check
     * @param in_languages Optional comma delimited list of two character language codes
     * @param in_flagEmail Optional processing of email addresses
     * @param in_flagPhone Optional processing of phone numbers
     * @param in_flagUrls Optional processing of urls
     * @param in_callback The method to be invoked when the server response is received
     *
     * Significant error codes:
     *
     * 40421 - WebPurify not configured
     * 40422 - General exception occurred
     * 40423 - WebPurify returned an error (Http status != 200)
     * 40424 - WebPurify not enabled
     */
    public void profanityCheck(
        String in_text,
        String in_languages,
        boolean in_flagEmail,
        boolean in_flagPhone,
        boolean in_flagUrls,
        IServerCallback in_callback) {
        try {
            JSONObject data = new JSONObject();
            data.put(Parameter.text.name(), in_text);
            if (in_languages != null) {
                data.put(Parameter.languages.name(), in_languages);
            }
            data.put(Parameter.flagEmail.name(), in_flagEmail);
            data.put(Parameter.flagPhone.name(), in_flagPhone);
            data.put(Parameter.flagUrls.name(), in_flagUrls);

            ServerCall sc = new ServerCall(ServiceName.profanity, ServiceOperation.PROFANITY_CHECK, data, in_callback);
            _client.sendRequest(sc);
        } catch (JSONException je) {
            je.printStackTrace();
        }
    }


    /**
     * Replaces the characters of profanity text with a passed character(s).
     *
     * Service Name - Profanity
     * Service Operation - ProfanityReplaceText
     *
     * @param in_text The text to check
     * @param in_replaceSymbol The text to replace individual characters of profanity text with
     * @param in_languages Optional comma delimited list of two character language codes
     * @param in_flagEmail Optional processing of email addresses
     * @param in_flagPhone Optional processing of phone numbers
     * @param in_flagUrls Optional processing of urls
     * @param in_callback The method to be invoked when the server response is received
     *
     * Significant error codes:
     *
     * 40421 - WebPurify not configured
     * 40422 - General exception occurred
     * 40423 - WebPurify returned an error (Http status != 200)
     * 40424 - WebPurify not enabled
     */
    public void profanityReplaceText(
        String in_text,
        String in_replaceSymbol,
        String in_languages,
        boolean in_flagEmail,
        boolean in_flagPhone,
        boolean in_flagUrls,
        IServerCallback in_callback) {
        try {
            JSONObject data = new JSONObject();
            data.put(Parameter.text.name(), in_text);
            data.put(Parameter.replaceSymbol.name(), in_replaceSymbol);
            if (in_languages != null) {
                data.put(Parameter.languages.name(), in_languages);
            }
            data.put(Parameter.flagEmail.name(), in_flagEmail);
            data.put(Parameter.flagPhone.name(), in_flagPhone);
            data.put(Parameter.flagUrls.name(), in_flagUrls);

            ServerCall sc = new ServerCall(ServiceName.profanity, ServiceOperation.PROFANITY_CHECK, data, in_callback);
            _client.sendRequest(sc);
        } catch (JSONException je) {
            je.printStackTrace();
        }
    }


    /**
     * Checks supplied text for profanity and returns a list of bad wors.
     *
     * Service Name - Profanity
     * Service Operation - ProfanityIdentifyBadWords
     *
     * @param in_text The text to check
     * @param in_languages Optional comma delimited list of two character language codes
     * @param in_flagEmail Optional processing of email addresses
     * @param in_flagPhone Optional processing of phone numbers
     * @param in_flagUrls Optional processing of urls
     * @param in_callback The method to be invoked when the server response is received
     *
     * Significant error codes:
     *
     * 40421 - WebPurify not configured
     * 40422 - General exception occurred
     * 40423 - WebPurify returned an error (Http status != 200)
     * 40424 - WebPurify not enabled
     */
    void profanityIdentifyBadWords(
        String in_text,
        String in_languages,
        boolean in_flagEmail,
        boolean in_flagPhone,
        boolean in_flagUrls,
        IServerCallback in_callback) {
        try {
            JSONObject data = new JSONObject();
            data.put(Parameter.text.name(), in_text);
            if (in_languages != null) {
                data.put(Parameter.languages.name(), in_languages);
            }
            data.put(Parameter.flagEmail.name(), in_flagEmail);
            data.put(Parameter.flagPhone.name(), in_flagPhone);
            data.put(Parameter.flagUrls.name(), in_flagUrls);

            ServerCall sc = new ServerCall(ServiceName.profanity, ServiceOperation.PROFANITY_IDENTIFY_BAD_WORDS, data, in_callback);
            _client.sendRequest(sc);
        } catch (JSONException je) {
            je.printStackTrace();
        }
    }
}
