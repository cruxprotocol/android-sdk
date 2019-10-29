package com.example.liquid_test_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;

import org.liquidplayer.javascript.JSContext;
import org.liquidplayer.javascript.JSFunction;
import org.liquidplayer.javascript.JSObject;
import org.liquidplayer.javascript.JSValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        runScript(this);
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

    public static String runScript(Context androidContextObject) {
        try {
            JSContext context = new JSContext();


            JSObject console = new JSObject(context);
            console.property("log", new JSFunction(context, "log") {
                public void log(JSValue message) {
                    System.out.println("JSConsoleLog: " + message.toJSON());
                }
            });
            context.property("console", console);


            String fetchPolyFill = MainActivity.getFromFile(androidContextObject,"fetch.umd.js");
            String myFile = MainActivity.getFromFile(androidContextObject,"android-test-dom.js");
            System.out.println("==================fileok=======");
            context.evaluateScript("var window = this");
            context.evaluateScript("var self = this");
            context.evaluateScript(fetchPolyFill);
            System.out.println("==================polyfilled=======");
            context.evaluateScript(myFile);
            System.out.println("==================sbbssb=======");
//            System.out.println(context.evaluateScript("window.footest1()"));
            System.out.println("==================xxxxxxxxxxx=======");
//            System.out.println(context.evaluateScript("console.log('YOLOPRINTPLS');"));
            System.out.println("==================0=======");
//            System.out.println(context.evaluateScript("Response;"));
            System.out.println("==================1=======");
            System.out.println(context.evaluateScript("fetch('https://www.google.com').then(function(response) {console.log('success'); console.log(response)}, function(err){console.log('fail'); console.log(err.stack)});"));
            Thread.sleep(5000);
            System.out.println("==================2=======");
//            System.out.println(context.evaluateScript("crypto;"));
            System.out.println("==================3=======");

//            context.evaluateScript(myF);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }







}
