package com.crux.sdk;

import com.crux.sdk.model.CruxAddress;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import java.lang.reflect.Type;
import java.util.HashMap;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addressMapfromJsonStringTest() {
        String m = "{\"trx\": {\"addressHash\": \"TG3iFaVvUs34SGpWq8RG9gnagDLTe1jdyz\"}, \"eth\": {\"addressHash\": \"0x0a2311594059b468c9897338b027c8782398b481\"}, \"xrp\": {\"addressHash\": \"rpfKAA2Ezqoq5wWo3XENdLYdZ8YGziz48h\", \"secIdentifier\": \"12345\"}, \"btc\": {\"addressHash\": \"1HX4KvtPdg9QUYwQE1kNqTAjmNaDG7w82V\"}}";
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<String, CruxAddress>>(){}.getType();
        HashMap currency = gson.fromJson(m, type);
        System.out.println(currency);
    }

    @Test
    public void undefinedToVoidTest() {
        String m = null;
        Gson gson = new Gson();
        Void currency = gson.fromJson(m, Void.class);
        System.out.println(currency);
    }

    @Test
    public void trueToBooleanTest() {
        String m = "true";
        Gson gson = new Gson();
        Boolean currency = gson.fromJson(m, Boolean.class);
        System.out.println(currency);
    }

    @Test
    public void falseToBooleanTest() {
        String m = "false";
        Gson gson = new Gson();
        Boolean currency = gson.fromJson(m, Boolean.class);
        System.out.println(currency);
    }
}