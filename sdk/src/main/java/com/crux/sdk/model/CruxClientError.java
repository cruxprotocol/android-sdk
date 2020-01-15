package com.crux.sdk.model;


public class CruxClientError extends Throwable {

    public Integer errorCode;
    public String errorMessage;

    public CruxClientError(Integer errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    private static String getErrorMessage(Integer errorCode) {
        return (String) AndroidCruxClientErrorString.errorCodeToErrorStringMap.get(errorCode);

    }

    public static CruxClientError getCruxClientError(Integer errorCode) {
        String message = CruxClientError.getErrorMessage(errorCode);
        return new CruxClientError(errorCode, message);
    }
}
