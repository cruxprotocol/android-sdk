package com.example.liquid_test_2;

import android.content.Context;

import org.liquidplayer.javascript.JSObject;

import java.io.IOException;
import java.lang.reflect.Array;

public class CruxClient {
    private final CruxJSBridge jsBridge;

    CruxClient(String walletName, Context androidContextObject) throws IOException {
        this.jsBridge = new CruxJSBridge(walletName, androidContextObject);
    }


    public void getCruxIDState(final CruxClientResponseHandler<CruxIDState> handler) {
        CruxJSBridgeAsyncRequest bridgeRequest = new CruxJSBridgeAsyncRequest("getCruxIDState", new CruxParams(), new CruxJSBridgeResponseHandler() {
            @Override
            public void onResponse(JSObject successResponse) {
                handler.onResponse(new CruxIDState("asd", new CruxIDRegistrationStatus("asdd","ggs")));
            }

            @Override
            public void onErrorResponse(JSObject failureResponse) {
                handler.onErrorResponse(new CruxClientError());
            }
        });

        jsBridge.executeAsync(bridgeRequest);
    }
}
