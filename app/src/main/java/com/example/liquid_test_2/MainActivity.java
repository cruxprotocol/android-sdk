package com.example.liquid_test_2;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.crux.sdk.CruxClient;
import com.crux.sdk.model.*;

import java.io.IOException;
import java.util.ArrayList;
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
        final Boolean[] success = {true};
        final ArrayList<String> failures = new ArrayList<>();
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
                String failureString = "--------isCruxIDAvailable-Failed----------";
                success[0] = false;
                failures.add(failureString);
                System.out.println(failureString);
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
                String failureString = "--------registerCruxID-Failed---------";
                // Commenting because already registered and will always fail
//                success[0] = false;
//                failures.add(failureString);
                System.out.println(failureString);
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
                String failureString = "--------getCruxIDState-Failed---------";
                success[0] = false;
                failures.add(failureString);
                System.out.println(failureString);
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
                String failureString = "--------getAddressMap-Failed--------";
                success[0] = false;
                failures.add(failureString);
                System.out.println(failureString);
                System.err.println(failureResponse.errorCode);
                System.err.println(failureResponse.errorMessage);
            }
        });

        client.getEnabledAssetGroups(new CruxClientResponseHandler<String[]>() {
            @Override
            public void onResponse(String[] successResponse) {
                System.out.println("--------getEnabledAssetGroups-------");
                System.out.println(successResponse);
            }

            @Override
            public void onErrorResponse(CruxClientError failureResponse) {
                String failureString = "--------getEnabledAssetGroups-Failed----------";
                success[0] = false;
                failures.add(failureString);
                System.out.println(failureString);
                System.err.println(failureResponse.errorCode);
                System.err.println(failureResponse.errorMessage);
            }
        });

        final String[] assetGroups = {"ERC20_eth"};
        client.putEnabledAssetGroups(assetGroups, new CruxClientResponseHandler<String[]>() {
            @Override
            public void onResponse(String[] successResponse) {
                System.out.println("--------putEnabledAssetGroups-------");
                System.out.println(successResponse);
            }

            @Override
            public void onErrorResponse(CruxClientError failureResponse) {
                String failureString = "--------putEnabledAssetGroups-Failed---------";
                success[0] = false;
                failures.add(failureString);
                System.out.println(failureString);
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
                String failureString = "--------resolveCurrencyAddressForCruxID-Failed--------";
                success[0] = false;
                failures.add(failureString);
                System.out.println(failureString);
                System.out.println(failureResponse);
            }
        });

        HashMap<String, String> assetMatcher = getAssetMatcher();
        client.resolveAssetAddressForCruxID(testResolveAddressCruxId, assetMatcher, new CruxClientResponseHandler<CruxAddress>() {
            @Override
            public void onResponse(CruxAddress successResponse) {
                System.out.println("--------resolveAssetAddressForCruxID-------");
                System.out.println(successResponse);
            }

            @Override
            public void onErrorResponse(CruxClientError failureResponse) {
                String failureString = "--------resolveAssetAddressForCruxID-Failed--------";
                success[0] = false;
                failures.add(failureString);
                System.out.println(failureString);
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
                String failureString = "--------putAddressMap-Failed--------";
                success[0] = false;
                failures.add(failureString);
                System.out.println(failureString);
                System.err.println(failureResponse.errorCode);
                System.err.println(failureResponse.errorMessage);
            }
        });

        HashMap<String, CruxAddress> newAddressMapPrivate = new HashMap<String, CruxAddress>();
        newAddressMapPrivate.put("btc", new CruxAddress("bc1q34aq5drpuwy3wgl9lhup9892qp6svr8ldzyy7c", null));
        String[] fullCruxIDs = {"mascot6699@cruxdev.crux"};
        client.putPrivateAddressMap(fullCruxIDs, newAddressMapPrivate, new CruxClientResponseHandler<CruxPutPrivateAddressMapResult>() {
            @Override
            public void onResponse(CruxPutPrivateAddressMapResult successResponse) {
                System.out.println("--------putPrivateAddressMap-------");
                System.out.println(successResponse);
            }

            @Override
            public void onErrorResponse(CruxClientError failureResponse) {
                String failureString = "--------putPrivateAddressMap-Failed--------";
                success[0] = false;
                failures.add(failureString);
                System.out.println(failureString);
                System.err.println(failureResponse.errorCode);
                System.err.println(failureResponse.errorMessage);
            }
        });

        if (!success[0]) {
            System.out.println("=========FAILED============!!");
            System.out.println(failures);
        } else {
            System.out.println("=========SUCCESS============!!");
        }


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

    private HashMap<String, String> getAssetMatcher() {
        HashMap<String, String> assetMatcher = new HashMap<>();
        assetMatcher.put("assetGroup", "ERC20_ETH");
//        assetMatcher.put("assetIdentifierValue", "0xE41d2489571d322189246DaFA5ebDe1F4699F498")
        return assetMatcher;
    }

}
