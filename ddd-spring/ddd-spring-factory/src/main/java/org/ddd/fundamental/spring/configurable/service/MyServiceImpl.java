package org.ddd.fundamental.spring.configurable.service;

public class MyServiceImpl implements MyService{
    @Override
    public void greet() {
        System.out.println("Hello from MyService!");
    }
}
