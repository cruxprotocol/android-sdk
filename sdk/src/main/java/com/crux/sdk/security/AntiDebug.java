package com.crux.sdk.security;

import android.content.Context;
import android.os.Debug;
import android.content.pm.ApplicationInfo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class AntiDebug {

    private final Context mContext;

    public AntiDebug(Context context) {
        mContext = context;
    }

    public boolean isDebugging() {

        if (Debug.isDebuggerConnected()) {
            return true;
        }

        return (mContext.getApplicationContext().getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
    }

    //**************************************** TracerPid begin ************************************

    public static boolean isTracerPid() {

        if (isLocalPortUsing(23946)) {
            return true;
        }

        String tracerPid = getTracerPid();
        if (!"0".equals(tracerPid)) {
            return true;
        }

        return false;
    }

    /***
     *  true:already in using  false:not using
     * @param port
     */
    private static boolean isLocalPortUsing(int port) {
        boolean flag = true;
        try {
            flag = isPortUsing("127.0.0.1", port);
        } catch (Exception e) {
        }
        return flag;
    }

    /***
     *  true:already in using  false:not using
     * @param host
     * @param port
     * @throws UnknownHostException
     */
    private static boolean isPortUsing(String host, int port) throws UnknownHostException {
        boolean flag = false;
        InetAddress theAddress = InetAddress.getByName(host);
        try {
            Socket socket = new Socket(theAddress, port);
            flag = true;
        } catch (IOException e) {
        }
        return flag;
    }

    private static String getTracerPid() {
        BufferedReader bufferedReader = null;
        String readLine = "";
        try {
            bufferedReader = new BufferedReader(new FileReader("/proc/self/status"));
            do {
                readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }

            } while (!readLine.startsWith("TracerPid:"));
            readLine = readLine.substring("TracerPid:".length()).trim();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return readLine;
    }
    //**************************************** TracerPid end **************************************

    //**************************************** network test begin *********************************
    public static boolean checkGrabData() {
        try {
            String proxyHost = System.getProperty("http.proxyHost");
            String proxyPort = System.getProperty("http.proxyPort");
            return proxyHost != null || proxyPort != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    //**************************************** network test end ***********************************
}
