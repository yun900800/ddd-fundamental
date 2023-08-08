package org.ddd.fundamental.app.importtest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ImportSelectorExample {
    public static void main (String[] args) {
        System.setProperty("myProp", "someValue");

        ApplicationContext context =
                new AnnotationConfigApplicationContext(
                        MainConfig.class);
        ClientBean bean = context.getBean(ClientBean.class);
        bean.doSomething();
    }
}
