package com.crux.sdk;

import android.content.Context;
import android.os.Debug;

import com.crux.sdk.bridge.CruxJSBridge;
import com.crux.sdk.bridge.CruxJSBridgeAsyncRequest;
import com.crux.sdk.bridge.handlerImpl.CruxJSBridgeResponseHandlerImpl;
import com.crux.sdk.model.AndroidCruxClientErrorCode;
import com.crux.sdk.model.CruxAddress;
import com.crux.sdk.model.CruxClientError;
import com.crux.sdk.model.CruxClientInitConfig;
import com.crux.sdk.model.CruxClientResponseHandler;
import com.crux.sdk.model.CruxIDState;
import com.crux.sdk.model.CruxParams;
import com.crux.sdk.model.CruxPutAddressMapSuccess;
import com.crux.sdk.security.SdkSafety;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;


public class CruxClient {
    private final CruxJSBridge jsBridge;
    private final Gson gson = new Gson();

    public CruxClient(CruxClientInitConfig.Builder configBuilder, Context androidContextObject) throws IOException, CruxClientError {

        SdkSafety sf = new SdkSafety(androidContextObject);
        if (sf.checkSafety()) {
            throw CruxClientError.getCruxClientError(AndroidCruxClientErrorCode.runningInUnsafeEnvironment);
        }
        this.jsBridge = new CruxJSBridge(configBuilder, androidContextObject);
    }


    public void getCruxIDState(final CruxClientResponseHandler<CruxIDState> handler) {
        CruxJSBridgeAsyncRequest bridgeRequest = new CruxJSBridgeAsyncRequest("getCruxIDState", new CruxParams(), new CruxJSBridgeResponseHandlerImpl(CruxIDState.class, handler, gson));
        jsBridge.executeAsync(bridgeRequest);
    }

    public void registerCruxID(String cruxIDSubdomain, final CruxClientResponseHandler<Void> handler) {
        CruxJSBridgeAsyncRequest bridgeRequest = new CruxJSBridgeAsyncRequest("registerCruxID", new CruxParams(cruxIDSubdomain), new CruxJSBridgeResponseHandlerImpl(null, handler, gson));
        jsBridge.executeAsync(bridgeRequest);
    }

    public void getAddressMap(final CruxClientResponseHandler<HashMap<String, CruxAddress>> handler) {
        Type type = new TypeToken<HashMap<String, CruxAddress>>(){}.getType();
        CruxJSBridgeAsyncRequest bridgeRequest = new CruxJSBridgeAsyncRequest("getAddressMap", new CruxParams(), new CruxJSBridgeResponseHandlerImpl(type, handler, gson));
        jsBridge.executeAsync(bridgeRequest);
    }

    public void putAddressMap(HashMap<String, CruxAddress> newAddressMap, final CruxClientResponseHandler<CruxPutAddressMapSuccess> handler){
        CruxParams params = new CruxParams(jsBridge.JSONtoObject(gson.toJson(newAddressMap)));
        CruxJSBridgeAsyncRequest bridgeRequest = new CruxJSBridgeAsyncRequest("putAddressMap", params, new CruxJSBridgeResponseHandlerImpl(CruxPutAddressMapSuccess.class, handler, gson));
        jsBridge.executeAsync(bridgeRequest);
    }

    public void resolveCurrencyAddressForCruxID(String fullCruxID, String walletCurrencySymbol, final CruxClientResponseHandler<CruxAddress> handler){
        CruxJSBridgeAsyncRequest bridgeRequest = new CruxJSBridgeAsyncRequest("resolveCurrencyAddressForCruxID", new CruxParams(fullCruxID, walletCurrencySymbol), new CruxJSBridgeResponseHandlerImpl(CruxAddress.class, handler, gson));
        jsBridge.executeAsync(bridgeRequest);
    }

    public void isCruxIDAvailable(String cruxIDSubdomain, final CruxClientResponseHandler<Boolean> handler) {
        CruxJSBridgeAsyncRequest bridgeRequest = new CruxJSBridgeAsyncRequest("isCruxIDAvailable", new CruxParams(cruxIDSubdomain), new CruxJSBridgeResponseHandlerImpl(Boolean.class, handler, gson));
        jsBridge.executeAsync(bridgeRequest);
    }

    public void resolveAssetAddressForCruxID(String fullCruxID, HashMap<String, String> assetMatcher, final CruxClientResponseHandler<CruxAddress> handler){
        Object assetMatcherJsObj = jsBridge.JSONtoObject(gson.toJson(assetMatcher));
        CruxJSBridgeAsyncRequest bridgeRequest = new CruxJSBridgeAsyncRequest("resolveAssetAddressForCruxID", new CruxParams(fullCruxID, assetMatcherJsObj), new CruxJSBridgeResponseHandlerImpl(CruxAddress.class, handler, gson));
        jsBridge.executeAsync(bridgeRequest);
    }

    public void getEnabledAssetGroups(final CruxClientResponseHandler<String[]> handler) {
        Type type = new TypeToken<String[]>(){}.getType();
        CruxJSBridgeAsyncRequest bridgeRequest = new CruxJSBridgeAsyncRequest("getEnabledAssetGroups", new CruxParams(), new CruxJSBridgeResponseHandlerImpl(type, handler, gson));
        jsBridge.executeAsync(bridgeRequest);
    }

    public void putEnabledAssetGroups(String[] symbolAssetGroups, final CruxClientResponseHandler<String[]> handler) {
        Object symbolAssetGroupsJsObj = jsBridge.JSONtoObject(gson.toJson(symbolAssetGroups));
        Type type = new TypeToken<String[]>(){}.getType();
        CruxJSBridgeAsyncRequest bridgeRequest = new CruxJSBridgeAsyncRequest("putEnabledAssetGroups", new CruxParams(symbolAssetGroupsJsObj), new CruxJSBridgeResponseHandlerImpl(type, handler, gson));
        jsBridge.executeAsync(bridgeRequest);
    }

}
