package org.ddd.fundamental.generics.bounds;

public class BoundMethodTest {
    public static void main(String[] args) {
        Bound bound = new Bound();
        f(bound);
        g(bound);
    }

    static <T extends Bound1> void f(T t) {

    }

    static <T extends Bound2> void g(T t) {

    }
}

interface Bound1 {

}

interface Bound2 {

}

class Bound implements Bound1, Bound2 {

}
