package com.crux.sdk;

import com.crux.sdk.bridge.CruxJSBridgeResponseHandler;
import com.crux.sdk.bridge.GenericUtils;
import com.crux.sdk.model.CruxClientError;
import com.crux.sdk.model.CruxClientResponseHandler;
import com.crux.sdk.model.CruxIDState;
import com.google.gson.Gson;

import org.liquidplayer.javascript.JSObject;
import org.liquidplayer.javascript.JSValue;
import org.json.JSONObject;

import java.lang.reflect.Type;


public class CruxJSBridgeResponseHandlerImpl implements CruxJSBridgeResponseHandler {

    private Type returnClass;
    private final Gson gson;
    private final CruxClientResponseHandler handler;

    public CruxJSBridgeResponseHandlerImpl (Type returnClass, final CruxClientResponseHandler handler) {
        this.returnClass = returnClass;
        this.gson = new Gson();
        this.handler = handler;
    }


    @Override
    public void onErrorResponse(JSObject failureResponse) {
        handler.onErrorResponse(new CruxClientError());
    }


    @Override
    public void onResponse(JSValue successResponse){
        Object successResponseJson = GenericUtils.toJavaObject(successResponse, JSONObject.class);
        String msg = successResponseJson.toString();
        CruxIDState responseObject = gson.fromJson(msg, this.returnClass);
        handler.onResponse(responseObject);
    }

}