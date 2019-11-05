package com.example.liquid_test_2;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.liquidplayer.javascript.JSContext;
import org.liquidplayer.javascript.JSException;
import org.liquidplayer.javascript.JSFunction;
import org.liquidplayer.javascript.JSObject;
import org.liquidplayer.javascript.JSValue;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

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
        String sdkFile = GenericUtils.getFromFile(androidContextObject, "cruxpay-sdk-dom.js");
        JSContext context = new JSContext();
        JSPolyFill.fixConsoleLog(context);
        JSPolyFill.addFetch(context, androidContextObject);
        JSPolyFill.fixRandomInt(context);
//        System.out.println(context.evaluateScript("Response;"));
//        System.out.println(context.evaluateScript("fetch('https://www.google.com', {method: 'GET', headers:{'a':'asdasd'}}).then(function(res){console.log('Promise.then');console.log(res);console.log(res.content);console.log('=Promise.then over=')}).catch(function(err){console.log('Promise.catch');console.log(err)})"));


        System.out.println("\n\n\n\n====+++++====++++====++\n\n\n\n");
        context.evaluateScript("var window = this;");
        context.evaluateScript("window.crypto = {}; window.crypto.getRandomValues = function(size){let all = new Array();for (let i = 0; i < size; i++) {all[i] = crypto.randomInt();};return all;}");
        context.evaluateScript(sdkFile);
        System.out.println("SDK FILE EVALUATED!");
        System.out.println(context.evaluateScript("cc = new CruxPay.CruxClient({ walletClientName: 'cruxdev', storage: inmemStorage, getEncryptionKey: function(){return 'fookey'}})"));
        System.out.println(context.evaluateScript("cc.init()"));
        JSValue cruxIDStatePromise = context.evaluateScript("cc.getCruxIDState()");
        try {
            PromiseSynchronizer.sync(context, cruxIDStatePromise, 1000);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (PromiseSynchronizerTimeout promiseSynchronizerTimeout) {
            promiseSynchronizerTimeout.printStackTrace();
        }
//        System.out.println(context.evaluateScript("cc.getAddressMap().then(function(res){console.log('getAddressMap result');console.log(res);}).catch(function(err){console.log('errtime getAddressMap');console.log(err.code);console.log(err.message);console.log(err.stack)})"));
//
//        System.out.println("====== Changeing address map! ========");
//        System.out.println(context.evaluateScript("cc.putAddressMap({'bitcoin': {'addressHash': '1HX4KvtPdg9QUYwQE1kNqTAjmNaDG7w82V'}, 'ethereum': {'addressHash': '0x0a2311594059b468c9897338b027c8782398b481'}, 'ripple': {'addressHash': 'rpfKAA2Ezqoq5wWo3XENdLYdZ8YGziz48h', 'secIdentifier': '008888'}, 'tron': {'addressHash': 'TG3iFaVvUs34SGpWq8RG9gnagDLTe1jdyz'}}).catch(function(err){console.log('errtime putAddressMap');console.log(err.code);console.log(err.message);console.log(err.stack)})"));
//        System.out.println(context.evaluateScript("cc.putAddressMap({'btc': {'addressHash': '1HX4KvtPdg9QUYwQE1kNqTAjmNaDG7MMMM'}}).catch(function(err){console.log('errtime putAddressMap');console.log(err.code);console.log(err.message);console.log(err.stack)})"));
//        System.out.println("====== Getting address map again! ========");
//        System.out.println(context.evaluateScript("cc.getAddressMap().then(function(res){console.log('getAddressMap result');console.log(res);})"));
//        System.out.println("====== END fetch ========");
        return null;
    }
}
