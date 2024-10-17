package org.ddd.fundamental.generics.interfaces;

import java.util.Iterator;

public class FibonacciAdapter implements Iterable<Integer>{
    private Fibonacci fibonacci;
    private int size;

    public FibonacciAdapter(int size) {
        fibonacci = new Fibonacci();
        this.size = size;
    }

    public static void main(String[] args) {
        for (Integer i : new FibonacciAdapter(10)) {
            System.out.print(i + " ");
        }
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public Integer next() {
                index++;
                return fibonacci.next();
            }
        };
    }
}
