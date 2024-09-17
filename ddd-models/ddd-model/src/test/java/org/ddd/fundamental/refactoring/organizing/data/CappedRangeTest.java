package org.ddd.fundamental.refactoring.organizing.data;

import org.junit.Assert;
import org.junit.Test;

public class CappedRangeTest {

    @Test
    public void testIncludes(){
        CappedRange intRange = new CappedRange(1,12,4);
        Assert.assertFalse(intRange.includes(5));
        Assert.assertFalse(intRange.includes(15));
    }

    @Test
    public void testGrow(){
        CappedRange intRange = new CappedRange(1,12,14);
        intRange.grow(2);
        Assert.assertTrue(intRange.includes(5));
        Assert.assertFalse(intRange.includes(25));
    }
}
