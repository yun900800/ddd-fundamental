package org.ddd.fundamental.datasource.utils;

import org.checkerframework.checker.units.qual.C;
import org.ddd.fundamental.datasource.beans.MyBean;
import org.ddd.fundamental.datasource.beans.MyChildBean;
import org.ddd.fundamental.datasource.core.DataSourceProvider;
import org.ddd.fundamental.datasource.core.enums.Database;
import org.ddd.fundamental.datasource.provider.HSQLDBDataSourceProvider;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionUtilsTest {

    @Test
    public void testNewInstanceNoArg() {
        DataSourceProvider dataSourceProvider = ReflectionUtils.newInstance(HSQLDBDataSourceProvider.class);
        Assert.assertEquals(dataSourceProvider.database(), Database.HSQLDB);
    }

    @Test
    public void testNewInstanceWithArg(){
        MyBean myBean = ReflectionUtils.newInstance(MyBean.class, new String[]{"hello"}, new Class[]{String.class});
        Assert.assertEquals(myBean.getName(),"hello");
    }

    @Test
    public void testGetField() throws IllegalAccessException {
        Field field = ReflectionUtils.getField(MyBean.class,"name");
        field.setAccessible(true);
        MyBean myBean = new MyBean("nice");
        Assert.assertEquals(field.get(myBean),"nice");
        Assert.assertEquals(field.getName(),"name");
    }

    @Test
    public void testGetFieldOrNull(){
        Field field = ReflectionUtils.getField(MyBean.class,"age1");
        Assert.assertNull(field);
    }

    @Test
    public void testGetFieldValue() {
        MyBean myBean = new MyBean("nice");
        String value = ReflectionUtils.getFieldValue(myBean,"name");
        Assert.assertEquals("nice",value);
    }

    @Test
    public void testGetFieldValueOrNull() {
        MyBean myBean = new MyBean("null");
        String value = ReflectionUtils.getFieldValueOrNull(myBean,"age");
        Assert.assertNull(value);
    }

    @Test
    public void testSetFieldValue() {
        MyBean myBean = new MyBean(null);
        ReflectionUtils.setFieldValue(myBean,"name","world");
        Assert.assertEquals("world",myBean.getName());
    }

    @Test
    public void testGetMethod() throws InvocationTargetException, IllegalAccessException {
        Method initAge = ReflectionUtils.getMethod(MyBean.class,"initAge",new Class[]{});
        MyBean myBean = new MyBean(null);
        initAge.invoke(myBean,null);
        Assert.assertEquals(myBean.getAge(),20,0);

        MyBean myBean1 = new MyBean(null);
        Method initAge1 = ReflectionUtils.getMethod(myBean1,"initAge",new Class[]{});
        initAge1.invoke(myBean1,null);
        Assert.assertEquals(myBean1.getAge(),20,0);
    }

    @Test
    public void testGetMethodOrNull() {
        Method initAge = ReflectionUtils.getMethodOrNull(MyBean.class,"initAge1",new Class[]{});
        Assert.assertNull(initAge);
        MyBean myBean = new MyBean(null);
        Method initAge1 = ReflectionUtils.getMethodOrNull(myBean,"initAge1",new Class[]{});
        Assert.assertNull(initAge1);
    }


    /**
     *
     * getMethod（）：获取自身能用所有的public公共方法。1.类本身的public 2.继承父类的public 3.实现接口的public
     *
     * getDeclaredMethod（）：获取类自身声明的所有方法,包含public、protected和private方法。。
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Test
    public void testGetDeclaredMethodOrNull() throws InvocationTargetException, IllegalAccessException {
        Method initAge = ReflectionUtils.getDeclaredMethodOrNull(MyChildBean.class,
                "initAge", new Class[]{});
        Method printInfo = ReflectionUtils.getDeclaredMethodOrNull(MyChildBean.class,
                "printInfo", new Class[]{});
        MyChildBean myChildBean = new MyChildBean(null);
        initAge.invoke(myChildBean,null);
        Assert.assertEquals(myChildBean.getAge(),20,0);
        printInfo.setAccessible(true);
        String result = (String)printInfo.invoke(myChildBean,null);
        Assert.assertEquals(result,"name:null;age:20");
    }

}
