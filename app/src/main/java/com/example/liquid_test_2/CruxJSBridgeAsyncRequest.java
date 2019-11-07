package com.example.liquid_test_2;

class CruxJSBridgeAsyncRequest {
    public final String method;
    public final CruxParams params;
    public final CruxJSBridgeResponseHandler handler;

    CruxJSBridgeAsyncRequest(String method, CruxParams params, CruxJSBridgeResponseHandler handler) {
        this.method = method;
        this.params = params;
        this.handler = handler;
    }
}