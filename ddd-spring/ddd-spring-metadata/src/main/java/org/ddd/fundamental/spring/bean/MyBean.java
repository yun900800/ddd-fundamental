package org.ddd.fundamental.spring.bean;

import org.ddd.fundamental.spring.annotation.MyAnnotation;
import org.ddd.fundamental.spring.annotation.MyClassAnnotation;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@MyClassAnnotation
@Component("myBean")
public class MyBean extends MyAbstract implements Serializable {
    public String key;

    public String value;

    @MyAnnotation
    public static void myMethod1() {

    }

    @MyAnnotation
    public String myMethod2() {
        return "hello world";
    }

    public void myMethod3() {

    }

    public static class MyInnerClass {
        // 内部类的定义
    }
}
