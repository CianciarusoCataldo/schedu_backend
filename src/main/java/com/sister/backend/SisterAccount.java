package com.sister.backend;

import com.dispatcher.Account;

import org.json.JSONObject;

public class SisterAccount extends Account {

    public SisterAccount(String usr, String psw) {
        super(usr, psw);
    }

    public JSONObject getUserData(Object cf) {
        
        //TODO: Gestire connessione con server SISTer
        throw new RuntimeException("Not Implemented");

    }

}
