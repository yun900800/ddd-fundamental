package org.ddd.fundamental.datasource.utils;

import org.ddd.fundamental.datasource.beans.MyBean;
import org.ddd.fundamental.datasource.core.DataSourceProvider;
import org.ddd.fundamental.datasource.core.enums.Database;
import org.ddd.fundamental.datasource.provider.HSQLDBDataSourceProvider;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;

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
        Field field = ReflectionUtils.getField(MyBean.class,"age");
        Assert.assertNull(field);
    }

}
