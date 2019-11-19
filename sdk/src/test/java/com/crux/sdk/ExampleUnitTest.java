package com.crux.sdk;

import com.crux.sdk.model.CruxAddressMapping;
import com.google.gson.Gson;

import org.junit.Test;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void CruxAddressMappingfromJsonString1() {
        String m = "{\"trx\": {\"addressHash\": \"TG3iFaVvUs34SGpWq8RG9gnagDLTe1jdyz\"}, \"eth\": {\"addressHash\": \"0x0a2311594059b468c9897338b027c8782398b481\"}, \"xrp\": {\"addressHash\": \"rpfKAA2Ezqoq5wWo3XENdLYdZ8YGziz48h\", \"secIdentifier\": \"12345\"}, \"btc\": {\"addressHash\": \"1HX4KvtPdg9QUYwQE1kNqTAjmNaDG7w82V\"}}";
        Gson gson = new Gson();
        CruxAddressMapping currency = gson.fromJson(m, CruxAddressMapping.class);
        System.out.println(currency);
    }
}