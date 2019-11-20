package com.crux.sdk.bridge;

import org.liquidplayer.javascript.JSValue;

public interface CruxJSBridgeResponseHandler {
    void onResponse(JSValue successResponse) throws ClassNotFoundException, InstantiationException, IllegalAccessException;
    void onErrorResponse(JSValue failureResponse);
}
