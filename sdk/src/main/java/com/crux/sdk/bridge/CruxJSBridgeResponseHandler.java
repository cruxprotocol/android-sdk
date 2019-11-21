package com.crux.sdk.bridge;

import org.liquidplayer.javascript.JSValue;

public interface CruxJSBridgeResponseHandler {
    void onResponse(JSValue successResponse);
    void onErrorResponse(JSValue failureResponse);
}
