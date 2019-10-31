package com.example.liquid_test_2;


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

    FetchTaskSettings(String method, String credentials, String body) {
        this.method = method;
        this.credentials = credentials;
        this.body = body;
    }
}

class FetchTask extends AsyncTask<FetchTaskParams, Void, String> {

    private String downloadContent(String myurl, FetchTaskSettings settings) throws IOException {
        InputStream is = null;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            int response = conn.getResponseCode();
            System.out.println("The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = convert(is);
            return contentAsString;
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


    protected String doInBackground(FetchTaskParams... params) {

        String content = "";
        System.out.println("FetchTask doInBackground start");
        try {
            content = downloadContent(params[0].url, params[0].settings);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(content);
        System.out.println("FetchTask doInBackground stop");
        return content;
    }
}


public class JSPolyFill {

    private static void makeFunctionAsyncOnGlobalScope(JSContext context, JSFunction function, String asyncNameOfFunction){
        String syncNameOfFunction = "____sync_" + asyncNameOfFunction;
        context.property(syncNameOfFunction, function);
        context.evaluateScript(asyncNameOfFunction + " = function(...params) { return new Promise(function(resolve, reject){resolve("+syncNameOfFunction +"(...params))})}");
    }

    static void addFetch(JSContext jsContext) {
        JSFunction fetch = new JSFunction(jsContext,"fetch") {
            public String fetch(String url, JSObject settings) throws Exception {


                String method = null;
                String credentials = null;
                String body = null;

                String methodProperty = settings.property("method").toString();
                String credentialsProperty = settings.property("credentials").toString();
                String bodyProperty = settings.property("body").toString();

                if (!methodProperty.equals("undefined")) {
                    method = methodProperty;
                }
                if (!credentialsProperty.equals("undefined")) {
                    credentials = credentialsProperty;
                }
                if (!bodyProperty.equals("undefined")) {
                    body = bodyProperty;
                }


                FetchTaskSettings fetchSettings = new FetchTaskSettings(method, credentials, body);

                FetchTaskParams params = new FetchTaskParams(url, fetchSettings);
                FetchTask fetchTask = new FetchTask();
                fetchTask.execute(params);
                return fetchTask.get();
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
