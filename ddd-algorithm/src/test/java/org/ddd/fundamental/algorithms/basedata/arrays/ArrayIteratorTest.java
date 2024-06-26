package org.ddd.fundamental.algorithms.basedata.arrays;

import org.ddd.fundamental.algorithm.basedata.arrays.ArrayIterator;
import org.ddd.fundamental.algorithm.basedata.random.Procedure;
import org.ddd.fundamental.algorithm.basedata.random.RandomUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ArrayIteratorTest {

    @Test
    public void testForIterator() {
        List<String> source = ArrayIterator.generate(10);
        ArrayIterator arrayIterator = new ArrayIterator(source);
        String actual = source.stream().collect(Collectors.joining("->"));
        Assert.assertEquals("["+actual+"]", arrayIterator.forIterator());
    }

    @Test
    public void testRecursiveBefore() {
        List<String> source = ArrayIterator.generate(10);
        ArrayIterator arrayIterator = new ArrayIterator(source);
        List<String> recursiveRes = arrayIterator.recursivePre();
        String[] arr = new String[source.size()];
        String[] expect = source.toArray(arr);
        String[] actual = recursiveRes.toArray(arr);
        Assert.assertArrayEquals(expect,actual);
    }

    @Test
    public void testRecursiveAfter() {
        List<String> source = ArrayIterator.generate(10);
        ArrayIterator arrayIterator = new ArrayIterator(source);
        List<String> recursiveRes = arrayIterator.recursivePost();
        String expect = arrayIterator.forIterator();
        ArrayIterator anotherArrayIterator = new ArrayIterator(recursiveRes);
        String actual = anotherArrayIterator.forIterator();
        Assert.assertNotEquals(expect, actual);
        String[] arr = new String[source.size()];
        String[] expect1 = source.toArray(arr);
        String[] actual1 = recursiveRes.toArray(arr);
        Assert.assertArrayEquals(expect1,actual1);
    }

    @Test
    public void testFastSort() {
        int count = 50000;
        Set<Integer> integerSet = RandomUtils.generateRandom(count);
        int[] data = RandomUtils.setToArrayInt(count,integerSet);

        Procedure fn = () ->{
            ArrayIterator.fastSearch(data,0,data.length-1);
            System.out.println("sort finished");
        };
        RandomUtils.fnExecCostTime(fn);
    }
}
