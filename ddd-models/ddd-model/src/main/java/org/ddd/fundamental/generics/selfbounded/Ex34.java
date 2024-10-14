package org.ddd.fundamental.generics.selfbounded;

abstract class SelfBoundedType<T extends SelfBoundedType<T>> {
    abstract T f(T arg);
    T g(T arg) {
        System.out.println("g(T arg)");
        return f(arg);
    }
}

class D1 extends SelfBoundedType<D1> {
    D1 f(D1 arg) {
        System.out.println("f(D arg)");
        return arg;
    }
}


public class Ex34 {
    public static void main(String[] args) {
        D1 d = new D1();
        d.f(d).g(d);
    }
}
