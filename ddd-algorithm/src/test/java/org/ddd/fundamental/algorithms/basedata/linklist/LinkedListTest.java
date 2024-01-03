package org.ddd.fundamental.algorithms.basedata.linklist;

import org.junit.Assert;
import org.junit.Test;

import java.util.Deque;
import java.util.LinkedList;

public class LinkedListTest {

    @Test
    public void testQueue() {
        LinkedList<String> queue = new LinkedList<>();
        queue.offer("hello");
        queue.offer("world");
        queue.offer("nice");
        queue.offer("girl");
        StringBuilder sb = new StringBuilder();
        while (!queue.isEmpty()) {
            String element = queue.peek();
            sb.append(element);
            queue.poll();
        }
        Assert.assertTrue(queue.isEmpty());
        Assert.assertEquals("helloworldnicegirl",sb.toString());
    }

    @Test
    public void testStack() {
        Deque<String> stack = new LinkedList<>();
        stack.push("a");
        stack.push("b");
        stack.push("c");
        stack.push("d");
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            String element = stack.peek();
            sb.append(element);
            stack.pop();
        }
        Assert.assertTrue(stack.isEmpty());
        Assert.assertEquals("dcba",sb.toString());
    }
}
