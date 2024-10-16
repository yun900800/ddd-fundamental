package org.ddd.fundamental.tuple;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

interface Addable<T> {
    void add(T item);
}

interface Subable {
    <T> void sub(T item);
}

class Animal {}

class Cat extends Animal{

}

public class TupleTest {

    @Test
    public void testTwoTuple(){
        TwoTuple<Addable<Integer>,Animal> twoTuple = Tuple.tuple(new Addable<Integer>() {
            private List<Integer> items = new ArrayList<>();
            @Override
            public  void add(Integer item) {
                items.add(item);
            }
        }, new Animal());
        Assert.assertTrue(twoTuple.first.toString().contains("TupleTest$1@"));
        Assert.assertTrue(twoTuple.second.toString().contains("Animal@"));

        TwoTuple<Subable,Cat> twoTuple1 = Tuple.tuple(new Subable() {
            @Override
            public <Integer> void sub(Integer item) {

            }
        }, new Cat());
        System.out.println(twoTuple1);
        Assert.assertTrue(twoTuple1.first.toString().contains("TupleTest$2@"));
        Assert.assertTrue(twoTuple1.second.toString().contains("Cat@"));
    }

    @Test
    public void testThreeTuple() {
        ThreeTuple<Integer, String, Animal> threeTuple = Tuple.tuple(1, "hello",new Cat());
        Assert.assertEquals(threeTuple.first,1,0);
        Assert.assertEquals(threeTuple.second,"hello");
        Assert.assertTrue(threeTuple.third.toString().contains("Cat@"));
    }

}
