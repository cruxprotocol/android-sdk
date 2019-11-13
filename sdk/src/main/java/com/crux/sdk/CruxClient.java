package com.crux.sdk;

import android.content.Context;

import com.crux.sdk.bridge.CruxJSBridge;
import com.crux.sdk.bridge.CruxJSBridgeAsyncRequest;
import com.crux.sdk.bridge.CruxJSBridgeResponseHandler;
import com.crux.sdk.model.CruxAddressMapping;
import com.crux.sdk.model.CruxClientError;
import com.crux.sdk.model.CruxClientResponseHandler;
import com.crux.sdk.model.CruxIDState;
import com.crux.sdk.model.CruxParams;
import com.google.gson.Gson;

import org.liquidplayer.javascript.JSObject;

import java.io.IOException;

public class CruxClient {
    private final CruxJSBridge jsBridge;
    private final Gson gson;

    public CruxClient(String walletName, Context androidContextObject) throws IOException {
        this.jsBridge = new CruxJSBridge(walletName, androidContextObject);
        this.gson = new Gson();
    }


    public void getCruxIDState(final CruxClientResponseHandler<CruxIDState> handler) {
        CruxJSBridgeAsyncRequest bridgeRequest = new CruxJSBridgeAsyncRequest("getCruxIDState", new CruxParams(), new CruxJSBridgeResponseHandler() {
            @Override
            public void onResponse(JSObject successResponse) {
                String successResponseJson = jsBridge.objectToJSON(successResponse);
                CruxIDState responseObject = gson.fromJson(successResponseJson, CruxIDState.class);
                handler.onResponse(responseObject);
            }

            @Override
            public void onErrorResponse(JSObject failureResponse) {
                handler.onErrorResponse(new CruxClientError());
            }
        });

        jsBridge.executeAsync(bridgeRequest);
    }

    public void registerCruxID(String cruxIDSubdomain,final CruxClientResponseHandler<String> handler) {
        CruxJSBridgeAsyncRequest bridgeRequest = new CruxJSBridgeAsyncRequest("registerCruxID", new CruxParams(cruxIDSubdomain), new CruxJSBridgeResponseHandler(){
            @Override
            public void onResponse(JSObject successResponse) {
                System.out.println("--------RegisterID-------");
                String successResponseJson = jsBridge.objectToJSON(successResponse);
                System.out.println(successResponseJson);
//                CruxIDState responseObject = gson.fromJson(successResponseJson, CruxIDState.class);
                handler.onResponse(successResponseJson);
            }

            @Override
            public void onErrorResponse(JSObject failureResponse) {
                handler.onErrorResponse(new CruxClientError());
            }

        });

        jsBridge.executeAsync(bridgeRequest);
    }

    public void getAddressMap(final CruxClientResponseHandler<CruxAddressMapping> handler) {
        CruxJSBridgeAsyncRequest bridgeRequest = new CruxJSBridgeAsyncRequest("getAddressMap", new CruxParams(), new CruxJSBridgeResponseHandler(){
            @Override
            public void onResponse(JSObject successResponse) {
                System.out.println("--------getAddressMap-------");
                String successResponseJson = jsBridge.objectToJSON(successResponse);
                System.out.println(successResponseJson);
                CruxAddressMapping responseObject = gson.fromJson(successResponseJson, CruxAddressMapping.class);
                handler.onResponse(responseObject);
            }

            @Override
            public void onErrorResponse(JSObject failureResponse) {
                handler.onErrorResponse(new CruxClientError());
            }

        });

        jsBridge.executeAsync(bridgeRequest);
    }
}
