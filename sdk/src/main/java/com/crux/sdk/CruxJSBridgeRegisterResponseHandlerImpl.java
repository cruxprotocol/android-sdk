package com.crux.sdk;

import com.crux.sdk.model.CruxClientResponseHandler;

import org.liquidplayer.javascript.JSValue;

import java.lang.reflect.Type;

public class CruxJSBridgeRegisterResponseHandlerImpl extends CruxJSBridgeResponseHandlerImpl {


    public CruxJSBridgeRegisterResponseHandlerImpl(Type returnClass, CruxClientResponseHandler handler) {
        super(returnClass, handler);
    }

    @Override
    public void onResponse(JSValue successResponse){
        String successResponseString = successResponse.toString();
        String responseObject = "";
        if (successResponseString.equals("undefined")) {
            responseObject = null;
        }
        handler.onResponse(responseObject);

    }

}
