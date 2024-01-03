package org.ddd.fundamental.algorithm.basedata.decimal;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

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

    /**
     * 这个方法不常用,在一些需要不同的舍入模式的时候用到
     * @param a
     * @param b
     * @param scale
     * @param mode
     * @return
     */
    public static double div(Double a, Double b, int scale, RoundingMode mode) {
        if (scale < 0) {
            throw new IllegalArgumentException("精度不能为负值");
        }
        BigDecimal decimalA = new BigDecimal(Double.toString(a));
        BigDecimal decimalB = new BigDecimal(Double.toString(b));
        return decimalA.divide(decimalB, scale, mode).doubleValue();
    }


}
