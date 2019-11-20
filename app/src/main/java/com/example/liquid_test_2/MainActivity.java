package com.example.liquid_test_2;


import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.crux.sdk.CruxClient;
import com.crux.sdk.model.CruxAddress;
import com.crux.sdk.model.CruxAddressMapping;
import com.crux.sdk.model.CruxClientError;
import com.crux.sdk.model.CruxClientInitConfig;
import com.crux.sdk.model.CruxClientResponseHandler;
import com.crux.sdk.model.CruxIDState;
import com.crux.sdk.model.CruxPutAddressMapSuccess;

import java.io.IOException;
import java.util.HashMap;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    Button clientInit;
    Button checkAvailability;
    Button registerId;
    Button getIdState;
    Button getAddressMap;
    Button resolveCurrencyAddress;
    Button putAddressMap;
    EditText cruxId;
    TextView responseView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkAvailability = (Button) findViewById(R.id.checkAvailability);



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
        CruxClientInitConfig.Builder configBuilder = new CruxClientInitConfig.Builder()
                .setWalletClientName("cruxdev")
                .setPrivateKey("KxRwDkwabEq5uT9vyPFeT2GQyNzZC5B8HjYpRYXxwcSmZJxKmVH7");
        final CruxClient client = new CruxClient(configBuilder, androidContextObject);

//        final String testAvailabilityCruxId = "yadu007";

        checkAvailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cruxId = (EditText) findViewById(R.id.cruxId);
                responseView = (TextView) findViewById(R.id.responseView);
                client.isCruxIDAvailable(cruxId.getText().toString(), new CruxClientResponseHandler<Boolean>() {
                    @Override
                    public void onResponse(Boolean successResponse) {
                        System.out.println("--------isCruxIDAvailable-------");
                        System.out.println(successResponse);
                        if (successResponse == Boolean.TRUE) {
                            responseView.setText(cruxId.getText().toString() + " is available");
                        } else {
                            responseView.setText(cruxId.getText().toString() + " is not available");
                        }
                    }

                    @Override
                    public void onErrorResponse(CruxClientError failureResponse) {
                        System.out.println(failureResponse);
                    }
                });
            }
        });

//        client.isCruxIDAvailable(testAvailabilityCruxId, new CruxClientResponseHandler<Boolean>() {
//            @Override
//            public void onResponse(Boolean successResponse) {
//                System.out.println("--------isCruxIDAvailable-------");
//                System.out.println(successResponse);
//                if (successResponse == Boolean.TRUE) {
//                    System.out.println(testAvailabilityCruxId + " is available");
//                } else {
//                    System.out.println(testAvailabilityCruxId + " is not available");
//                }
//            }
//
//            @Override
//            public void onErrorResponse(CruxClientError failureResponse) {
//                System.out.println(failureResponse);
//            }
//        });

        client.registerCruxID("dev_umang2", new CruxClientResponseHandler<Void>() {
            @Override
            public void onResponse(Void successResponse) {
                System.out.println("--------registerCruxID-------");
                System.out.println(successResponse);
            }

            @Override
            public void onErrorResponse(CruxClientError failureResponse) {
                System.out.println(failureResponse);
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
                System.out.println(failureResponse);
            }
        });

        client.getAddressMap(new CruxClientResponseHandler<CruxAddressMapping>() {
            @Override
            public void onResponse(CruxAddressMapping successResponse) {
                System.out.println("--------getAddressMap-------");
                System.out.println(successResponse);
            }

            @Override
            public void onErrorResponse(CruxClientError failureResponse) {
                System.out.println(failureResponse);
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

        CruxAddressMapping newAddressMap = getCruxAddressMapping();

        client.putAddressMap(newAddressMap, new CruxClientResponseHandler<CruxPutAddressMapSuccess>() {
            @Override
            public void onResponse(CruxPutAddressMapSuccess successResponse) {
                System.out.println("--------putAddressMap-------");
                System.out.println(successResponse);
            }

            @Override
            public void onErrorResponse(CruxClientError failureResponse) {
                System.out.println(failureResponse);
            }
        });

        return null;
    }

    private CruxAddressMapping getCruxAddressMapping() {
        CruxAddressMapping newAddressMap = new CruxAddressMapping();
        HashMap<String, CruxAddress> currency = new HashMap<String, CruxAddress>();
        // Add currency and address
        currency.put("btc", new CruxAddress("1HX4KvtPdg9QUYwQE1kNqTAjmNaDG7w82V", null));
        currency.put("eth", new CruxAddress("0x0a2311594059b468c9897338b027c8782398b481", null));
        currency.put("tron", new CruxAddress("TG3iFaVvUs34SGpWq8RG9gnagDLTe1jdyz", null));
        currency.put("xrp", new CruxAddress("rpfKAA2Ezqoq5wWo3XENdLYdZ8YGziz48h", "3434"));
        newAddressMap.currency = currency;
        return newAddressMap;
    }

}
