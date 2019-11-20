package com.crux.sdk.model;

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

    public String getCruxClientInitConfigString() {

        // { walletClientName: 'cruxdev', privateKey: 'KxRwDkwabEq5uT9vyPFeT2GQyNzZC5B8HjYpRYXxwcSmZJxKmVH7', storage: inmemStorage, getEncryptionKey: }

        JSONObject cruxClientInitConfigObject = new JSONObject();
        try {
            cruxClientInitConfigObject.put("walletClientName", this.walletClientName);
            // cruxClientInitConfigObject.put("storage", "inmemStorage");
            // cruxClientInitConfigObject.put("getEncryptionKey", "function(){return '"+ this._encryptionKeyValue +"'}");
            if (this.privateKey != null) {
                cruxClientInitConfigObject.put("privateKey", this.privateKey);
            }
        } catch (Exception e) {
            System.out.println("s");
        }
        return cruxClientInitConfigObject.toString();

    }

}

