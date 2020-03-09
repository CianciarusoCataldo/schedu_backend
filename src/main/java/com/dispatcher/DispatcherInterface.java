package com.dispatcher;

import java.util.List;

public interface DispatcherInterface {

    public Object getResult(List<Object> task);

    public boolean addAccount(Account acc);
    public boolean removeAccount(Account acc);


	public List<Account> getAccounts();

}