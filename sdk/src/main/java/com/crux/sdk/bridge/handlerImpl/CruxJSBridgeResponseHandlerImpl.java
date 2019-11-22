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

    public CruxJSBridgeResponseHandlerImpl (Type returnClass, final CruxClientResponseHandler handler) {
        this.returnClass = returnClass;
        this.gson = new Gson();
        this.handler = handler;
    }


    @Override
    public void onErrorResponse(JSValue failureResponse) {
        Object successResponseJson = GenericUtils.toJavaObject(failureResponse, JSONObject.class);
        String msg = successResponseJson.toString();
        CruxClientError errorObject = gson.fromJson(msg, CruxClientError.class);
        errorObject.errorMessage = failureResponse.toString();
        handler.onErrorResponse(errorObject);
    }

    public String convertToString(JSValue jsValue) {
        Object successResponseJson = GenericUtils.toJavaObject(jsValue, JSONObject.class);
        String msg = successResponseJson.toString();
        return msg;
    }


    @Override
    public void onResponse(JSValue successResponse){
        String msg = this.convertToString(successResponse);
        Object responseObject = gson.fromJson(msg, this.returnClass);
        handler.onResponse(responseObject);
    }

}


