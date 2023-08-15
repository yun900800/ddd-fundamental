package org.ddd.fundamental.algorithms.basedata.binaryheap;

import org.ddd.fundamental.algorithm.basedata.binaryheap.MaxPQ;
import org.junit.Assert;
import org.junit.Test;

public class MaxPQTest {

    private MaxPQ<Integer> init() {
        MaxPQ<Integer> maxPQ = new MaxPQ<>(99);
        maxPQ.insert(5);
        maxPQ.insert(8);
        maxPQ.insert(1);
        maxPQ.insert(20);
        maxPQ.insert(-5);
        return maxPQ;
    }

    @Test
    public void testInsert() {
        MaxPQ<Integer> maxPQ = init();
        Assert.assertEquals(20, maxPQ.max(),0);
        Assert.assertEquals(5, maxPQ.getN());
    }

    @Test
    public void testDeleteMax() {
        MaxPQ<Integer> maxPQ = init();
        maxPQ.deleteMax();
        Assert.assertEquals(8, maxPQ.max(),0);
        Assert.assertEquals(4, maxPQ.getN());
    }
}
