package org.ddd.fundamental.datasource.utils;

import org.junit.Test;

public class ParameterizedTypeTest {
    class Girl implements Person {}
    class Boy implements Person {}
    interface Person {}
    class School<A extends Boy & Person> {}
    School<Boy> boySchool;
    School<Boy>[] schools;
    Boy boy;
    @Test
    public void testParameterizedType() {
        // class java.lang.Class
        System.out.println(ReflectionUtils.getFieldOrNull(ParameterizedTypeTest.class, "boy").getGenericType().getClass());
        // class sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl
        System.out.println(ReflectionUtils.getFieldOrNull(ParameterizedTypeTest.class, "boySchool").getGenericType().getClass());
        // class sun.reflect.generics.reflectiveObjects.GenericArrayTypeImpl
        System.out.println(ReflectionUtils.getFieldOrNull(ParameterizedTypeTest.class, "schools").getGenericType().getClass());
    }
}
