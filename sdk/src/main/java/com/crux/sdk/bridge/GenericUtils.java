package com.crux.sdk.bridge;

import android.content.Context;
import android.content.res.AssetManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import org.liquidplayer.javascript.JSValue;


public class GenericUtils {
    public static String getFromFile(Context androidContextObject, String fileName) throws IOException {
        AssetManager assetManager = androidContextObject.getAssets();
        System.out.println(Arrays.toString(assetManager.list("/")));
        InputStream is = assetManager.open(fileName);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader input = new BufferedReader(isr);
        String line;
        StringBuilder returnString = new StringBuilder();
        while ((line = input.readLine()) != null) {
            returnString.append(line);
            returnString.append("\n");
        }
        return new String(returnString);
    }

    // Minimal implementation form https://github.com/LiquidPlayer/LiquidCore/blob/master/LiquidV8/src/main/java/org/liquidplayer/javascript/JSValue.java#L411
    public static String toJavaString(JSValue val) {
        if (val.isBoolean())
            return val.toBoolean().toString();
        else if (val.isUndefined())
            return null;
//        else if (val.isArray())
//            return val.toJSON();
//        else if (val.isObject())
//            return new JSONObject(val.toJSON()).toString();
        else
            return val.toJSON();
    }

    public static long crc32(String input) {
        byte[] bytes = input.getBytes();
        Checksum checksum = new CRC32(); // java.util.zip.CRC32
        checksum.update(bytes, 0, bytes.length);

        return checksum.getValue();
    }

}

