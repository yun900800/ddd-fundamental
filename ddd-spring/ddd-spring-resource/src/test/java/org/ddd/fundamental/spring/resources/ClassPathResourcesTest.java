package org.ddd.fundamental.spring.resources;

import org.junit.Assert;
import org.junit.Test;

public class ClassPathResourcesTest {

    @Test
    public void testClassPathResourcesInput() {
        String path = "application.properties";
        try {
            String result = ClassPathResources.input(path);
            Assert.assertEquals(result,"profile=test");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
