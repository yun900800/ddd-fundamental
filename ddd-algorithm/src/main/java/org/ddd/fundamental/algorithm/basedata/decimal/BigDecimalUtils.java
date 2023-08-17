package org.ddd.fundamental.algorithm.basedata.decimal;

import java.math.BigDecimal;
import java.math.MathContext;

public class BigDecimalUtils {

    private static final int DEF_DIV_SCALE = 6;

    public static double add(Double a, Double b) {
        BigDecimal decimalA = new BigDecimal(Double.toString(a));
        BigDecimal decimalB = new BigDecimal(Double.toString(b));
        return decimalA.add(decimalB).doubleValue();
    }

    public static  double subtract(Double a, Double b) {
        BigDecimal decimalA = new BigDecimal(Double.toString(a));
        BigDecimal decimalB = new BigDecimal(Double.toString(b));
        return decimalA.subtract(decimalB).doubleValue();
    }

    public static double mul(Double a, Double b) {
        BigDecimal decimalA = new BigDecimal(Double.toString(a));
        BigDecimal decimalB = new BigDecimal(Double.toString(b));
        return decimalA.multiply(decimalB).doubleValue();
    }

    public static double div(Double a, Double b) {
        return div(a,b,DEF_DIV_SCALE);
    }

    /**
     * BigDecimal divide的几种情况：https://www.geeksforgeeks.org/bigdecimal-divide-method-in-java-with-examples/
     * 这里需要注意参数的意义;最后结果的精度和舍入模式
     * @param a
     * @param b
     * @param scale
     * @return
     *
     */
    public static double div(Double a, Double b, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("精度不能为负值");
        }
        BigDecimal decimalA = new BigDecimal(Double.toString(a));
        BigDecimal decimalB = new BigDecimal(Double.toString(b));
        MathContext mc = new MathContext(scale);
        return decimalA.divide(decimalB, mc).doubleValue();
    }


}
