package org.ddd.fundamental.generics.why;

public class Holders2 {
    private Object o;
    public Holders2(Object o){
        this.o = o;
    }

    public void set(Object o){
        this.o = o;
    }
    public Object get(){
        return this.o;
    }
    public static void main(String[] args) {
        Holders2 h2 = new Holders2(new Automobile());
        Automobile a = (Automobile)h2.get();
        h2.set("Not an Automobile");
        String s = (String)h2.get();
        h2.set(1); // Autoboxes to Integer
        Integer x = (Integer)h2.get();
    }
}
