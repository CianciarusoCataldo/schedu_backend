package com.sister.backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Semaphore;

import com.database.DBUtils;
import com.dispatcher.Account;
import com.dispatcher.Dispatcher;

import org.json.JSONArray;

public class SisterDispatcher extends Dispatcher {

    private ArrayList<Account> resources = new ArrayList<>();
    private CopyOnWriteArrayList<Integer> freeResources = new CopyOnWriteArrayList<>();
    private ArrayList<Thread> threads = new ArrayList<>();
    private Semaphore mutex = new Semaphore(1);

    public SisterDispatcher() {
        DBUtils.getAccounts(resources);

        for (int i = 0; i < resources.size(); i++) {
            System.out.format("%s, %s\n", resources.get(i).getUsername(), resources.get(i).getPassword());
            freeResources.add(i);
        }
    }

    @Override
    public JSONArray getResult(List<Object> cf) {
        try {
            mutex.acquire();
        } catch (InterruptedException e1) {
            System.out.println(e1.getStackTrace());
            return new JSONArray();
        }

        threads.clear();
        long time = System.nanoTime();
        queue.addAll(cf);
        JSONArray array = new JSONArray();
        System.out.println("QUEUE : " + queue.toString());
        while (queue.size() > 0) {
            if (freeResources.size() > 0) {
                Object codf = queue.remove(0);
                int index = freeResources.remove(0);
                Thread t = new Thread(() -> {
                    System.out.println("Processing " + codf + " with resource " + index);
                    Object tempRes = resources.get(index).getUserData(codf);
                    System.out.println(codf + " processed by resource " + index);
                    array.put(tempRes);
                    freeResources.add(index);
                });

                threads.add(t);
                t.start();
            } else {
                continue;
            }
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Elapsed time :" + ((System.nanoTime() - time) / 1000000000) + " seconds");
        mutex.release();
        return array;

    }

    @Override
    public boolean addAccount(Account acc) {

        if (acc.isOnline()) {

            if (DBUtils.insert(acc)) {

                resources.add((SisterMockAccount) acc);

                freeResources.add(resources.size());
                
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean removeAccount(Account acc) {

        if (DBUtils.delete(acc)) {
            resources.clear();
            DBUtils.getAccounts(resources);
            return true;
        }

        return false;
    }

    public boolean removeAccount(int i) {
        if (DBUtils.delete(resources.get(i))) {
            freeResources.remove(freeResources.size() - 1);
            resources.remove(i);
            return true;
        }

        return false;
    }

    public List<Account> getAccounts() {
        try {
            mutex.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        ArrayList<Account> response = new ArrayList<>();
        response.addAll(resources);
        Collections.sort(response, new Comparator<Account>() {

            @Override
            public int compare(Account o1, Account o2) {
                return o1.getUsername().compareTo(o2.getUsername());
            }

        });
        mutex.release();
        return response;
    }

}
