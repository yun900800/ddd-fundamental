package org.ddd.fundamental.algorithms.basedata.operation;

import org.ddd.fundamental.algorithm.basedata.operation.StackOperation;
import org.junit.Assert;
import org.junit.Test;

public class StackOperationTest {

    @Test
    public void testMidToLast() {
        String source = "!0&1|0";
        String result = StackOperation.midToLast(source);
        String expect = "0!1&0|";
        Assert.assertEquals(expect, result);

        source = "1&0|0&1";
        result = StackOperation.midToLast(source);
        expect = "10&01&|";
        Assert.assertEquals(expect, result);


    }

    @Test
    public void testWithBranket() {
        String source = "1|(1&0)";
        String result = StackOperation.midToLast(source);
        String expect = "110&|";
        Assert.assertEquals(expect, result);

        source = "((!0&1))|0";
        result = StackOperation.midToLast(source);
        expect = "0!1&0|";
        Assert.assertEquals(expect, result);
    }

    @Test
    public void testPriority() {
        String source = "1|1&1&!0";
        String result = StackOperation.midToLast(source);
        String expect = "111&0!&|";
        Assert.assertEquals(expect, result);
    }
}
