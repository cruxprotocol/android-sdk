package com.crux.sdk.security;

import android.os.Debug;

public class AntiDebug {

    public static boolean isDebugging() {
        return Debug.isDebuggerConnected();
    }
}
