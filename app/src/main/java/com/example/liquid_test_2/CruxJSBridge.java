package com.example.liquid_test_2;

import android.content.Context;

import org.liquidplayer.javascript.JSContext;
import org.liquidplayer.javascript.JSFunction;
import org.liquidplayer.javascript.JSObject;
import org.liquidplayer.javascript.JSValue;

import java.io.IOException;

public class CruxJSBridge {
    private final JSContext jsContext;
    private final JSObject jsClient;

    CruxJSBridge(String walletName, Context androidContextObject) throws IOException {
        this.jsContext = this.getContextForClient(androidContextObject);
        System.out.println(jsContext.evaluateScript("cc = new CruxPay.CruxClient({ walletClientName: 'cruxdev', storage: inmemStorage, getEncryptionKey: function(){return 'fookey'}})"));
        System.out.println(jsContext.evaluateScript("cc.init()"));
        this.jsClient = jsContext.property("cc").toObject();
        // TODO This must be blocking here!
    }

    private JSContext getContextForClient(Context androidContextObject) throws IOException {
        String sdkFile = GenericUtils.getFromFile(androidContextObject, "cruxpay-sdk-dom.js");
        JSContext jsContext = new JSContext();
        JSPolyFill.fixConsoleLog(jsContext);
        JSPolyFill.addFetch(jsContext, androidContextObject);
        JSPolyFill.fixRandomInt(jsContext);
        jsContext.evaluateScript("var window = this;");
        jsContext.evaluateScript("window.crypto = {}; window.crypto.getRandomValues = function(size){let all = new Array();for (let i = 0; i < size; i++) {all[i] = crypto.randomInt();};return all;}");
        jsContext.evaluateScript(sdkFile);
        return jsContext;
    }

    public void executeAsync(final CruxJSBridgeAsyncRequest request) {
        JSFunction jsMethod = jsClient.property(request.method).toFunction();
        JSObject promise = jsMethod.call(null, request.params.args).toObject();



        JSFunction jsSuccessHandler = new JSFunction(jsContext, "jsSuccessHandler") {
            public void jsSuccessHandler(JSValue successResponse) {
                request.handler.onResponse(successResponse.toObject());
            }
        };

        JSFunction jsFailureHandler = new JSFunction(jsContext, "jsFailureHandler") {
            public void jsFailureHandler(JSValue failureResponse) {
                request.handler.onErrorResponse(failureResponse.toObject());
            }
        };

        JSFunction promiseThen = promise.property("then").toFunction();
        JSFunction promiseCatch = promise.property("catch").toFunction();

        promiseThen.call(promise, jsSuccessHandler);
        promiseCatch.call(promise, jsFailureHandler);
    }
}
