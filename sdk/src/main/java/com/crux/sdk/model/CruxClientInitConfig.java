package com.crux.sdk.model;

import java.util.Map;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

public class CruxClientInitConfig {

    public static class Builder {

        //        private String storage;
        //        private String encryption;
        //        private String nameService;
        private String walletClientName;
        private String privateKey;

        public Builder() {
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
    private char[] privateKey;
    private String walletClientName;


    protected CruxClientInitConfig(Builder builder) {
        this.builder = builder;
//        this.privateKey = builder.privateKey;
        this.privateKey = new char[]{'c', 'd', 'f', '2', 'd', '2', '7', '6', 'c', 'a', 'f', '0', 'c', '9', 'c', '3', '4', '2', '5', '8', 'e', 'd', '6', 'e', 'b', 'd', '0', 'e', '6', '0', 'e', '0', 'e', '8', 'b', '3', 'd', '9', 'a', '7', 'b', '8', 'a', '9', 'a', '7', '1', '7', 'f', '2', 'e', '1', '9', 'e', 'd', '9', 'b', '3', '7', 'f', '7', 'c', '6', 'f'};
        this.walletClientName = builder.walletClientName;
        builder.privateKey = null;
        builder.walletClientName = null;
        builder = null;
    }

    public String getCruxClientInitConfigString() throws JSONException {
        JSONObject cruxClientInitConfigObject = new JSONObject();
        cruxClientInitConfigObject.put("walletClientName", this.walletClientName);
        if (this.privateKey != null) {
            cruxClientInitConfigObject.put("privateKey", this.privateKey);
        }
        return cruxClientInitConfigObject.toString();
    }

    public Map<String, char[]> getCruxClientInitConfigMap() throws JSONException {
//        JSONObject cruxClientInitConfigObject = new JSONObject();
        Map<String, char[]> cruxClientInitConfigObject = new HashMap<String, char[]>();
        cruxClientInitConfigObject.put("walletClientName", this.walletClientName.toCharArray());
        if (this.privateKey != null) {
            cruxClientInitConfigObject.put("privateKey", this.privateKey);
        }
        return cruxClientInitConfigObject;
    }

}

