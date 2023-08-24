package org.ddd.fundamental.algorithms.basedata.operation;

import org.ddd.fundamental.algorithm.basedata.operation.Stack;
import org.junit.Assert;
import org.junit.Test;

public class StackTest {

    @Test
    public void testStackPush() {
        Stack<Integer> stack = new Stack<>(10,Integer.class);
        Assert.assertNull(stack.peek());

        stack.push(5);
        Assert.assertNotNull(stack.peek());
        stack.push(1);
        stack.push(2);
        Integer[] expect = new Integer[]{5,1,2,null,null,null,null,null,null,null};
        Assert.assertArrayEquals(expect, stack.getArrays());
    }

    @Test
    public void testStackPeek() {
        Stack<Integer> stack = new Stack<>(10, Integer.class);
        stack.push(5);
        stack.push(1);
        stack.push(2);
        Assert.assertEquals(2,stack.peek().intValue());
        Assert.assertEquals(2,stack.peek().intValue());
    }

    public void testStackPop() {
        Stack<Integer> stack = new Stack<>(10, Integer.class);
        stack.push(5);
        stack.push(1);
        stack.push(2);
        Assert.assertEquals(2,stack.pop().intValue());
        Assert.assertEquals(1,stack.pop().intValue());
    }
}
