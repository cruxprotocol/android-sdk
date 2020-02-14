package com.example.liquid_test_2;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.crux.sdk.CruxClient;
import com.crux.sdk.model.*;
//import com.crux.sdk.model.CruxAddress;
//import com.crux.sdk.model.CruxClientError;
//import com.crux.sdk.model.CruxClientInitConfig;
//import com.crux.sdk.model.CruxClientResponseHandler;
//import com.crux.sdk.model.CruxIDState;
//import com.crux.sdk.model.CruxPutAddressMapSuccess;

import java.io.IOException;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            runScript(this);
        } catch (IOException | CruxClientError e) {
            e.printStackTrace();
        }

    }

    public String runScript(final Context androidContextObject) throws IOException, CruxClientError {

        CruxClientInitConfig.Builder configBuilder = new CruxClientInitConfig.Builder()
                .setWalletClientName("cruxdev")
                .setPrivateKey("cdf2d276caf0c9c34258ed6ebd0e60e0e8b3d9a7b8a9a717f2e19ed9b37f7c6f");

        CruxClient client;
        try {
            client = new CruxClient(configBuilder, androidContextObject);
        } catch(CruxClientError e) {
            Toast.makeText(androidContextObject, "Client caught and reraised:" + e.errorMessage, Toast.LENGTH_LONG).show();
            System.out.println("use debug version for development");
            return null;
        }

        final String testAvailabilityCruxId = "yadu007";
        client.isCruxIDAvailable(testAvailabilityCruxId, new CruxClientResponseHandler<Boolean>() {
            @Override
            public void onResponse(Boolean successResponse) {
                System.out.println("--------isCruxIDAvailable-------");
                System.out.println(successResponse);
                if (successResponse == Boolean.TRUE) {
                    System.out.println(testAvailabilityCruxId + " is available");
                } else {
                    System.out.println(testAvailabilityCruxId + " is not available");
                }
            }

            @Override
            public void onErrorResponse(CruxClientError failureResponse) {
                System.err.println(failureResponse.errorCode);
                System.err.println(failureResponse.errorMessage);
            }
        });

        client.registerCruxID("dev_umang5", new CruxClientResponseHandler<Void>() {
            @Override
            public void onResponse(Void successResponse) {
                System.out.println("--------registerCruxID-------");
                System.out.println(successResponse);
            }

            @Override
            public void onErrorResponse(CruxClientError failureResponse) {
                System.err.println(failureResponse.errorCode);
                System.err.println(failureResponse.errorMessage);
            }
        });

        client.getCruxIDState(new CruxClientResponseHandler<CruxIDState>() {
            @Override
            public void onResponse(CruxIDState successResponse) {
                System.out.println("--------getCruxIDState-------");
                System.out.println(successResponse);
            }

            @Override
            public void onErrorResponse(CruxClientError failureResponse) {
                System.err.println(failureResponse.errorCode);
                System.err.println(failureResponse.errorMessage);
            }
        });

        client.getAddressMap(new CruxClientResponseHandler<HashMap<String, CruxAddress>>() {
            @Override
            public void onResponse(HashMap<String, CruxAddress> successResponse) {
                System.out.println("--------getAddressMap-------");
                System.out.println(successResponse);
            }

            @Override
            public void onErrorResponse(CruxClientError failureResponse) {
                System.err.println(failureResponse.errorCode);
                System.err.println(failureResponse.errorMessage);
            }
        });

        final String testResolveAddressCruxId = "mascot6699@cruxdev.crux";
        client.resolveCurrencyAddressForCruxID(testResolveAddressCruxId, "xrp", new CruxClientResponseHandler<CruxAddress>() {
            @Override
            public void onResponse(CruxAddress successResponse) {
                System.out.println("--------resolveCurrencyAddressForCruxID-------");
                System.out.println(successResponse);
            }

            @Override
            public void onErrorResponse(CruxClientError failureResponse) {
                System.out.println(failureResponse);
            }
        });

        HashMap<String, CruxAddress> newAddressMap = getCurrencyMap();

        client.putAddressMap(newAddressMap, new CruxClientResponseHandler<CruxPutAddressMapSuccess>() {
            @Override
            public void onResponse(CruxPutAddressMapSuccess successResponse) {
                System.out.println("--------putAddressMap-------");
                System.out.println(successResponse);
            }

            @Override
            public void onErrorResponse(CruxClientError failureResponse) {
                System.err.println(failureResponse.errorCode);
                System.err.println(failureResponse.errorMessage);
            }
        });

        return null;
    }

    private HashMap<String, CruxAddress> getCurrencyMap() {
        HashMap<String, CruxAddress> currencyMap = new HashMap<String, CruxAddress>();
        // Add currency and address
        currencyMap.put("btc", new CruxAddress("1HX4KvtPdg9QUYwQE1kNqTAjmNaDG7w82V", null));
        currencyMap.put("eth", new CruxAddress("0x0a2311594059b468c9897338b027c8782398b481", null));
        currencyMap.put("tron", new CruxAddress("TG3iFaVvUs34SGpWq8RG9gnagDLTe1jdyz", null));
        currencyMap.put("xrp", new CruxAddress("rpfKAA2Ezqoq5wWo3XENdLYdZ8YGziz48h", "7777"));
        return currencyMap;
    }

}
