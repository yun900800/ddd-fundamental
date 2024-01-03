package org.ddd.fundamental.spring.resources;

import org.junit.Assert;
import org.junit.Test;

public class ByteResourcesTest {

    @Test
    public void testByteResourcesInput() {
        String source = "how to find a well job";
        try {
            String result = ByteResources.input(source.getBytes());
            Assert.assertEquals(result,source);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
