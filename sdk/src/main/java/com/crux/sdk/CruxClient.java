package com.crux.sdk;

import android.content.Context;

import com.crux.sdk.bridge.CruxJSBridge;
import com.crux.sdk.bridge.CruxJSBridgeAsyncRequest;
import com.crux.sdk.model.CruxAddress;
import com.crux.sdk.model.CruxAddressMapping;
import com.crux.sdk.model.CruxClientResponseHandler;
import com.crux.sdk.model.CruxIDState;
import com.crux.sdk.model.CruxParams;
import com.crux.sdk.model.CruxPutAddressMapSuccess;
import com.google.gson.Gson;


import java.io.IOException;


public class CruxClient {
    private final CruxJSBridge jsBridge;

    public CruxClient(String walletName, Context androidContextObject) throws IOException {
        this.jsBridge = new CruxJSBridge(walletName, androidContextObject);
    }


    public void getCruxIDState(final CruxClientResponseHandler<CruxIDState> handler) {
        CruxJSBridgeAsyncRequest bridgeRequest = new CruxJSBridgeAsyncRequest("getCruxIDState", new CruxParams(), new CruxJSBridgeResponseHandlerImpl(CruxIDState.class, handler));
        jsBridge.executeAsync(bridgeRequest);
    }

    public void registerCruxID(String cruxIDSubdomain, final CruxClientResponseHandler<Void> handler) {
        CruxJSBridgeAsyncRequest bridgeRequest = new CruxJSBridgeAsyncRequest("registerCruxID", new CruxParams(cruxIDSubdomain), new CruxJSBridgeRegisterResponseHandlerImpl(null, handler));
        jsBridge.executeAsync(bridgeRequest);
    }

    public void getAddressMap(final CruxClientResponseHandler<CruxAddressMapping> handler) {
        CruxJSBridgeAsyncRequest bridgeRequest = new CruxJSBridgeAsyncRequest("getAddressMap", new CruxParams(), new CruxJSBridgeGetAddressMapResponseHandlerImpl(CruxAddressMapping.class, handler));
        jsBridge.executeAsync(bridgeRequest);
    }

    public void putAddressMap(CruxAddressMapping newAddressMap, final CruxClientResponseHandler<CruxPutAddressMapSuccess> handler){
        Gson gson = new Gson();
        CruxParams params = new CruxParams(jsBridge.JSONtoObject(gson.toJson(newAddressMap.currency)));
        CruxJSBridgeAsyncRequest bridgeRequest = new CruxJSBridgeAsyncRequest("putAddressMap", params, new CruxJSBridgeResponseHandlerImpl(CruxPutAddressMapSuccess.class, handler));
        jsBridge.executeAsync(bridgeRequest);
    }

    public void resolveCurrencyAddressForCruxID(String fullCruxID, String walletCurrencySymbol, final CruxClientResponseHandler<CruxAddress> handler){
        CruxJSBridgeAsyncRequest bridgeRequest = new CruxJSBridgeAsyncRequest("resolveCurrencyAddressForCruxID", new CruxParams(fullCruxID, walletCurrencySymbol), new CruxJSBridgeResponseHandlerImpl(CruxAddress.class, handler));
        jsBridge.executeAsync(bridgeRequest);
    }

    public void isCruxIDAvailable(String cruxIDSubdomain, final CruxClientResponseHandler<Boolean> handler) {
        CruxJSBridgeAsyncRequest bridgeRequest = new CruxJSBridgeAsyncRequest("isCruxIDAvailable", new CruxParams(cruxIDSubdomain), new CruxJSBridgeCruxIDAvailablityResponseHandlerImpl(Boolean.class, handler));
        jsBridge.executeAsync(bridgeRequest);
    }

}
