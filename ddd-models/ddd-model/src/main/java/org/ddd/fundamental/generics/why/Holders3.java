package org.ddd.fundamental.generics.why;

public class Holders3<T> {
    private T a;
    public Holders3(T a) { this.a = a; }
    public void set(T a) { this.a = a; }
    public T get() { return a; }
    public static void main(String[] args) {
        Holders3<Automobile> h3 =
                new Holders3<Automobile>(new Automobile());
        Automobile a = h3.get(); // No cast needed
        // h3.set("Not an Automobile"); // Error
        // h3.set(1); // Error
    }
} //
