package org.ddd.fundamental.algorithm.basedata.operation;

import java.lang.reflect.Array;

public class Stack<T> {

    private T[] arrays;

    private int capacity;

    private int index = -1;

    private int size = 0;

    public Stack(int capacity, Class<T> clazz) {
        this.capacity = capacity;
        this.arrays = (T[]) Array.newInstance(clazz, capacity);
    }

    public void push(T data) {
        if (this.isFull()) {
            System.out.println("栈已经满啦");
            return;
        }
        this.arrays[++index] = data;
        this.size++;
    }

    public T peek() {
        if (isEmpty()) {
          return null;
        }
        return this.arrays[index];
    }

    public T pop() {
        if (this.isEmpty()) {
            System.out.println("栈没有元素啦");
            return null;
        }
        this.size--;
        T temp = this.arrays[index--];
        this.arrays[index+1] = null;
        return temp;
    }

    public  boolean isEmpty() {
        return this.size == 0;
    }

    public boolean isFull() {
        return this.size == capacity;
    }

    public T[] getArrays() {
        return this.arrays;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getIndex() {
        return index;
    }

    public int getSize() {
        return size;
    }
}
