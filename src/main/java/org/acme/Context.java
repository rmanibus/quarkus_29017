package org.acme;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class Context {

    String test;

    public void set(String test){
        this.test = test;
    }
    public String get(){
        return test;
    }
}