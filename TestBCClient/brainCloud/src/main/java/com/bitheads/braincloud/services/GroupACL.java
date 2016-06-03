package com.bitheads.braincloud.services;

import org.json.JSONException;
import org.json.JSONObject;

public class GroupACL {
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
    private Access _member;

    public GroupACL() {
        _other = Access.None;
        _member = Access.None;
    }

    public GroupACL(Access other, Access member) {
        _other = other;
        _member = member;
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

    public Access getMember() {
        return _member;
    }

    public void setMember(Access in_value) {
        _member = in_value;
    }

    public void setMember(int in_value) {
        _member = Access.fromOrdinal(in_value);
    }

    public static GroupACL createFromJson(String in_json) {
        try {
            JSONObject jsonObj = new JSONObject(in_json);
            return createFromJson(jsonObj);
        } catch (JSONException je) {
            je.printStackTrace();
        }

        return new GroupACL();
    }

    public static GroupACL createFromJson(JSONObject in_json) {
        GroupACL acl = new GroupACL();
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
            setMember(in_json.getInt("member"));
        } catch (JSONException je) {
            je.printStackTrace();
        }
    }

    public String toJsonString() {
        try {
            JSONObject acl = new JSONObject();
            acl.put("other", _other.ordinal());
            acl.put("member", _member.ordinal());
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

