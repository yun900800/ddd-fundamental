package org.ddd.fundamental.generics.inference;

import java.util.*;

public class New {
    public static <K,V> Map<K,V> map() {
        return new HashMap<K,V>();
    }
    public static <T> List<T> list() {
        return new ArrayList<T>();
    }
    public static <T> LinkedList<T> lList() {
        return new LinkedList<T>();
    }
    public static <T> Set<T> set() {
        return new HashSet<T>();
    }
    public static <T> Queue<T> queue() {
        return new LinkedList<T>();
    }

    public static void main(String[] args) {
        Map<Person, List<? extends Pet>> petPeople = New.map();
        // Rest of the code is the same...

        f(New.map());
        f(New.<Person, List<? extends Pet>>map());
        f(petPeople);
    }
    static void
    f(Map<Person, List<? extends Pet>> petPeople) {
        System.out.println(petPeople.getClass().getName());
        petPeople.put(new Person(),List.of(new Pet()));
        System.out.println(petPeople);
    }
}

class Person{

}

class Pet{

}
