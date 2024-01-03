package org.ddd.fundamental.conditional.beanpostprocessor.metadatareader;

public class MyBean extends MyAbstract{
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
