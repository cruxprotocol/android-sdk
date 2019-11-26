package com.crux.sdk;

import android.content.Context;

import com.crux.sdk.bridge.CruxJSBridge;
import com.crux.sdk.bridge.CruxJSBridgeAsyncRequest;
//import com.crux.sdk.bridge.handlerImpl.CruxJSBridgeCruxIDAvailablityResponseHandlerImpl;
//import com.crux.sdk.bridge.handlerImpl.CruxJSBridgeVoidResponseHandlerImpl;
import com.crux.sdk.bridge.handlerImpl.CruxJSBridgeResponseHandlerImpl;
import com.crux.sdk.model.CruxAddress;
import com.crux.sdk.model.CruxClientError;
import com.crux.sdk.model.CruxClientInitConfig;
import com.crux.sdk.model.CruxClientResponseHandler;
import com.crux.sdk.model.CruxIDState;
import com.crux.sdk.model.CruxParams;
import com.crux.sdk.model.CruxPutAddressMapSuccess;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;


public class CruxClient {
    private final CruxJSBridge jsBridge;

    public CruxClient(CruxClientInitConfig.Builder configBuilder, Context androidContextObject) throws IOException, CruxClientError {
        this.jsBridge = new CruxJSBridge(configBuilder, androidContextObject);
    }

    public void init(final CruxClientResponseHandler<Void> handler) {
        CruxJSBridgeAsyncRequest bridgeRequest = new CruxJSBridgeAsyncRequest("init", new CruxParams(), new CruxJSBridgeResponseHandlerImpl(null, handler));
        jsBridge.executeAsync(bridgeRequest);
    }

    public void getCruxIDState(final CruxClientResponseHandler<CruxIDState> handler) {
        CruxJSBridgeAsyncRequest bridgeRequest = new CruxJSBridgeAsyncRequest("getCruxIDState", new CruxParams(), new CruxJSBridgeResponseHandlerImpl(CruxIDState.class, handler));
        jsBridge.executeAsync(bridgeRequest);
    }

    public void registerCruxID(String cruxIDSubdomain, final CruxClientResponseHandler<Void> handler) {
        CruxJSBridgeAsyncRequest bridgeRequest = new CruxJSBridgeAsyncRequest("registerCruxID", new CruxParams(cruxIDSubdomain), new CruxJSBridgeResponseHandlerImpl(null, handler));
        jsBridge.executeAsync(bridgeRequest);
    }

    public void getAddressMap(final CruxClientResponseHandler<HashMap> handler) {
        Type type = new TypeToken<HashMap<String, CruxAddress>>(){}.getType();
        CruxJSBridgeAsyncRequest bridgeRequest = new CruxJSBridgeAsyncRequest("getAddressMap", new CruxParams(), new CruxJSBridgeResponseHandlerImpl(type, handler));
        jsBridge.executeAsync(bridgeRequest);
    }

    public void putAddressMap(HashMap newAddressMap, final CruxClientResponseHandler<CruxPutAddressMapSuccess> handler){
        Gson gson = new Gson();
        CruxParams params = new CruxParams(jsBridge.JSONtoObject(gson.toJson(newAddressMap)));
        CruxJSBridgeAsyncRequest bridgeRequest = new CruxJSBridgeAsyncRequest("putAddressMap", params, new CruxJSBridgeResponseHandlerImpl(CruxPutAddressMapSuccess.class, handler));
        jsBridge.executeAsync(bridgeRequest);
    }

    public void resolveCurrencyAddressForCruxID(String fullCruxID, String walletCurrencySymbol, final CruxClientResponseHandler<CruxAddress> handler){
        CruxJSBridgeAsyncRequest bridgeRequest = new CruxJSBridgeAsyncRequest("resolveCurrencyAddressForCruxID", new CruxParams(fullCruxID, walletCurrencySymbol), new CruxJSBridgeResponseHandlerImpl(CruxAddress.class, handler));
        jsBridge.executeAsync(bridgeRequest);
    }

    public void isCruxIDAvailable(String cruxIDSubdomain, final CruxClientResponseHandler<Boolean> handler) {
        CruxJSBridgeAsyncRequest bridgeRequest = new CruxJSBridgeAsyncRequest("isCruxIDAvailable", new CruxParams(cruxIDSubdomain), new CruxJSBridgeResponseHandlerImpl(Boolean.class, handler));
        jsBridge.executeAsync(bridgeRequest);
    }

}
