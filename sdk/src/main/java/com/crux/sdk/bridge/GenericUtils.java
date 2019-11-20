package com.crux.sdk.bridge;

import android.content.Context;
import android.content.res.AssetManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.liquidplayer.javascript.JSValue;


public class GenericUtils {
    public static String getFromFile(Context androidContextObject, String fileName) throws IOException {
        AssetManager assetManager = androidContextObject.getAssets();
        System.out.println(assetManager.list("/"));
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
    public static Object toJavaObject(JSValue val, Class clazz) {
        try {
            if (clazz == JSONObject.class && val.isObject())
                return new JSONObject(val.toJSON());
            else if (clazz == Boolean.class || clazz == boolean.class)
                return val.toBoolean();
//            else if (clazz == JSONArray.class && isArray())
//                return new JSONArray(toJSON());
//            else if (clazz == Map.class)
//                return new JSObjectPropertiesMap(toObject(), Object.class);
//            else if (clazz == List.class)
//                return toJSArray();
//            else if (clazz == String.class)
//                return toString();
//            else if (clazz == Double.class || clazz == double.class)
//                return toNumber();
//            else if (clazz == Float.class || clazz == float.class)
//                return toNumber().floatValue();
//            else if (clazz == Integer.class || clazz == int.class)
//                return toNumber().intValue();
//            else if (clazz == Long.class || clazz == long.class)
//                return toNumber().longValue();
//            else if (clazz == Byte.class || clazz == byte.class)
//                return toNumber().byteValue();
//            else if (clazz == Short.class || clazz == short.class)
//                return toNumber().shortValue();
//            else if (clazz.isArray())
//                return toJSArray().toArray(clazz.getComponentType());
//            else if (JSArray.class.isAssignableFrom(clazz))
//                return clazz.cast(toJSArray());
//            else if (JSObject.class.isAssignableFrom(clazz))
//                return clazz.cast(val.toObject());
//            else if (JSValue.class.isAssignableFrom(clazz))
//                return clazz.cast(this);
//            else if (clazz == Object.class)
//                return "this";

        } catch (JSONException e) {
            /* Fall through */
        }
        return null;
    }

}

