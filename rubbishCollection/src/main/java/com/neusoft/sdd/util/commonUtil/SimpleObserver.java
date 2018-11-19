package com.neusoft.sdd.util.commonUtil;

import java.util.Observable;
import java.util.Observer;

public class SimpleObserver implements Observer {

    private String name;

    public SimpleObserver(String name) {
        this.name = name;
    }

    public void update(Observable o, Object arg) {
        SimpleObservable observable = (SimpleObservable) o;
        System.out.println(name + " found that Data of " + observable.getName() + " has changed to " + observable.getData());
    }
}
