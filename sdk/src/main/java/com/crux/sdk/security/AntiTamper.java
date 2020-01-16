package com.crux.sdk.security;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.util.Arrays;
import java.util.List;

public class AntiTamper {

    private final Context mContext;

    public AntiTamper(Context context) {
        mContext = context;
    }

    //**************************************** hook detection begin ****************************

    /**
     * Detects if there is any suspicious installed application.
     *
     * @return <code>true</code> if some bad application is installed, <code>false</code> otherwise.
     */
    public boolean hookDetected() {
        PackageManager packageManager = mContext.getPackageManager();
        List<ApplicationInfo> applicationInfoList = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        String[] dangerousPackages = {"de.robv.android.xposed.installer", "com.saurik.substrate", "de.robv.android.xposed"};

        for (ApplicationInfo applicationInfo : applicationInfoList) {
            if (Arrays.asList(dangerousPackages).contains(applicationInfo.packageName)) {
                return true;
            }
        }

        return this.advancedHookDetection();
    }

    private boolean advancedHookDetection() {
        try {
            throw new Exception();
        } catch (Exception e) {
            int zygoteInitCallCount = 0;
            for (StackTraceElement stackTraceElement : e.getStackTrace()) {
                if (stackTraceElement.getClassName().equals("com.android.internal.os.ZygoteInit")) {
                    zygoteInitCallCount++;
                    if (zygoteInitCallCount == 2) {
                        return true;
                    }
                }

                if (stackTraceElement.getClassName().equals("com.saurik.substrate.MS$2") &&
                        stackTraceElement.getMethodName().equals("invoked")) {
                    return true;
                }

                if (stackTraceElement.getClassName().equals("de.robv.android.xposed.XposedBridge") &&
                        stackTraceElement.getMethodName().equals("main")) {
                    return true;
                }

                if (stackTraceElement.getClassName().equals("de.robv.android.xposed.XposedBridge") &&
                        stackTraceElement.getMethodName().equals("handleHookedMethod")) {
                    return true;
                }
            }
        }

        return !this.checkFrida();
    }

    private boolean checkFrida() {
        ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServices = activityManager.getRunningServices(300);

        if (runningServices != null) {
            for (int i = 0; i < runningServices.size(); ++i) {
                if (runningServices.get(i).process.contains("fridaserver")) {
                    return false;
                }
            }
        }

        return true;
    }

    //**************************************** hook detection ends ****************************

    //**************************************** signature testing begin ****************************
    public boolean isOwnApp() {
        String APP_SIGN = "test";

        if (mContext == null) {
            return false;
        }

        String signStr = this.getSignature();
        return APP_SIGN.equals(signStr);
    }

    private String getSignature() {
        return "test";
    }
    //**************************************** signature testing ends *****************************
}
