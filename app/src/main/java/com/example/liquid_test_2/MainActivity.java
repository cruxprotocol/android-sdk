package com.example.liquid_test_2;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.liquidplayer.javascript.JSContext;
import org.liquidplayer.javascript.JSException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        try {
            runScript(this);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSException e) {
            System.out.println("JSEXCEPTION DETECTED");
            System.out.println(e.stack());
        }
    }


    public String runScript(final Context androidContextObject) throws IOException {
        System.out.println("====== START runScript fetch wali ========");
        String sdkFile = GenericUtils.getFromFile(androidContextObject, "sdk-snapshot-1.js");
        JSContext context = new JSContext();
        JSPolyFill.fixConsoleLog(context);
        JSPolyFill.addFetch(context, androidContextObject);
//        System.out.println(context.evaluateScript("Response;"));
//        System.out.println(context.evaluateScript("fetch('https://www.google.com', {method: 'GET', headers:{'a':'asdasd'}}).then(function(res){console.log('Promise.then');console.log(res);console.log(res.content);console.log('=Promise.then over=')}).catch(function(err){console.log('Promise.catch');console.log(err)})"));

        System.out.println("\n\n\n\n====+++++====++++====++\n\n\n\n");
        context.evaluateScript("var window = this;");
        context.evaluateScript(sdkFile);
        System.out.println("SDK FILE EVALUATED!");
        System.out.println(context.evaluateScript("cc = new CruxPay.CruxClient({ walletClientName: 'cruxdev', getEncryptionKey: function(){return 'fookey'}})"));
        System.out.println(context.evaluateScript("cc.init()"));
        System.out.println(context.evaluateScript("cc.getCruxIDState().then(function(res){console.log('getCruxIdState result');console.log(res);}).catch(function(err){console.log('errtime');console.log(err.code);console.log(err.message);console.log(err.stack)})"));

        System.out.println("====== END fetch ========");
        return null;
    }


}
