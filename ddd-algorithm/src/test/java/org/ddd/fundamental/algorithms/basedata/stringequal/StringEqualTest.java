package org.ddd.fundamental.algorithms.basedata.stringequal;

import org.junit.Assert;
import org.junit.Test;

public class StringEqualTest {

    /**
     * == 比较分为两种情况 基本类型(值比较)和引用类型(地址比较)
     */
    @Test
    public void testDemo1() {
        String str1 = "abc";
        String str2 = "abc";
        Assert.assertTrue(str1 == str2);
        Assert.assertTrue(str1.equals(str2));
    }

    @Test
    public void testDemo3() {
        String str1 = new String("abc");
        String str2 = "abc";
        Assert.assertFalse(str1 == str2);
        Assert.assertTrue(str1.equals(str2));
    }

    @Test
    public void testDemo4() {
        String str1 = "a"+"b"+"c";
        String str2 = "abc";
        Assert.assertTrue(str1 == str2);
        Assert.assertTrue(str1.equals(str2));
    }

    @Test
    public void testDemo5() {
        String str1 = "ab";
        String str2 = "abc";
        String str3 = str1+ "c";
        String str4 = "ab"+"c";
        Assert.assertTrue(str2 == str4);
        Assert.assertFalse(str2 == str3);
        Assert.assertTrue(str2.equals(str3));
    }
}
