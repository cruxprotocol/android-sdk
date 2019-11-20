package com.crux.sdk.bridge;


import com.crux.sdk.model.CruxParams;

public class CruxJSBridgeAsyncRequest {
    public final String method;
    public final CruxParams params;
    public final CruxJSBridgeResponseHandler handler;

    public CruxJSBridgeAsyncRequest(String method, CruxParams params, CruxJSBridgeResponseHandler handler) {
        this.method = method;
        this.params = params;
        this.handler = handler;
    }
}