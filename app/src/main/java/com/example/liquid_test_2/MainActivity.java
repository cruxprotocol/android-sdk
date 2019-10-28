package com.example.liquid_test_2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.liquidplayer.javascript.JSContext;
import org.liquidplayer.javascript.JSValue;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JSContext context = new JSContext();
        JSValue result = context.evaluateScript("(() => { let x = 10; return x; })()");
        System.out.println("========1");
        System.out.println(result);
        System.out.println("========2");
    }



}
