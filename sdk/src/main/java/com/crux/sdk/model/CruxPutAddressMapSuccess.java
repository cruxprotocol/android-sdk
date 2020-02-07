package com.crux.sdk.model;

import java.util.HashMap;

public class CruxPutAddressMapSuccess {
    private HashMap<String, CruxAddress> success;
    private HashMap<String, String> failures;

    public CruxPutAddressMapSuccess(HashMap<String, CruxAddress> success, HashMap<String, String> failures) {
        this.success = success;
        this.failures = failures;
    }
}
