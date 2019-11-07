package com.example.liquid_test_2;

import org.liquidplayer.javascript.JSObject;

interface CruxJSBridgeResponseHandler {
    void onResponse(JSObject successResponse);
    void onErrorResponse(JSObject failureResponse);
}
