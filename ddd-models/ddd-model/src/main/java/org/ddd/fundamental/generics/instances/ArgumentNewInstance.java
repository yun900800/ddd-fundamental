package org.ddd.fundamental.generics.instances;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


public class ArgumentNewInstance {

    public static void main(String[] args) {
        A a = (A) createNew(A.class, "hello, world");
        System.out.println(a);
    }

    private static Object createNew(Class<?> clazz,Object... args){
        Class<?>[] classes = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            classes[i] = args[i].getClass();
        }
        try {
            Constructor<?> constructor = clazz.getDeclaredConstructor(classes);
            return constructor.newInstance(args);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }
}

class A {
    private String string;

    public A(String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return string;
    }
}
