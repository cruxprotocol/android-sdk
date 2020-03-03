package com.crux.sdk.model;

import java.util.HashMap;

public class CruxPutPrivateAddressMapResult {
    public HashMap<String, String>[] failures;

    public CruxPutPrivateAddressMapResult(HashMap<String, String>[] failures) {
        this.failures = failures;
    }
}
