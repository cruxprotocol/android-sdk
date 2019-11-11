package com.crux.sdk.bridge;

import org.liquidplayer.javascript.JSObject;

public interface CruxJSBridgeResponseHandler {
    void onResponse(JSObject successResponse);
    void onErrorResponse(JSObject failureResponse);
}
