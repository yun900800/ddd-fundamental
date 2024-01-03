package org.ddd.fundamental.spring.resources;

import org.junit.Assert;
import org.junit.Test;

public class UrlResourcesTest {
    @Test
    public void testUrlResourcesInput() {
        String path = "";
        try {
            String result = UrlResources.input(path);
            Assert.assertNotNull(result);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
