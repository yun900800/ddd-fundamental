package org.ddd.fundamental.algorithms.basedata.stringequal;

import org.junit.Assert;
import org.junit.Test;

public class IntegerUtilsTest {
    @Test
    public void testIntegerEqual() {
        Integer a = 100, b = 100, c = 150, d =150;
        Assert.assertTrue(a == b);
        Assert.assertFalse(c == d);
    }
}
