package com.sister.backend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.dispatcher.Account;

import org.json.JSONArray;
import org.json.JSONObject;

public class SisterMockAccount extends Account {

    public SisterMockAccount(String usr, String psw) {
        super(usr, psw);
    }

    public JSONObject getUserData(Object cf) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        String json = "";
        BufferedReader read;
        StringBuilder b=new StringBuilder();
        try {
            read = new BufferedReader(new FileReader(new File("D:\\response.json")));
            while (json != null) {
                b.append(json);
                json = read.readLine();
            }
        
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject obj=new JSONObject(b.toString());
        JSONObject element=new JSONObject();
        element.put("key", cf);
        element.put("immobili", obj.getJSONObject("payload").getJSONArray("lista").getJSONObject(0).getJSONArray("immobili"));
        element.put("info", obj.getJSONObject("payload").getJSONArray("lista").getJSONObject(0).getJSONArray("anagrafica"));
        JSONArray imm =  obj.getJSONObject("payload").getJSONArray("lista").getJSONObject(0).getJSONArray("immobili");
        
        for (int i = 0; i < imm.length(); i++) {
            imm.getJSONObject(i).remove("altriDati");    
        }
        return element;

    }

}
