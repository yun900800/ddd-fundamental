package org.ddd.fundamental.app.importtest;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ImportSelectorExample {

    @Test
    public void testImport() {
        System.setProperty("config", "MyConfig1");

        ApplicationContext context =
                new AnnotationConfigApplicationContext(
                        MainConfig.class);
        ClientBean bean = context.getBean(ClientBean.class);
        String result = bean.doSomething();
        Assert.assertEquals("from config 1", result);
    }

    @Test
    public void testImport1() {
        System.setProperty("config", "MyConfig2");

        ApplicationContext context =
                new AnnotationConfigApplicationContext(
                        MainConfig.class);
        ClientBean bean = context.getBean(ClientBean.class);
        String result = bean.doSomething();
        Assert.assertEquals("from config 2", result);
    }

    @Test
    public void testImport2() {
        System.setProperty("config", "MyConfig3");

        ApplicationContext context =
                new AnnotationConfigApplicationContext(
                        MainConfig.class);
        ClientBean bean = context.getBean(ClientBean.class);
        String result = bean.doSomething();
        Assert.assertEquals("from main config ", result);
    }
}
