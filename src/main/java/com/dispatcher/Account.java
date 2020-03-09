package com.dispatcher;

public abstract class Account implements AccountInterface {

    private String username;
    private String password;

    public Account(String us, String pass) {
        this.username = us;
        this.password = pass;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Account)) {
            return false;
        }
        Account acc = (Account) obj;
        return username.equals(acc.getUsername()) && password.equals(acc.getPassword());
    }

    @Override
    public boolean isOnline() {
        return username.length() > 0 && password.length() > 0;
    }

    @Override
    public String toString() {
        return "\n  Username : " + username + "\n  Password : " + password + "\n";
    }
}