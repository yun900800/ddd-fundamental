package org.ddd.fundamental.generics.sequence;

public class Sequence<T> {
    private T[] elements;
    private int next = 0;

    public Sequence(int size){
        elements = (T[]) new Object[size];
    }

    public void add(T element){
        if (next < elements.length) {
            elements[next++] = element;
        }
    }

    private class SequenceSelector implements Selector<T>{

        private int index;

        @Override
        public boolean end() {
            return index == elements.length;
        }

        @Override
        public T current() {
            return elements[index];
        }

        @Override
        public void next() {
            if (index < elements.length)index++;
        }
    }

    public Selector<T> selector(){
        return new SequenceSelector();
    }

    public static void main(String[] args) {
        Sequence<String> sequence = new Sequence<>(10);
        for (int i = 0; i < 10; i++)
            sequence.add(Integer.toString(i));
        Selector selector = sequence.selector();
        while (!selector.end()) {
            System.out.print(selector.current() + " ");
            selector.next();
        }
    }

}
interface Selector<T> {
    boolean end();

    T current();

    void next();
}
