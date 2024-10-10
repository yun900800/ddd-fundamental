package org.ddd.fundamental.generics;

import org.ddd.fundamental.generics.interfaces.CoffeeGenerator;
import org.ddd.fundamental.generics.interfaces.Fibonacci;
import org.ddd.fundamental.generics.interfaces.Generator;
import org.ddd.fundamental.generics.interfaces.model.Coffee;

import java.util.*;

public class Generators {
    public static <T> Collection<T> fill(Collection<T> coll, Generator<T> gen,int n){
        for(int i = 0; i < n; i++)
            coll.add(gen.next());
        return coll;
    }

//    public static void main(String[] args) {
//        Collection<Coffee> coffee = fill(
//                new ArrayList<>(), new CoffeeGenerator(), 4);
//        for(Coffee c : coffee)
//            System.out.println(c);
//        Collection<Integer> fnumbers = fill(
//                new ArrayList<>(), new Fibonacci(), 12);
//        for(int i : fnumbers)
//            System.out.print(i + ", ");
//    }

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

    public static void main(String[] args) {
        Collection<Coffee> coffee = fill(new ArrayList<Coffee>(), new CoffeeGenerator(), 4);
        for(Coffee c : coffee) System.out.println(c);
        Collection<Integer> fnumbers = fill(new ArrayList<Integer>(), new Fibonacci(), 12);
        for(int i : fnumbers) System.out.print(i + ", ");
        System.out.println();
        List<Coffee> coffeeList = fill(new ArrayList<Coffee>(), new CoffeeGenerator(), 5);
        System.out.println("List type: " + coffeeList.getClass());
        System.out.println("coffeeList:" + coffeeList);
        Queue<Coffee> coffeeQueue = fill(new ArrayDeque<Coffee>(), new CoffeeGenerator(), 5);
        System.out.println("Queue type: " + coffeeQueue.getClass());
        System.out.println("coffeeQueue: " + coffeeQueue);
//        Set<Coffee> coffeeSet = fill(new HashSet<Coffee>(), new CoffeeGenerator(), 5);
//        System.out.println("Set type: " + coffeeSet.getClass());
        //System.out.println("coffeeSet: " + coffeeSet);
        LinkedList<Coffee> coffeeLinkedList = fill(new LinkedList<Coffee>(), new CoffeeGenerator(), 5);
        System.out.println("LinkedList type: " + coffeeLinkedList.getClass());
        System.out.println("coffeeLinkedList: " + coffeeLinkedList);
    }
}
