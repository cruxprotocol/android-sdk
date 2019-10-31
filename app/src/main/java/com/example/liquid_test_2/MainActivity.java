package com.example.liquid_test_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;

import org.liquidplayer.javascript.JSContext;
import org.liquidplayer.javascript.JSException;
import org.liquidplayer.javascript.JSFunction;
import org.liquidplayer.javascript.JSObject;
import org.liquidplayer.javascript.JSValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

import android.os.Handler;
import android.os.Looper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    private static class FetchTaskParams {
        public String url;
        public String method;

        FetchTaskParams(String url, String method) {
            this.url = url;
            this.method = method;
        }
    }

    private class FetchTask extends AsyncTask<FetchTaskParams, Void, String> {

        private String downloadContent(String myurl, String method) throws IOException {
            InputStream is = null;

            try {
                URL url = new URL(myurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod(method);
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
                content = downloadContent(params[0].url, params[0].method);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(content);
            System.out.println("FetchTask doInBackground stop");
            return content;
        }
    }




    public class AsyncObj extends JSObject {
        public AsyncObj(JSContext ctx) throws JSException { }
        public void fetch(final Integer ms, final JSValue callback) throws JSException {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(
                    new Runnable() {
                        @Override
                        public void run() {
                            new CallMeLater(ms).execute(callback.toFunction());
                        }
                    }
            );
        }

        private class CallMeLater extends AsyncTask<JSFunction, Void, JSFunction> {
            public CallMeLater(Integer ms) {
                this.ms = ms;
            }
            private final Integer ms;
            @Override
            protected JSFunction doInBackground(JSFunction... params) {
                try {
                    Thread.sleep(ms);
                } catch (InterruptedException e) {
                    Thread.interrupted();
                }
                return params[0];
            }

            @Override
            protected void onPostExecute(JSFunction callback) {
                try {
                    callback.call(null, "This is a delayed message from Java!");
                } catch (JSException e) {
                    System.out.println(e.toString());
                }
            }
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        try {
//            runScript(this);
            runScript3(this);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSException e) {
            System.out.println("JSEXCEPTION DETECTED");
            System.out.println(e.stack());
        }
    }



    public String runScript3(final Context androidContextObject) throws IOException {
        System.out.println("====== START runScript fetch wali ========");
        String sdkFile = GenericUtils.getFromFile(androidContextObject,"crux-test-sdk.js");
        JSContext context = new JSContext();
        JSPolyFill.fixConsoleLog(context);
        JSPolyFill.addFetch(context, androidContextObject);
//        System.out.println(context.evaluateScript("Response;"));
//        System.out.println(context.evaluateScript("fetch('https://www.google.com', {method: 'GET', headers:{'a':'asdasd'}}).then(function(res){console.log('Promise.then');console.log(res);console.log(res.content);console.log('=Promise.then over=')}).catch(function(err){console.log('Promise.catch');console.log(err)})"));

        System.out.println("\n\n\n\n====+++++====++++====++\n\n\n\n");
        context.evaluateScript("var window = this;");
        context.evaluateScript(sdkFile);
        System.out.println("SDK FILE EVALUATED!");
        System.out.println(context.evaluateScript("CruxClient"));
        System.out.println("====== END fetch ========");
        return null;
    }


    public String runScript2(Context androidContextObject) throws IOException {
        System.out.println("====== START runScript2 ========");
        JSContext context = new JSContext();
        JSPolyFill.fixConsoleLog(context);
        String sdkFile = GenericUtils.getFromFile(androidContextObject,"android-test-dom.js");

        AsyncObj fetchPolyfill = new AsyncObj(context);
        context.property("fetchPolyfill", fetchPolyfill);

        context.evaluateScript("var window = this");
        context.evaluateScript("var self = this; window.fetch = fetchPolyfill.fetch;");



        context.evaluateScript(
                "console.log('Please call me back in 5 seconds');\n" +
                        "fetch(5000, function(msg) {\n" +
                        "    console.log(msg);\n" +
                        "    console.log('Whoomp. There it is.');\n" +
                        "});\n" +
                        "console.log('fetch() has returned, but wait for it ...');\n"
        );


        context.evaluateScript("fetch('https://www.google.com')"
                        + "  .then("
                        + "    function(response) {"
                        + "      if (response.status !== 200) {"
                        + "        console.log('Looks like there was a problem. Status Code: ' +"
                        + "          response.status);"
                        + "        return;"
                        + "      }"

                        + "      // Examine the text in the response"
                        + "      response.json().then(function(data) {"
                        + "        console.log(data);"
                        + "      });"
                        + "    }"
                        + "  )"
                        + "  .catch(function(err) {"
                        + "    console.log('Fetch Error :-S', err);"
                        + "  });"
        );

//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


        System.out.println("====== END ========");
        return null;
    }

//    public static String runScript(Context androidContextObject) {
//        try {
//            JSContext context = new JSContext();
//
//
//            MainActivity.fixLogging(context);
//
////            String fetchPolyFill = MainActivity.getFromFile(androidContextObject,"fetch.umd.js");
////            String xmlPolyfill = MainActivity.getFromFile(androidContextObject,"xmlhttprequest_polyfill.js");
//            String myFile = MainActivity.getFromFile(androidContextObject,"android-test-dom.js");
//
//
//            System.out.println("==================fileok=======");
//            context.evaluateScript("var window = this");
//            context.evaluateScript("var self = this");
////            context.evaluateScript("window.location = {'protocol': 'asdasd'}");
//            context.evaluateScript(xmlPolyfill);
//
//            System.out.println("==================-1=======");
//            context.evaluateScript(myFile);
//            System.out.println("==================sbbssb=======");
//            context.evaluateScript(fetchPolyFill);
////            System.out.println(context.evaluateScript("window.footest1()"));
//            System.out.println("==================xxxxxxxxxxx=======");
////            System.out.println(context.evaluateScript("console.log('YOLOPRINTPLS');"));
//            System.out.println("==================0=======");
////            System.out.println(context.evaluateScript("Response;"));
//            System.out.println("==================1=======");
//            System.out.println(context.evaluateScript("fetch('https://www.google.com').then(function(response) {console.log('success'); console.log(response)}, function(err){console.log('fail'); console.log(err.stack)});"));
//            System.out.println("sleeping");
//            Thread.sleep(10000);
//            System.out.println("==================2=======");
////            System.out.println(context.evaluateScript("crypto;"));
//            System.out.println("==================3=======");
//
////            context.evaluateScript(myF);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }







}
