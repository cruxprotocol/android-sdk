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

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

public class MainActivity extends AppCompatActivity {


    public interface IAsyncObj {
        @SuppressWarnings("unused")
        void callMeMaybe(Integer ms, JSValue callback) throws JSException;
    }

    public class AsyncObj extends JSObject implements IAsyncObj {
        public AsyncObj(JSContext ctx) throws JSException { super(ctx,IAsyncObj.class); }
        @Override
        public void callMeMaybe(final Integer ms, final JSValue callback) throws JSException {
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
            runScript2(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static String getFromFile(Context androidContextObject, String fileName) throws IOException {
        AssetManager assetManager = androidContextObject.getAssets();
        System.out.println(assetManager.list("/"));
        InputStream is = assetManager.open(fileName);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader input = new BufferedReader(isr);
        String line;
        StringBuilder returnString = new StringBuilder();
        while ((line = input.readLine())!= null) {
            returnString.append(line);
            returnString.append("\n");
        }
        return new String(returnString);

    }

    public String runScript2(Context androidContextObject) throws IOException {
        System.out.println("====== START runScript2 ========");
        JSContext context = new JSContext();
        MainActivity.fixLogging(context);
//        String myFile = MainActivity.getFromFile(androidContextObject,"android-test-dom.js");


        context.evaluateScript("var window = this");
        context.evaluateScript("var self = this");


        AsyncObj async = new AsyncObj(context);
        context.property("async", async);
        context.evaluateScript(
                "console.log('Please call me back in 5 seconds');\n" +
                        "async.callMeMaybe(5000, function(msg) {\n" +
                        "    console.log(msg);\n" +
                        "    console.log('Whoomp. There it is.');\n" +
                        "});\n" +
                        "console.log('async.callMeMaybe() has returned, but wait for it ...');\n"
        );

//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


        System.out.println("====== END ========");
        return null;
    }

    private static void fixLogging(JSContext context) {
        JSObject console = new JSObject(context);
        console.property("log", new JSFunction(context, "log") {
            public void log(JSValue message) {
                System.out.println("JSConsoleLog: " + message.toJSON());
            }
        });
        context.property("console", console);
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
