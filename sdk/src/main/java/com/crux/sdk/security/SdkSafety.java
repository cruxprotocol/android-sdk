package com.crux.sdk.security;

import android.content.Context;
import android.widget.Toast;
import com.crux.sdk.BuildConfig;

import java.util.Random;


public class SdkSafety {

    private final Context mContext;

    public SdkSafety(Context context) {
        mContext = context;
    }

    public boolean checkSafety() {

        boolean isUnsafe = _checkSafety();

        if (isUnsafe) {
            Toast.makeText(mContext, "sdk informed unsafe env", Toast.LENGTH_LONG).show();
        }
        if (isReleaseVersion()) {
            return isUnsafe;
            // System.exit(0);
        }
        return false;
    }

    private boolean isReleaseVersion() {
         return false;
//         return BuildConfig.BUILD_TYPE.contentEquals("release");
    }

    private boolean _checkSafety() {

        AntiTamper at = new AntiTamper(mContext);
        AntiDebug ad = new AntiDebug(mContext);
        AntiRoot ar = new AntiRoot(mContext);

        Random rand = new Random();
        int randomizeCheck = rand.nextInt(1000)/7;
        switch (randomizeCheck) {
            case 1:
                if (AntiEmulator.isEmulator()) {
                    return true;
                }
                break;

            case 2:
                boolean ownApp = at.isOwnApp();
                if (!ownApp) {
                    return true;
                }
                break;

            case 3:
                if (ad.isDebugging()) {
                    return true;
                }
                break;

            case 4:
                if (AntiDebug.isTracerPid()) {
                    return true;
                }
                break;

            case 5:
                if (at.hookDetected()) {
                    return true;
                }

            case 6:
                if (ar.isRooted()) {
                    return true;
                }

            case 0:
                if (AntiDebug.checkGrabData()) {
                    return true;
                }

            default:
                return AntiEmulator.isEmulator() || AntiDebug.isTracerPid() || at.hookDetected()
                        || ar.isRooted() || AntiDebug.checkGrabData() || ad.isDebugging();
        }

        return AntiEmulator.isEmulator() || AntiDebug.isTracerPid() || at.hookDetected()
                || ar.isRooted() || AntiDebug.checkGrabData() || ad.isDebugging();

    }

}
