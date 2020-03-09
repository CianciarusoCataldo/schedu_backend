package com.json;

import java.util.List;

import com.dispatcher.Account;
import com.dispatcher.Dispatcher;
import com.sister.backend.SisterDispatcher;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonUtils {

    public static JSONArray getJson(List<Account> accs) {
        int id = 0;
        JSONArray array=new JSONArray();
        String result = "[";
        for (Account acc : accs) {
            JSONObject obj=new JSONObject();
            obj.put("ID", id);
            obj.put("Username", acc.getUsername());
            obj.put("Password", acc.getPassword());
            array.put(obj);
            result += "{\"ID\" : \"" + id + "\"," + "\"Username\" : \"" + acc.getUsername() + "\","
                    + "\"Password  \" : \"" + acc.getPassword() + "\"},";
            id++;
        }
        result = result.substring(0, result.length() - 1);
        result += "]";
        return array;
    }

    public static void main(String[] args) {
        Dispatcher d = new SisterDispatcher();
        System.out.println(JsonUtils.getJson(d.getAccounts()));
    }

    public static void addToJson(JSONObject jsonObj, Account acc) {

    }

    public static void addToJson(JSONObject jsonObj, List<Account> accs) {

    }
}