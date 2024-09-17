package org.ddd.fundamental.refactoring.organizing.data;

import org.junit.Assert;
import org.junit.Test;

public class IntRangeTest {


    @Test
    public void testIncludes(){
        IntRange intRange = new IntRange(1,12);
        Assert.assertTrue(intRange.includes(5));
        Assert.assertFalse(intRange.includes(15));
    }

    @Test
    public void testGrow(){
        IntRange intRange = new IntRange(1,12);
        intRange.grow(2);
        Assert.assertTrue(intRange.includes(5));
        Assert.assertTrue(intRange.includes(15));
    }
}
