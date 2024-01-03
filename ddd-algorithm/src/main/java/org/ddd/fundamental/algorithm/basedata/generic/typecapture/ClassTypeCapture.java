package org.ddd.fundamental.algorithm.basedata.generic.typecapture;

public class ClassTypeCapture<T> {

    Class<T> clazz;

    public ClassTypeCapture(Class<T> clazz) {
        this.clazz = clazz;
    }

    public boolean typeCheck(Object arg) {
        return clazz.isInstance(arg);
    }
}
