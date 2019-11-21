package com.crux.sdk.model;

import org.json.JSONException;
import org.json.JSONObject;

public class CruxClientInitConfig {

    public static class Builder {

        //        private String storage;
        //        private String encryption;
        //        private String nameService;
        private String _encryptionKeyValue;
        private String walletClientName;
        private String privateKey;

        public Builder() {
            this._encryptionKeyValue = "fookey";
        }

        public Builder setWalletClientName(String walletClientName) {
            this.walletClientName = walletClientName;
            return this;
        }

        public Builder setPrivateKey(String privateKey) {
            this.privateKey = privateKey;
            return this;
        }

        public CruxClientInitConfig create() {
            return new CruxClientInitConfig(this);
        }

    }

    private Builder builder;
    private String privateKey;
    private String walletClientName;
    private String _encryptionKeyValue;


    protected CruxClientInitConfig(Builder builder) {
        this.builder = builder;
        this.privateKey = builder.privateKey;
        this.walletClientName = builder.walletClientName;
        this._encryptionKeyValue = builder._encryptionKeyValue;
    }

    public String getCruxClientInitConfigString() throws JSONException {
        JSONObject cruxClientInitConfigObject = new JSONObject();
        cruxClientInitConfigObject.put("walletClientName", this.walletClientName);
        if (this.privateKey != null) {
            cruxClientInitConfigObject.put("privateKey", this.privateKey);
        }
        return cruxClientInitConfigObject.toString();
    }

}

