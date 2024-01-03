package org.ddd.fundamental.algorithms.basedata.operation;

import org.ddd.fundamental.algorithm.basedata.operation.XOROperation;
import org.junit.Assert;
import org.junit.Test;

public class XOROperationTest {

    /**
     * x ^= y # =>                      (x ^ y, y)   op1 line18
     * y ^= x # => (x ^ y, y ^ x ^ y) = (x ^ y, x)   op2 line19
     * x ^= y # => (x ^ y ^ x, x)     = (y, x)       op3 line20
     * 每条指令后的注释显示 (x, y) 的当前值：
     */
    @Test
    public void testXorSwap() {
        int x = 6;
        int y = 8;
        x^=y;
        y^=x;
        x^=y;
        Assert.assertTrue(x==8);
        Assert.assertTrue(y==6);
    }

    @Test
    public void testIntToBinary() {
        String str = XOROperation.intToBinary(1023);
        Assert.assertEquals("1111111111", str);
    }
}
