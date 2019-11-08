package com.example.liquid_test_2;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.crux.cruxsdk.AnkitTest;
import com.crux.cruxsdk.CruxClient;
import com.crux.cruxsdk.model.CruxClientError;
import com.crux.cruxsdk.model.CruxClientResponseHandler;
import com.crux.cruxsdk.model.CruxIDState;

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
        }
//        catch (JSException e) {
//            System.out.println("JSEXCEPTION DETECTED");
//            System.out.println(e.stack());
//        }
    }

    public String runScript(final Context androidContextObject) throws IOException {
        CruxClient client = new CruxClient("cruxdev", androidContextObject);
        client.getCruxIDState(new CruxClientResponseHandler<CruxIDState>() {
            @Override
            public void onResponse(CruxIDState successResponse) {
                System.out.println(successResponse);
            }

            @Override
            public void onErrorResponse(CruxClientError failureResponse) {
                System.out.println(failureResponse);
            }
        });

        return null;
    }



    public String runScript2(final Context androidContextObject) throws IOException {
        System.out.println(new AnkitTest().foo());
        return null;
    }

//    public String runScript2(final Context androidContextObject) throws IOException {
//        System.out.println("====== START runScript fetch wali ========");
//        String sdkFile = GenericUtils.getFromFile(androidContextObject, "cruxpay-sdk-dom.js");
//        JSContext context = new JSContext();
//        JSPolyFill.fixConsoleLog(context);
//        JSPolyFill.addFetch(context, androidContextObject);
//        JSPolyFill.fixRandomInt(context);
////        System.out.println(context.evaluateScript("Response;"));
////        System.out.println(context.evaluateScript("fetch('https://www.google.com', {method: 'GET', headers:{'a':'asdasd'}}).then(function(res){console.log('Promise.then');console.log(res);console.log(res.content);console.log('=Promise.then over=')}).catch(function(err){console.log('Promise.catch');console.log(err)})"));
//// Request a string response from the provided URL.
//
//        System.out.println("\n\n\n\n====+++++====++++====++\n\n\n\n");
//        context.evaluateScript("var window = this;");
//        context.evaluateScript("window.crypto = {}; window.crypto.getRandomValues = function(size){let all = new Array();for (let i = 0; i < size; i++) {all[i] = crypto.randomInt();};return all;}");
//        context.evaluateScript(sdkFile);
//        System.out.println("SDK FILE EVALUATED!");
//        System.out.println(context.evaluateScript("cc = new CruxPay.CruxClient({ walletClientName: 'cruxdev', storage: inmemStorage, getEncryptionKey: function(){return 'fookey'}})"));
//        System.out.println(context.evaluateScript("cc.init()"));
//        JSValue cruxIDStatePromise = context.evaluateScript("cc.getCruxIDState()");
//        try {
//            JSValue result = PromiseSynchronizer.sync(context, cruxIDStatePromise, 1000);
//            JSFunction stringify = context.property("JSON.stringify").toFunction();
//            JSValue stringifyResult = stringify.call(result.toObject());
//            String javaString = stringifyResult.toString();
//            System.out.println("!!! javastring !!!");
//            System.out.println(javaString);
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (PromiseSynchronizerTimeout promiseSynchronizerTimeout) {
//            promiseSynchronizerTimeout.printStackTrace();
//        } catch (PromiseSynchronizerRejected promiseSynchronizerRejected) {
//            promiseSynchronizerRejected.printStackTrace();
//        }
//        return null;
//    }
}
