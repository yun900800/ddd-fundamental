package org.ddd.fundamental.spring.beans.definitioon;

import org.ddd.fundamental.spring.bean.MyBean;
import org.ddd.fundamental.spring.beans.definition.BeanDefinitionsExample;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class BeanDefinitionsExampleTest {

    @Test
    public void testLoadBeans() throws IOException {
        MyBean myBean = (MyBean)BeanDefinitionsExample.loadBeans();
        Assert.assertEquals(myBean.getAge(),"18");
        Assert.assertEquals(myBean.getName(),"lex");
    }
}
