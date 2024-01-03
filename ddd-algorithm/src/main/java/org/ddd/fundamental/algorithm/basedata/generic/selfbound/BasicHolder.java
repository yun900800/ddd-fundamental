package org.ddd.fundamental.algorithm.basedata.generic.selfbound;

public class BasicHolder<T> {
    T element;
    public void setElement(T e) {
        this.element = e;
    }

    public T getElement() {
        return this.element;
    }
    public void printElement() {
        System.out.println(element.getClass().getSimpleName());
    }
}

