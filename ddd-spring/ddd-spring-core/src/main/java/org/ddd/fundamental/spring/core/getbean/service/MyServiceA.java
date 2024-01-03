package org.ddd.fundamental.spring.core.getbean.service;

import org.springframework.stereotype.Component;

@Component
public class MyServiceA {
    public void destroy(){
        System.out.println("MyServiceA.destroy");
    }
}
