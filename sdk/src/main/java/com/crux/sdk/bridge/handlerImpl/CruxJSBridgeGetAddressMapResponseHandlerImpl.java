package com.crux.sdk.bridge.handlerImpl;

import com.crux.sdk.bridge.GenericUtils;
import com.crux.sdk.model.AndroidCruxClientErrorCode;
import com.crux.sdk.model.CruxClientError;
import com.crux.sdk.model.CruxClientResponseHandler;

import org.json.JSONException;
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
        } catch (JSONException e) {
            e.printStackTrace();
            CruxClientError errorObject = CruxClientError.getCruxClientError(AndroidCruxClientErrorCode.cruxAddressMappingConversionFailed);
            handler.onErrorResponse(errorObject);
        }
        return cruxAddressMapping.toString();
    }

}
