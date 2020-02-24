package com.crux.sdk.model;

import java.util.HashMap;

public class CruxPutAddressMapSuccess {
    public HashMap<String, CruxAddress> success;
    public HashMap<String, String> failures;

    public CruxPutAddressMapSuccess(HashMap<String, CruxAddress> success, HashMap<String, String> failures) {
        this.success = success;
        this.failures = failures;
    }
}
