package com.dispatcher;

import java.util.ArrayList;

public abstract class Dispatcher implements DispatcherInterface {

    protected ArrayList<Object> queue = new ArrayList<>();

    @Override
    public String toString() {
        return "\nQueue : " + queue.toString() + "\n";
    }

}