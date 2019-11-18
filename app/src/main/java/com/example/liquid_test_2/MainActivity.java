package com.example.liquid_test_2;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.crux.sdk.CruxClient;
import com.crux.sdk.model.CruxAddressMapping;
import com.crux.sdk.model.CruxClientError;
import com.crux.sdk.model.CruxClientResponseHandler;
import com.crux.sdk.model.CruxIDState;

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
                System.out.println("--------getCruxIDState-------");
                System.out.println(successResponse);
            }

            @Override
            public void onErrorResponse(CruxClientError failureResponse) {
                System.out.println(failureResponse);
            }
        });

//        client.registerCruxID("test_43", new CruxClientResponseHandler<String>() {
//            @Override
//            public void onResponse(String successResponse) {
//                System.out.println("--------registerCruxID-------");
//                System.out.println(successResponse);
//            }
//
//            @Override
//            public void onErrorResponse(CruxClientError failureResponse) {
//                System.out.println(failureResponse);
//            }
//        });
//
//        client.getAddressMap(new CruxClientResponseHandler<CruxAddressMapping>() {
//            @Override
//            public void onResponse(CruxAddressMapping successResponse) {
//                System.out.println("--------getAddressMap-------");
//                System.out.println(successResponse);
//            }
//
//            @Override
//            public void onErrorResponse(CruxClientError failureResponse) {
//                System.out.println(failureResponse);
//            }
//        });

        final String testCruxId = "yadu007";
        client.isCruxIDAvailable(testCruxId, new CruxClientResponseHandler<Boolean>() {
            @Override
            public void onResponse(Boolean successResponse) {
                System.out.println("--------isCruxIDAvailable-------");
                System.out.println(successResponse);
                if (successResponse == Boolean.TRUE) {
                    System.out.println(testCruxId + " is available");
                } else {
                    System.out.println(testCruxId + " is not available");
                }
            }

            @Override
            public void onErrorResponse(CruxClientError failureResponse) {
                System.out.println(failureResponse);
            }
        });

        return null;
    }

}
