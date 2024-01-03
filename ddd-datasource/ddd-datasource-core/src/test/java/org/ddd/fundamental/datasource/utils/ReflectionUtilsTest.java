package org.ddd.fundamental.datasource.utils;

import org.ddd.fundamental.datasource.beans.MyBean;
import org.ddd.fundamental.datasource.beans.MyChildBean;
import org.ddd.fundamental.datasource.core.DataSourceProvider;
import org.ddd.fundamental.datasource.core.enums.Database;
import org.ddd.fundamental.datasource.provider.HSQLDBDataSourceProvider;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.*;

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

    @Test
    public void testHasMethod() {
        boolean result = ReflectionUtils.hasMethod(MyChildBean.class,
                "changeAge", new Class[]{Integer.class});
        Assert.assertTrue(result);
    }

    @Test
    public void testGetSetter() throws InvocationTargetException, IllegalAccessException {
        MyBean myBean = new MyBean("this is setter test");
        Assert.assertEquals("this is setter test",myBean.getName());
        Method setterName = ReflectionUtils.getSetter(myBean,"name",String.class);
        setterName.invoke(myBean,"this is setter test changed");
        Assert.assertEquals("this is setter test changed",myBean.getName());
    }

    @Test
    public void testGetSetterOrNull() throws InvocationTargetException, IllegalAccessException {
        MyBean myBean = new MyBean("this is setter test");
        Assert.assertEquals("this is setter test",myBean.getName());
        Method setterName = ReflectionUtils.getSetterOrNull(myBean,"name",String.class);
        setterName.invoke(myBean,"this is setter test changed");
        Assert.assertEquals("this is setter test changed",myBean.getName());
        Method setterAge = ReflectionUtils.getSetterOrNull(myBean,"age",String.class);
        Assert.assertNull(setterAge);
    }

    @Test
    public void testGetGetter() throws InvocationTargetException, IllegalAccessException {
        MyBean myBean = new MyBean("this is setter test");
        Assert.assertEquals("this is setter test",myBean.getName());
        Method getterName = ReflectionUtils.getGetter(myBean,"name");
        String result = (String)getterName.invoke(myBean, null);
        Assert.assertEquals(result,"this is setter test");
    }

    @Test
    public void testInvokeMethod() {
        MyBean myBean = new MyBean("this is a invoking a method test");
        Assert.assertEquals("this is a invoking a method test",myBean.getName());
        Method changeAge = ReflectionUtils.getDeclaredMethodOrNull(MyBean.class,
                "changeAge", new Class[]{Integer.class});
        ReflectionUtils.invokeMethod(myBean,changeAge,
                50);
        Assert.assertEquals(50,myBean.getAge(),0);


        ReflectionUtils.invokeMethod(myBean,"changeAge",60);
        Assert.assertEquals(60,myBean.getAge(),0);

        //invokeGetter
        String name = ReflectionUtils.invokeGetter(myBean,"name");
        Assert.assertEquals("this is a invoking a method test",name);

        //invokeSetter
        ReflectionUtils.invokeSetter(myBean,"name",
                "this is another invoking a method test");
        Assert.assertEquals("this is another invoking a method test",myBean.getName());

        //invokeStaticMethod
        Method staticMethod = ReflectionUtils.getMethod(MyBean.class,"printClassName",new Class[]{});
        String result = ReflectionUtils.invokeStaticMethod(staticMethod,null);
        Assert.assertEquals(result,"org.ddd.fundamental.datasource.beans.MyBean");
    }

    @Test
    public void testGetClass() {
        Class<MyBean> clazz = ReflectionUtils.getClass("org.ddd.fundamental.datasource.beans.MyBean");
        Assert.assertEquals(MyBean.class,clazz);

        clazz = ReflectionUtils.getClassOrNull("MyBean");
        Assert.assertNull(clazz);
    }

    @Test
    public void testGetWrapperClass() {
        Class<?> clazz = ReflectionUtils.getWrapperClass(Integer.TYPE);
        Assert.assertEquals(Integer.class,clazz);
    }

    @Test
    public void testGetFirstSuperClassFromPackage() {
        Class<?> clazz = ReflectionUtils.getFirstSuperClassFromPackage(MyChildBean.class,
                "org.ddd.fundamental.datasource.beans");
        Assert.assertEquals(MyChildBean.class,clazz);

        //getClassPackageName
        String packageName = ReflectionUtils.getClassPackageName("org.ddd.fundamental.datasource.beans.MyBean");
        Assert.assertEquals("org.ddd.fundamental.datasource.beans",packageName);
    }

    @Test
    public void testGetMemberOrNull() {
        //MyBean myBean = new MyBean("this is a invoking a method test");
        Member member = ReflectionUtils.getMemberOrNull(MyBean.class,"name");
        Assert.assertNotNull(member);

        Type type = ReflectionUtils.getMemberGenericTypeOrNull(MyBean.class,"name");
        Assert.assertEquals(String.class,type);

        member = ReflectionUtils.getMemberOrNull(MyBean.class,"nickName");
        Assert.assertNull(member);
    }

}
