package com.crux.sdk.bridge;

import java.util.Map;
import org.liquidplayer.javascript.JSObject;
import org.liquidplayer.javascript.JSValue;

public interface CruxJSBridgeResponseHandler {
    public Class expectedResponseClass = Map.class;
//    void onResponse(JSObject successResponse);
    void onResponse(JSValue successResponse);
    void onErrorResponse(JSObject failureResponse);
}
