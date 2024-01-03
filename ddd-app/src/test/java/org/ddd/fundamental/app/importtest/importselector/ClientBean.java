package org.ddd.fundamental.app.importtest.importselector;

import org.springframework.beans.factory.annotation.Autowired;

public class ClientBean {

    @Autowired
    private AppBean appBean;

    public String printMessage() {
        return appBean.getMessage();
    }
}
