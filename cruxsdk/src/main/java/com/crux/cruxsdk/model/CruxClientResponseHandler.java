package com.crux.cruxsdk.model;


public interface CruxClientResponseHandler<T> {
    void onResponse(T successResponse);
    void onErrorResponse(CruxClientError failureResponse);

}
