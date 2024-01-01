package org.ddd.fundamental.spring.beans.definitioonholder;

import org.ddd.fundamental.spring.beans.definitionholder.BeanDefinitionHolderExample;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;

public class BeanDefinitionHolderExampleTest {

    @Test
    public void testRegisterBeanDefinitionHolder() {
        BeanFactory factory = (BeanFactory)BeanDefinitionHolderExample.registerBeanDefinitionHolder();
        Assert.assertEquals(factory.getBean("myBean"),factory.getBean("myBeanX"));
        Assert.assertEquals(factory.getBean("myBeanY"),factory.getBean("myBeanX"));
    }
}
