package org.ddd.fundamental.generics.selfbounded;


class SelfBounded<T extends SelfBounded<T>> {
    T element;
    SelfBounded<T> set(T arg) {
        element = arg;
        return this;
    }
    T get() { return element; }
}
class A extends SelfBounded<A> {}

class B extends SelfBounded<A> {}

class C extends SelfBounded<C> {
    C setAndGet(C arg) { set(arg); return get(); }
}

class D {}
// Can’t do this:
// class E extends SelfBounded<D> {}
// Compile error: Type parameter D is not within its bound

class F extends SelfBounded {}
public class SelfBounding {
    public static void main(String[] args) {
        A a = new A();
        a.set(new A());
        a = a.set(new A()).get();
        a = a.get();
        C c = new C();
        c = c.setAndGet(new C());
    }
}
