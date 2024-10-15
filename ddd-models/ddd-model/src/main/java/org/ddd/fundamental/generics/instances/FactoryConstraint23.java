package org.ddd.fundamental.generics.instances;

interface FactoryI1<T> {
    T create(Integer i);
}

class Foo21<T> {
    private T x;
    public void get() { System.out.println(x); }
    public <F extends FactoryI1<T>> Foo21(F factory, Integer i) {
        x = factory.create(i);
    }
}

class IntegerFactory1 implements FactoryI1<Integer> {
    public Integer create(Integer i) {
        return Integer.valueOf(i);
    }
}

class Widget1 {
    int i;
    public static class Factory implements FactoryI1<Widget1> {
        public Widget1 create(Integer i) {
            Widget1 w = new Widget1();
            w.i = i;
            return w;
        }
    }
    public String toString() {
        return "Widget " + i;
    }
}

public class FactoryConstraint23 {
    public static void main(String[] args) {
        Foo21 f1 = new Foo21<Integer>(new IntegerFactory1(), 1);
        Foo21 f2 = new Foo21<Widget1>(new Widget1.Factory(), 2);
        f1.get();
        f2.get();
    }
}
