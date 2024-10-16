package org.ddd.fundamental.core.generator;

import java.util.*;

public class Generators {
    public static <T> Collection<T> fill(Collection<T> coll, Generator<T> gen, int n){
        for(int i = 0; i < n; i++)
            coll.add(gen.next());
        return coll;
    }

    public static <T> List<T> fill(List<T> list, Generator<T> gen, int n) {
        for (int i = 0; i < n; i++) {
            list.add(gen.next());
        }
        return list;
    }

    public static <T> Queue<T> fill(Queue<T> queue, Generator<T> gen, int n) {
        for (int i = 0; i < n; i++) {
            queue.offer(gen.next());
        }
        return queue;
    }

    public static <T> Stack<T> fill(Stack<T> stack, Generator<T> gen, int n) {
        for (int i = 0; i < n; i++) {
            stack.push(gen.next());
        }
        return stack;
    }

    public static <T> ArrayList<T> fill(ArrayList<T> arrayList, Generator<T> gen, int n) {
        for (int i = 0; i < n; i++) {
            arrayList.add(gen.next());
        }
        return arrayList;
    }

    public static <T> LinkedList<T> fill(LinkedList<T> linkedList, Generator<T> gen, int n) {
        for (int i = 0; i < n; i++) {
            linkedList.addFirst(gen.next());
        }
        return linkedList;
    }
}
