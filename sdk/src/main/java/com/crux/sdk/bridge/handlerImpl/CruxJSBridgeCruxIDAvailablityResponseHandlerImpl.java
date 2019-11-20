package com.crux.sdk.bridge.handlerImpl;

import com.crux.sdk.bridge.GenericUtils;
import com.crux.sdk.model.CruxClientResponseHandler;

import org.liquidplayer.javascript.JSValue;

import java.lang.reflect.Type;

public class CruxJSBridgeCruxIDAvailablityResponseHandlerImpl extends CruxJSBridgeResponseHandlerImpl {

    public CruxJSBridgeCruxIDAvailablityResponseHandlerImpl(Type returnClass, CruxClientResponseHandler handler) {
        super(returnClass, handler);
    }

    @Override
    public void onResponse(JSValue successResponse){
        Object availability = GenericUtils.toJavaObject(successResponse, Boolean.class);
        handler.onResponse((Boolean) availability);
    }

}
