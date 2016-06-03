package com.bitheads.braincloud.services;

public class StringUtil {

    public static boolean IsOptionalParameterValid(String in_param) {
        return !(in_param == null || in_param.length() == 0);
    }
}
