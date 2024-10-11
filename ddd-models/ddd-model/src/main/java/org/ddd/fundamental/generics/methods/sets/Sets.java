package org.ddd.fundamental.generics.methods.sets;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class Sets {
    public static <T> Set<T> union(Set<T> a, Set<T> b){
        Set<T> result = new HashSet<>(a);
        result.addAll(b);
        return result;
    }

    public static <T> Set<T> intersection(Set<T> a, Set<T> b){
        Set<T> result = new HashSet<>(a);
        result.retainAll(b);
        return result;
    }

    public static <T> Set<T> difference(Set<T> superset, Set<T> subset){
        Set<T> result = new HashSet<T>(superset);
        result.removeAll(subset);
        return result;
    }

    public static <T> Set<T> complement(Set<T> a, Set<T> b) {
        return difference(union(a, b), intersection(a, b));
    }

    public static <T extends Enum<T>> EnumSet<T> union(EnumSet<T> a, EnumSet<T> b) {
        EnumSet<T> result = a.clone();
        result.addAll(b);
        return result;
    }

    public static <T extends Enum<T>> EnumSet<T> intersection(EnumSet<T> a, EnumSet<T> b) {
        EnumSet<T> result = a.clone();
        a.removeAll(b);
        return result;
    }

    public static <T extends Enum<T>> EnumSet<T> difference(EnumSet<T> a, EnumSet<T> b) {
        EnumSet<T> result = a.clone();
        result.removeAll(b);
        return result;
    }

    public static <T extends Enum<T>> EnumSet<T> complement(EnumSet<T> a, EnumSet<T> b) {
        return difference(union(a, b), intersection(a, b));
    }
}
