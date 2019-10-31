package com.example.liquid_test_2;


import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;

import org.liquidplayer.javascript.JSContext;
import org.liquidplayer.javascript.JSFunction;
import org.liquidplayer.javascript.JSObject;
import org.liquidplayer.javascript.JSValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


class FetchTaskParams {
    public String url;
    public FetchTaskSettings settings;

    FetchTaskParams(String url, FetchTaskSettings settings) {
        this.url = url;
        this.settings = settings;
    }
}

class FetchTaskSettings {
    public String method;
    public String credentials;
    public String body;
    public HashMap<String, String> headers;

    FetchTaskSettings(String method, String credentials, String body, HashMap<String, String> headers) {
        this.method = method;
        this.credentials = credentials;
        this.body = body;
        this.headers = headers;
    }
}


class FetchResponse {
    public String urlString;
    public Integer code;
    public String body;
    public Map<String, List<String>> headers;

    FetchResponse(String urlString, Integer code, String body, Map<String, List<String>> headers) {
        this.urlString = urlString;
        this.code = code;
        this.body = body;
        this.headers = headers;
    }
}

class FetchTask extends AsyncTask<FetchTaskParams, Void, FetchResponse> {

    private void addHeadersToConnection(HttpURLConnection conn, HashMap<String, String> headers) {
        if (headers != null) {
            for (String name : headers.keySet()) {
                conn.setRequestProperty(name, headers.get(name));
            }
        }
    }

    private FetchResponse downloadContent(String myurl, FetchTaskSettings settings) throws IOException {
        System.out.println(">>>" + myurl + " " + settings.method);
        InputStream is = null;

        if (settings.method == null) {
            settings.method = "GET";
        }

        try {

            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            addHeadersToConnection(conn, settings.headers);
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod(settings.method);
            conn.setDoInput(true);
            conn.connect();
            int responseCode = conn.getResponseCode();
            System.out.println("The response is: " + responseCode);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = convert(is);
            Map<String, List<String>> headerFields = conn.getHeaderFields();

            FetchResponse response = new FetchResponse(url.toString(), responseCode, contentAsString, headerFields);
            return response;
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    public String convert(InputStream inputStream) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF8"));
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        return stringBuilder.toString();
    }


    protected FetchResponse doInBackground(FetchTaskParams... params) {

        FetchResponse response = null;
        System.out.println("FetchTask doInBackground start");
        try {
            System.out.println("=DownloadContent starting");
            response = downloadContent(params[0].url, params[0].settings);
//            content = "Asd";
//            content = testfunc("https://googlelolol", params[0].settings);
            System.out.println("DownloadContent ended");
            System.out.println("FetchTask Printing Response");
            System.out.println(response.body);
            System.out.println(response.code);
            System.out.println(response.headers);
            System.out.println(response.urlString);
            System.out.println("FetchTask doInBackground stop");

        } catch (Exception e) {
            System.out.println("IPExc");
            e.printStackTrace();
        }
        return response;
    }
}


class GenericUtils {
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

}


public class JSPolyFill {

    private static void makeFunctionAsyncOnGlobalScope(JSContext context, JSFunction function, String asyncNameOfFunction) {
        String syncNameOfFunction = "____sync_" + asyncNameOfFunction;
        context.property(syncNameOfFunction, function);
        context.evaluateScript(asyncNameOfFunction + " = function(...params) { return new Promise(function(resolve, reject){resolve(" + syncNameOfFunction + "(...params))})}");
    }


    public static void addPreRequirements(JSContext jsContext, Context androidContext) {
        String demoDeps = null;
        try {
            demoDeps = GenericUtils.getFromFile(androidContext, "demoDeps.js");
        } catch (IOException e) {
            e.printStackTrace();
        }
        jsContext.evaluateScript(demoDeps);
    }


    public static void addFetch(JSContext jsContext, Context androidContext) {
        JSPolyFill.addPreRequirements(jsContext, androidContext);
        JSFunction fetch = new JSFunction(jsContext, "fetch") {
            public JSValue fetch(String url, JSObject settings) throws Exception {
                String method = null;
                String credentials = null;
                String body = null;
                JSObject headers = null;
                HashMap<String, String> headersMap = new HashMap<String, String>();

                String methodProperty = settings.property("method").toString();
                String credentialsProperty = settings.property("credentials").toString();
                String bodyProperty = settings.property("body").toString();
                String headerProperty = settings.property("headers").toString();


                if (!headerProperty.equals("undefined")) {
                    headers = settings.property("headers").toObject();
                    if (headers != null) {
                        for (String name : headers.propertyNames()) {
                            headersMap.put(name, headers.property(name).toString());
                        }
                    }
                }
                if (!methodProperty.equals("undefined")) {
                    method = methodProperty;
                }
                if (!credentialsProperty.equals("undefined")) {
                    credentials = credentialsProperty;
                }
                if (!bodyProperty.equals("undefined")) {
                    body = bodyProperty;
                }


                FetchTaskSettings fetchSettings = new FetchTaskSettings(method, credentials, body, headersMap);

                FetchTaskParams params = new FetchTaskParams(url, fetchSettings);
                FetchTask fetchTask = new FetchTask();
                fetchTask.execute(params);
                FetchResponse response = fetchTask.get();

                String uuid = UUID.randomUUID().toString().replace('-', '0');
                String bodyVarName = "tmpJsValForBody_" + uuid;

                context.property(bodyVarName, response.body);
                System.out.println(context.evaluateScript(bodyVarName));
                String responseCreation = String.format("new Response('%s',%s,%s,new Headers([]))", response.urlString, response.code, bodyVarName);
                JSValue globalResponse = context.evaluateScript(responseCreation);
                System.out.println("global response created");
                return globalResponse;
            }
        };
        JSPolyFill.makeFunctionAsyncOnGlobalScope(jsContext, fetch, "fetch");

    }

    static void fixConsoleLog(JSContext jsContext) {
        JSObject console = new JSObject(jsContext);
        console.property("log", new JSFunction(jsContext, "log") {
            public void log(JSValue message) {
                System.out.println("JSConsoleLog: " + message.toJSON());
            }
        });
        jsContext.property("console", console);
    }
}
