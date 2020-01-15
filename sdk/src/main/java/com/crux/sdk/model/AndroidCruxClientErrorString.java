package com.crux.sdk.model;

import java.util.HashMap;
import java.util.Map;

public class AndroidCruxClientErrorString {
    public static Map errorCodeToErrorStringMap;

    static {
        errorCodeToErrorStringMap = new HashMap<Integer, String>();
        errorCodeToErrorStringMap.put(AndroidCruxClientErrorCode.getCruxClientInitConfigStringFailed, "Could not initialize cruxClientConfig");
        errorCodeToErrorStringMap.put(AndroidCruxClientErrorCode.getCruxClientDebuggerFailed, "Could not initialize cruxClient as debugger detected");
        errorCodeToErrorStringMap.put(AndroidCruxClientErrorCode.cruxAddressMappingConversionFailed, "Could not create CruxAddressMapping");
    }
}
