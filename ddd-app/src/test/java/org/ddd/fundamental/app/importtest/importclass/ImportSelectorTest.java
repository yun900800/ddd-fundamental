package org.ddd.fundamental.app.importtest.importclass;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ImportSelectorTest {

    @Test
    public void testImportClass() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ConfigA.class);
        ServiceInterface bean = ctx.getBean(ServiceInterface.class);
        String result = bean.test();
        Assert.assertEquals("ServiceB",result);
    }

    @Test(expected = NoUniqueBeanDefinitionException.class)
    public void testImportNoConditionClass() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ConfigC.class);
        ServiceInterface bean = ctx.getBean(ServiceInterface.class);
        String result = bean.test();
        Assert.assertEquals("ServiceA",result);
    }

    @Test
    public void testImportHasConditionClass() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ConfigE.class);
        ServiceInterface bean = ctx.getBean(ServiceInterface.class);
        String result = bean.test();
        Assert.assertEquals("ServiceB",result);
    }
}
