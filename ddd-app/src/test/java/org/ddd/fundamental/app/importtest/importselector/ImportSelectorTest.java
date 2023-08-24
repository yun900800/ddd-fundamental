package org.ddd.fundamental.app.importtest.importselector;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ImportSelectorTest {

    @Test
    public void testImportSelectorDefault() {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(
                        MainConfig.class);
        ClientBean bean = context.getBean(ClientBean.class);
        String result = bean.printMessage();
        Assert.assertEquals("default message", result);
    }

    @Test
    public void testImportSelectorFirst() {
        System.setProperty("config","FirstConfig");
        ApplicationContext context =
                new AnnotationConfigApplicationContext(
                        MainConfig1.class);
        ClientBean bean = context.getBean(ClientBean.class);
        String result = bean.printMessage();
        Assert.assertEquals("FirstConfig", result);
    }
    @Test
    public void testImportSelectorSecond() {
        System.setProperty("config","SecondConfig");
        ApplicationContext context =
                new AnnotationConfigApplicationContext(
                        MainConfig1.class);
        ClientBean bean = context.getBean(ClientBean.class);
        String result = bean.printMessage();
        Assert.assertEquals("SecondConfig", result);
    }
    @Test
    public void testImportSelectorSimple() {
        System.setProperty("config","");
        ApplicationContext context =
                new AnnotationConfigApplicationContext(
                        MainConfig1.class);
        ClientBean bean = context.getBean(ClientBean.class);
        String result = bean.printMessage();
        Assert.assertEquals("default message", result);
    }
}
