package com.example.cruxpay;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button AvailabilityCheckButton;
    Button RegisterButton;
    Button ResolveButton;
    Button GetAddressMapButton;
    Button PutAddressMapButton;
    EditText mEdit;
    TextView mText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AvailabilityCheckButton = (Button)findViewById(R.id.button_availability_check);
        RegisterButton = (Button)findViewById(R.id.button_register_id);
        ResolveButton = (Button)findViewById(R.id.button_resolve_id);
        GetAddressMapButton = (Button)findViewById(R.id.button_getaddressmap);
        PutAddressMapButton = (Button)findViewById(R.id.button_putaddressmap);

        // Demo Wallet artifacts

        String walletClientName = "cruxdev";
        String encryptionKey = "fookey";
        String wallet_btc_address = "1HX4KvtPdg9QUYwQE1kNqTAjmNaDG7w82V";
        String wallet_eth_address = "0x0a2311594059b468c9897338b027c8782398b481";
        String wallet_trx_address = "TG3iFaVvUs34SGpWq8RG9gnagDLTe1jdyz";
        String wallet_xrp_address = "rpfKAA2Ezqoq5wWo3XENdLYdZ8YGziz48h";
        String wallet_xrp_sec_identifier = "12345";

        //    const sampleAddressMap: IAddressMapping = {
//        bitcoin: {
//            addressHash: wallet_btc_address
//        },
//        ethereum: {
//            addressHash: wallet_eth_address
//        },
//        tron: {
//            addressHash: wallet_trx_address
//        },
//        ripple: {
//            addressHash: wallet_xrp_address,
//                    secIdentifier: wallet_xrp_sec_identifier
//        }
//    };

        System.out.println("====== STARTED  ========");

    }

}
