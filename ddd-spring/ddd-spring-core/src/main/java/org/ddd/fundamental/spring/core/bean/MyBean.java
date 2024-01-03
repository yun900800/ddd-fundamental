package org.ddd.fundamental.spring.core.bean;

import org.springframework.beans.factory.DisposableBean;

public class MyBean implements DisposableBean {
    @Override
    public void destroy() throws Exception {
        System.out.println("MyBean被销毁了");
    }
}
