package com.dispatcher;

public interface AccountInterface{

    public boolean isOnline();
    public Object getUserData(Object id);
    public String getUsername();
    public String getPassword();
}