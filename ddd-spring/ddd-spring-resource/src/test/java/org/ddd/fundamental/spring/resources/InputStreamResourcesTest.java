package org.ddd.fundamental.spring.resources;

import org.junit.Assert;
import org.junit.Test;

public class InputStreamResourcesTest {
    @Test
    public void testInputStreamResourcesInput() {
        String path = "application.properties";
        try {
            String result = InputStreamResources.input(path);
            Assert.assertEquals(result,"application.properties");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
