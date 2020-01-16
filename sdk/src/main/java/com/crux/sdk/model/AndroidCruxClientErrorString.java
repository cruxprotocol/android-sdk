package com.crux.sdk.model;

import java.util.HashMap;
import java.util.Map;

public class AndroidCruxClientErrorString {
    public static Map errorCodeToErrorStringMap;

    static {
        errorCodeToErrorStringMap = new HashMap<Integer, String>();
        errorCodeToErrorStringMap.put(AndroidCruxClientErrorCode.getCruxClientInitConfigStringFailed, "Could not initialize cruxClientConfig");
        errorCodeToErrorStringMap.put(AndroidCruxClientErrorCode.runningInUnsafeEnvironment, "CRUX SDK should not run in unsafe environment");
        errorCodeToErrorStringMap.put(AndroidCruxClientErrorCode.cruxAddressMappingConversionFailed, "Could not create CruxAddressMapping");
    }
}
