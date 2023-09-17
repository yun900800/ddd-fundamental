package org.ddd.fundamental.algorithms.basedata.stringequal;

import org.junit.Assert;
import org.junit.Test;

public class StringBuilderTest {

    @Test
    public void testInsert() {
        StringBuilder sb = new StringBuilder();
        sb.append("hello_world");
        sb.insert(5,"www");
        Assert.assertEquals("hellowww_world",sb.toString());
    }
}
