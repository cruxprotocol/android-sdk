package com.example.liquid_test_2;

import android.os.AsyncTask;

import org.liquidplayer.javascript.JSContext;
import org.liquidplayer.javascript.JSObject;
import org.liquidplayer.javascript.JSValue;

import java.util.Date;
import java.util.Timer;
import java.util.UUID;
import java.util.concurrent.ExecutionException;


class PromiseTaskParams {
    public JSContext context;
    public JSValue promise;
    public Integer timeout;

    PromiseTaskParams(JSContext context, JSValue promise, Integer timeout) {
        this.context = context;
        this.promise = promise;
        this.timeout = timeout;
    }
}

class PromiseTaskResult {
    public JSValue successResult;
    public JSValue failureResult;

    PromiseTaskResult(JSValue successResult, JSValue failureResult) {
        this.successResult = successResult;
        this.failureResult = failureResult;
    }
}

class PromiseTask extends AsyncTask<PromiseTaskParams, Void, PromiseTaskResult> {
    protected PromiseTaskResult doInBackground(PromiseTaskParams... params) {
        String uuid = UUID.randomUUID().toString().replace('-', '0');
        String tmpScopeVarName = "tmpPromiseTaskScope" + uuid;
        JSContext jsContext = params[0].context;
        JSObject tmpScope = new JSObject(jsContext);
        tmpScope.property("currentPromise", params[0].promise);
        jsContext.property(tmpScopeVarName, tmpScope);
        jsContext.evaluateScript(String.format("%s.currentPromise.then(function(res){%s.successResult=res}).catch(function(err){%s.errorResult=res})", tmpScopeVarName, tmpScopeVarName, tmpScopeVarName));

        boolean terminationCondition = false;
        Timer t = new Timer();

        long startTime = new Date().getTime();
        PromiseTaskResult taskResult = null;
        while (!terminationCondition) {

            JSValue latestSuccessResult = jsContext.evaluateScript(String.format("%s.successResult", tmpScopeVarName));
            JSValue latestFailureResult = jsContext.evaluateScript(String.format("%s.failureResult", tmpScopeVarName));

            if (!latestSuccessResult.isUndefined() || !latestFailureResult.isUndefined()) {
                terminationCondition = true;
                taskResult = new PromiseTaskResult(latestSuccessResult, latestFailureResult);
            } else {

                long nowTime = new Date().getTime();
                if (nowTime - startTime > params[0].timeout)
                    terminationCondition = true;
                try {
                    //milliseconds
                    int intervalForChecking = 100;
                    Thread.sleep(intervalForChecking);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return taskResult;
    }
}

class PromiseSynchronizerTimeout extends Exception {
    public PromiseSynchronizerTimeout(String s) {
        super(s);
    }
}

class PromiseSynchronizerRejected extends Exception {
    public PromiseSynchronizerRejected(String s) {
        super(s);
    }
}


public class PromiseSynchronizer {

    public static JSValue sync(JSContext jsContext, JSValue promiseObject, Integer timeout) throws ExecutionException, InterruptedException, PromiseSynchronizerTimeout, PromiseSynchronizerRejected {
        PromiseTaskParams taskParams = new PromiseTaskParams(jsContext, promiseObject, timeout);
        PromiseTask promiseTask = new PromiseTask();
        promiseTask.execute(taskParams);
        PromiseTaskResult promiseTaskResult = promiseTask.get();
        if (promiseTaskResult.failureResult.isUndefined() && promiseTaskResult.successResult.isUndefined()) {
           throw new PromiseSynchronizerTimeout("No successResult or failureResult. Looks like TIMEOUT!");
        }
        if(!promiseTaskResult.failureResult.isUndefined()) {
            throw new PromiseSynchronizerRejected("Promise has been rejected");
        }
        return promiseTaskResult.successResult;
    }

}
