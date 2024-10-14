package org.ddd.fundamental.generics.casting;

import java.util.ArrayList;
import java.util.List;

class VariableSizeStack<T> {
    private ArrayList<T> storage = new ArrayList<T>();
    public int count() {
        return storage.size();
    }
    public void push(T item) {
        storage.add(item);
    }
    public T pop() {
        return storage.remove(storage.size() - 1);
    }
}

public class GenericCast33 {
    public static void main(String[] args) {
        VariableSizeStack<String> strings =
                new VariableSizeStack<String>();
        for(String s : "A B C D E F G H I J".split(" "))
            strings.push(s);
        int currentCount = strings.count();
        for(int i = 0; i < currentCount; i++) {
            String s = strings.pop();
            System.out.print(s + " ");
        }
    }
}

//class UseList<W,T> {
//    void f(List<T> v) {}
//    void f(List<W> v) {}
//}

class UseList2<W,T> {
    void f1(List<T> v) {}
    void f2(List<W> v) {}
}