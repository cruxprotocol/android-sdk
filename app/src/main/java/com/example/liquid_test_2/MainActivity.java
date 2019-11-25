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
    EditText resolveCurrencyCruxId;
    TextView responseViewIdAvailability;
    TextView responseViewRegisterID;
    TextView responseViewIdState;
    TextView responseViewAddressMap;
    TextView responseViewCurrencyAddress;
    TextView responseViewPutAddressMap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cruxId = (EditText) findViewById(R.id.cruxId);
        resolveCurrencyCruxId = (EditText) findViewById(R.id.resolveCurrencyCruxId);
        checkAvailability = (Button) findViewById(R.id.checkAvailability);
        registerId = (Button) findViewById(R.id.registerId);
        getIdState = (Button) findViewById(R.id.getIdState);
        getAddressMap = (Button) findViewById(R.id.getAddressMap);
        resolveCurrencyAddress = (Button) findViewById(R.id.resolveCurrencyAddress);
        putAddressMap = (Button) findViewById(R.id.putAddressMap);



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
                .setPrivateKey("KwuSrhqpMeDP3rrULvHhnvjmAeWTD5XTTGvBQnYU5AU856N8cUp5");
        final CruxClient client = new CruxClient(configBuilder, androidContextObject);

        checkAvailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                responseViewIdAvailability = (TextView) findViewById(R.id.responseViewIdAvailability);
                client.isCruxIDAvailable(cruxId.getText().toString(), new CruxClientResponseHandler<Boolean>() {
                    @Override
                    public void onResponse(Boolean successResponse) {
                        System.out.println("--------isCruxIDAvailable-------");
                        System.out.println(successResponse);
                        if (successResponse == Boolean.TRUE) {
                            responseViewIdAvailability.setText(cruxId.getText().toString() + " is available");
                        } else {
                            responseViewIdAvailability.setText(cruxId.getText().toString() + " is not available");
                        }
                    }

                    @Override
                    public void onErrorResponse(CruxClientError failureResponse) {
                        responseViewIdAvailability.setText((CharSequence) failureResponse);
                    }
                });
            }
        });

        registerId.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  responseViewRegisterID = (TextView) findViewById(R.id.responseViewRegisterID);
                  client.registerCruxID(cruxId.getText().toString(), new CruxClientResponseHandler<Void>() {
                      @Override
                      public void onResponse(Void successResponse) {
                          System.out.println("--------registerCruxID-------");
                          System.out.println(successResponse);
                          responseViewIdAvailability.setText(cruxId.getText().toString() + " registration was received");
                      }

                      @Override
                      public void onErrorResponse(CruxClientError failureResponse) {
                          System.out.println("--------registerCruxIDxxxxxxx-------");
                          System.out.println(failureResponse);
                          responseViewIdAvailability.setText("Private key linked to another account");
                      }
                  });
              }
        });


        getIdState.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  responseViewIdState = (TextView) findViewById(R.id.responseViewIdState);
                  client.getCruxIDState(new CruxClientResponseHandler<CruxIDState>() {
                      @Override
                      public void onResponse(CruxIDState successResponse) {
                          System.out.println("--------getCruxIDState-------");
                          responseViewIdState.setText(successResponse.status.statusDetail.toString());
                      }

                      @Override
                      public void onErrorResponse(CruxClientError failureResponse) {
                          System.out.println(failureResponse);
                          responseViewIdState.setText(failureResponse.errorCode.toString() + failureResponse.errorMessage.toString());
                      }
                  });
              }
        });

        getAddressMap.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  responseViewAddressMap = (TextView) findViewById(R.id.responseViewAddressMap);
                  client.getAddressMap(new CruxClientResponseHandler<CruxAddressMapping>() {
                      @Override
                      public void onResponse(CruxAddressMapping successResponse) {
                          System.out.println("--------getAddressMap-------");
                          System.out.println(successResponse);
                          responseViewAddressMap.setText(successResponse.currency.toString());
                      }

                      @Override
                      public void onErrorResponse(CruxClientError failureResponse) {
                          System.out.println(failureResponse);
                          responseViewAddressMap.setText(failureResponse.errorCode.toString() + failureResponse.errorMessage.toString());
                      }
                  });
              }
        });

        resolveCurrencyAddress.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 responseViewCurrencyAddress = (TextView) findViewById(R.id.responseViewCurrencyAddress);
                 client.resolveCurrencyAddressForCruxID(resolveCurrencyCruxId.getText().toString(), "xrp", new CruxClientResponseHandler<CruxAddress>() {
                     @Override
                     public void onResponse(CruxAddress successResponse) {
                         System.out.println("--------resolveCurrencyAddressForCruxID-------");
                         System.out.println(successResponse);
                         responseViewCurrencyAddress.setText(successResponse.addressHash.toString());
                     }

                     @Override
                     public void onErrorResponse(CruxClientError failureResponse) {
                         System.out.println(failureResponse);
                         responseViewCurrencyAddress.setText(failureResponse.errorCode.toString() + failureResponse.errorMessage.toString());
                     }
                 });
             }
        });



        putAddressMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CruxAddressMapping newAddressMap = getCruxAddressMapping();
                responseViewPutAddressMap = (TextView) findViewById(R.id.responseViewPutAddressMap);
                client.putAddressMap(newAddressMap, new CruxClientResponseHandler<CruxPutAddressMapSuccess>() {
                    @Override
                    public void onResponse(CruxPutAddressMapSuccess successResponse) {
                        System.out.println("--------putAddressMap-------");
                        System.out.println(successResponse);
                        responseViewPutAddressMap.setText(successResponse.hashCode());
                    }

                    @Override
                    public void onErrorResponse(CruxClientError failureResponse) {
                        System.out.println(failureResponse);
                        responseViewPutAddressMap.setText(failureResponse.errorCode.toString() + failureResponse.errorMessage.toString());
                    }
                });
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
