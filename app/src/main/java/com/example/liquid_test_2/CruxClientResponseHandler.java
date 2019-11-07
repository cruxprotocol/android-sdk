package com.example.liquid_test_2;

import org.liquidplayer.javascript.JSObject;


interface CruxClientResponseHandler<T> {
    void onResponse(T successResponse);
    void onErrorResponse(CruxClientError failureResponse);

}
