package com.crux.sdk.model;

public class CruxAddress {
    public String addressHash;
    public String secIdentifier = null;

    public CruxAddress(String addressHash, String secIdentifier) {
        this.addressHash = addressHash;
        this.secIdentifier = secIdentifier;
    }
}
