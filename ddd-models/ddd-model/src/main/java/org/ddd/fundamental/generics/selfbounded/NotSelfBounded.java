package org.ddd.fundamental.generics.selfbounded;

public class NotSelfBounded<T> {
    T element;
    NotSelfBounded<T> set(T arg) {
        element = arg;
        return this;
    }
    T get() { return element; }

    public static void main(String[] args) {
        A2 a = new A2();
        a.set(new A2());
        a = a.set(new A2()).get();
        a = a.get();
        C2 c = new C2();
        c = c.setAndGet(new C2());
    }
}
class A2 extends NotSelfBounded<A2> {}
class B2 extends NotSelfBounded<A2> {}
class C2 extends NotSelfBounded<C2> {
    C2 setAndGet(C2 arg) { set(arg); return get(); }
}
class D2 {}
// Now this is OK:
class E2 extends NotSelfBounded<D2> {}
