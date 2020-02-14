package com.crux.sdk.bridge;


import android.content.Context;
import android.os.AsyncTask;

import org.liquidplayer.javascript.JSContext;
import org.liquidplayer.javascript.JSFunction;
import org.liquidplayer.javascript.JSObject;
import org.liquidplayer.javascript.JSValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
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
            if(settings.body != null) {
                OutputStream op = conn.getOutputStream();
                byte[] bodyBytes = settings.body.getBytes();
                op.write(bodyBytes);
            }
            int responseCode = conn.getResponseCode();
            if (responseCode >= 200 && responseCode <400) {
                is = conn.getInputStream();

                // Convert the InputStream into a string
                String contentAsString = convert(is);
                Map<String, List<String>> headerFields = conn.getHeaderFields();

                FetchResponse response = new FetchResponse(url.toString(), responseCode, contentAsString, headerFields);
                return response;
            }
            else{
                is = conn.getErrorStream();
                String contentAsString = convert(is);
                Map<String, List<String>> headerFields = conn.getHeaderFields();

                FetchResponse response = new FetchResponse(url.toString(), responseCode, contentAsString, headerFields);
                return response;
            }
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
        try {
            response = downloadContent(params[0].url, params[0].settings);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
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

                String responseCreation = String.format("new Response('%s',%s,%s,new Headers([]))", response.urlString, response.code, bodyVarName);
                JSValue globalResponse = context.evaluateScript(responseCreation);
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

    static void fixRandomInt(JSContext jsContext) {
        JSObject crypto = new JSObject(jsContext);
        crypto.property("randomInt", new JSFunction(jsContext, "randomInt"){ public int randomInt() {
            SecureRandom random = new SecureRandom();
            int randomInteger = random.nextInt(256);
            return randomInteger;
        }
        });
        jsContext.property("crypto", crypto);
    }
}
