
CRUX Protocol Documentation & Guides - https://docs.cruxpay.com


# Quickstart

Official SDK Quickstart docs - https://docs.cruxpay.com/docs/quickstart

## 1. Install

```
    repositories {
          maven { url 'https://jitpack.io' }
    }

    dependencies {
        implementation 'com.github.cruxprotocol:android-sdk:Tag'
    }
```

## 2. Initialize

To initialise the SDK you need a **walletClientName**.

You can get create and manage your `walletClientName` at [CRUX Wallet Dashboard](https://cruxpay.com/wallet/dashboard). Please feel free to contact us at *contact@cruxpay.com* for any registration related queries.

```
    import com.crux.sdk.CruxClient;
    import com.crux.sdk.model.*;


    CruxClientInitConfig.Builder configBuilder = new CruxClientInitConfig.Builder()
        .setWalletClientName("testwallet")
        .setPrivateKey("6bd397dc89272e71165a0e7d197b288ed5b1e44e1928c25455506f1968f");


    CruxClient cruxClient = new CruxClient(configBuilder, androidContextObject);
```


## 3. Start Using!

```
    // Resolve any existing CRUX ID for a currency
    cruxClient.resolveCurrencyAddressForCruxID(
        "mascot6699@cruxdev.crux", "xrp",
        new CruxClientResponseHandler<CruxAddress>() {
            @Override
            public void onResponse(CruxAddress successResponse) {
                System.out.println(successResponse);
            }

            @Override
            public void onErrorResponse(CruxClientError failureResponse) {
                System.out.println(failureResponse);
            }
        }
    );

    // Create a new CRUX ID - mynewid@testwallet.crux
    cruxClient.registerCruxID("mynewid",
        new CruxClientResponseHandler<Void>() {
            @Override
            public void onResponse(Void successResponse) {
                System.out.println(successResponse);
                    // ID Will be owned by the injected private key
                    // New IDs take 6-8 confirmations in the Bitcoin network to confirm.
            }

            @Override
            public void onErrorResponse(CruxClientError failureResponse) {
            }
        }
    );




    // You can check the status of the ID by asking the SDK for the CruxID State
    cruxClient.getCruxIDState(
        new CruxClientResponseHandler<CruxIDState>() {
            @Override
            public void onResponse(CruxIDState successResponse) {
                System.out.println(successResponse);
            }

            @Override
            public void onErrorResponse(CruxClientError failureResponse) {
            }
        }
    );



    // A registered ID allows you to associate cryptocurrency addresses
    HashMap<String, CruxAddress> newAddressMap = getNewCurrencyMap();

    cruxClient.putAddressMap(newAddressMap,
        new CruxClientResponseHandler<CruxPutAddressMapSuccess>() {
            @Override
            public void onResponse(CruxPutAddressMapSuccess successResponse) {
                System.out.println(successResponse);
            }

            @Override
            public void onErrorResponse(CruxClientError failureResponse) {
            }
        }
    );
```


# SDK Integration Steps and User Interface:

[LINK](https://docs.cruxpay.com/docs/integration-dev-plan)

# SDK Reference

[LINK](#)