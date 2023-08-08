package org.ddd.fundamental.app.importtest;

import org.springframework.beans.factory.annotation.Autowired;

public class ClientBean {
    @Autowired
    private AppBean appBean;

    public void doSomething () {
        System.out.println(appBean.getMessage());
    }

}
