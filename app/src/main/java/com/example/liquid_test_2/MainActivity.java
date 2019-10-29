package com.example.liquid_test_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;

import org.liquidplayer.javascript.JSContext;
import org.liquidplayer.javascript.JSFunction;
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
            String myFile = MainActivity.getFromFile(androidContextObject,"android-test-dom.js");
            System.out.println("==================fileok=======");
            context.evaluateScript("var window = this");
            context.evaluateScript(myFile);
            System.out.println("==================sbbssb=======");
            System.out.println(context.evaluateScript("window.footest1()"));
            System.out.println("==================xxxxxxxxxxx=======");

//            context.evaluateScript(myF);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }







}
