package com.crux.sdk;

import com.crux.sdk.bridge.GenericUtils;
import com.crux.sdk.model.CruxClientResponseHandler;

import org.json.JSONObject;
import org.liquidplayer.javascript.JSValue;

import java.lang.reflect.Type;

public class CruxJSBridgeGetAddressMapResponseHandlerImpl extends CruxJSBridgeResponseHandlerImpl {

    public CruxJSBridgeGetAddressMapResponseHandlerImpl(Type returnClass, CruxClientResponseHandler handler) {
        super(returnClass, handler);
    }

    public String convertToString(JSValue jsValue) {
        Object successResponseJson = GenericUtils.toJavaObject(jsValue, JSONObject.class);
        JSONObject cruxAddressMapping = new JSONObject();
        try {
            cruxAddressMapping.put("currency", successResponseJson);
        } catch (Exception e) {
            System.out.println("s");
        }
        String msg = cruxAddressMapping.toString();
        return msg;
    }

}
