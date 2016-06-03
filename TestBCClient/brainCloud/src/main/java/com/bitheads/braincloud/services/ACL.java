package com.bitheads.braincloud.services;

import org.json.JSONException;
import org.json.JSONObject;

public class ACL {
    public enum Access {
        None,
        ReadOnly,
        ReadWrite;

        public static Access fromOrdinal(int val) {
            for (int i = 0, ilen = values().length; i < ilen; ++i) {
                if (i == val) {
                    return values()[i];
                }
            }
            return None;
        }
    }

    private Access _other;

    public ACL() {
        _other = Access.None;
    }

    public Access getOther() {
        return _other;
    }

    public void setOther(Access in_value) {
        _other = in_value;
    }

    public void setOther(int in_value) {
        _other = Access.fromOrdinal(in_value);
    }

    public static ACL readOnlyOther() {
        ACL acl = new ACL();
        acl._other = Access.ReadOnly;
        return acl;
    }

    public static ACL readWriteOther() {
        ACL acl = new ACL();
        acl._other = Access.ReadWrite;
        return acl;
    }

    public static ACL createFromJson(String in_json) {
        try {
            JSONObject jsonObj = new JSONObject(in_json);
            return createFromJson(jsonObj);
        } catch (JSONException je) {
            je.printStackTrace();
        }

        return new ACL();
    }

    public static ACL createFromJson(JSONObject in_json) {
        ACL acl = new ACL();
        acl.readFromJson(in_json);
        return acl;
    }

    public void readFromJson(String in_json) {
        try {
            JSONObject jsonObj = new JSONObject(in_json);
            readFromJson(jsonObj);
        } catch (JSONException je) {
            je.printStackTrace();
        }
    }

    public void readFromJson(JSONObject in_json) {
        try {
            setOther(in_json.getInt("other"));
        } catch (JSONException je) {
            je.printStackTrace();
        }
    }

    public String toJsonString() {
        try {
            JSONObject acl = new JSONObject();
            acl.put("other", _other.ordinal());
            return acl.toString();
        } catch (JSONException je) {
            je.printStackTrace();
        }
        return "";
    }

    @Override
    public String toString() {
        return toJsonString();
    }
}

