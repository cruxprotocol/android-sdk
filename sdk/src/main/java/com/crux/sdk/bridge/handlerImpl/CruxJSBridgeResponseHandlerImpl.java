package com.crux.sdk.bridge.handlerImpl;

import com.crux.sdk.bridge.CruxJSBridgeResponseHandler;
import com.crux.sdk.bridge.GenericUtils;
import com.crux.sdk.model.CruxClientError;
import com.crux.sdk.model.CruxClientResponseHandler;
import com.google.gson.Gson;

import org.json.JSONObject;
import org.liquidplayer.javascript.JSValue;

import java.lang.reflect.Type;


public class CruxJSBridgeResponseHandlerImpl implements CruxJSBridgeResponseHandler {

    private Type returnClass;
    private final Gson gson;
    protected final CruxClientResponseHandler handler;

    public CruxJSBridgeResponseHandlerImpl (Type returnClass, final CruxClientResponseHandler handler, final Gson gson) {
        this.returnClass = returnClass;
        this.gson = gson;
        this.handler = handler;
    }


    @Override
    public void onErrorResponse(JSValue failureResponse) {
        String msg = GenericUtils.toJavaString(failureResponse);
        CruxClientError errorObject = gson.fromJson(msg, CruxClientError.class);
        errorObject.errorMessage = failureResponse.toString();
        handler.onErrorResponse(errorObject);
    }

    @Override
    public void onResponse(JSValue successResponse) {
        String msg = GenericUtils.toJavaString(successResponse);
        Object responseObject = gson.fromJson(msg, this.returnClass);
        handler.onResponse(responseObject);
    }

}


