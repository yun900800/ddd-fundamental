package org.ddd.fundamental.algorithms.basedata.decimal;

import org.ddd.fundamental.algorithm.basedata.decimal.BigDecimalUtils;
import org.junit.Assert;
import org.junit.Test;

public class BigDecimalUtilsTest {

    @Test
    public void testBigDecimalAdd() {
        double a = 0.05;
        double b = 1.2698;
        Assert.assertEquals(1.3198, BigDecimalUtils.add(a,b),0.0001);
    }

    @Test
    public void testBigDecimalSubtract() {
        double a = 0.05;
        double b = 1.2698;
        Assert.assertEquals(-1.2198, BigDecimalUtils.subtract(a,b),0.0001);
    }

    @Test
    public void testBigDecimalMultiple() {
        double a = 0.05;
        double b = 1.2698;
        double actual = BigDecimalUtils.mul(a,b);
        Assert.assertEquals(0.06349, actual,0.00001);
    }

    @Test
    public void testBigDecimalDiv() {
        double a = 0.05;
        double b = 1.2698;
        double actual = BigDecimalUtils.div(a,b);
        Assert.assertEquals(0.039376, actual,0.000001);
        a = 2453648454542.0;
        b = 264.0;
        actual = BigDecimalUtils.div(a,b);
        Assert.assertEquals(9.29412E9, actual,0.00001);
    }
}
