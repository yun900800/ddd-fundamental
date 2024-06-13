package org.ddd.fundamental.algorithms.recursive;

import org.junit.Assert;
import org.junit.Test;

public class NodeUtilsTest {

    private static final int MAX_LENGTH =10000;

    public  static Node createHead() {
        Node node5 = new Node(1, null);
        Node node4 = new Node(2,node5);
        Node node3 = new Node(3,node4);
        Node node2 = new Node(4,node3);
        Node node1 = new Node(5,node2);
        Node head = new Node(6,node1);
        return head;
    }

    public static Node createManyNode() {
        Node nodeTemp = new Node(1, null);
        for (int i = 0; i < MAX_LENGTH; i++) {
            Node node = new Node(i,nodeTemp);
            nodeTemp = node;
        }
        Node head = new Node(MAX_LENGTH+1,nodeTemp);
        return head;
    }

    @Test
    public void testGetLengthRecursive(){
        Node head = createHead();
        Assert.assertEquals(NodeUtils.getLengthRecursive(head),6,0);
        Node head1 = createManyNode();
        Assert.assertEquals(NodeUtils.getLengthRecursive(head1),MAX_LENGTH+2,0);
    }


    @Test
    public void testGetLengthRailRecursive() {
        Node head = createHead();
        Assert.assertEquals(NodeUtils.getLengthRailRecursive(head,0),6,0);
//        java不支持尾递归优化
        Node head1 = createManyNode();
        Assert.assertEquals(NodeUtils.getLengthRailRecursive(head1,0),MAX_LENGTH+2,0);
    }

    @Test
    public void testFibonacciRecursively(){
        int fib10 = NodeUtils.fibonacciRecursively(10);
        Assert.assertEquals(fib10,89);
    }

    @Test
    public void testFibonacciTailRecursively() {
        int fib10 = NodeUtils.fibonacciTailRecursively(10,0,1);
        Assert.assertEquals(fib10,89);
    }

    @Test
    public void testFactorialRecursively() {
        int fac5 = NodeUtils.factorialRecursively(5);
        Assert.assertEquals(fac5,120);
    }

    @Test
    public void testFactorialTailRecursively() {
        int fac5 = NodeUtils.factorialTailRecursively(5);
        Assert.assertEquals(fac5,120);
    }

    @Test
    public void testFactorialContinuation() {
        int fac5 = NodeUtils.factorialContinuation(5,x->x);
        Assert.assertEquals(fac5,120);
    }

    @Test
    public void testFactorialContinuationWithOne() {
        int fac1 = NodeUtils.factorialContinuation(1,x->x);
        Assert.assertEquals(fac1,1);
    }

    @Test
    public void testFibonacciContinuation(){
        int fib10 = NodeUtils.fibonacciContinuation(10,x->x);
        Assert.assertEquals(fib10,89);
    }

    @Test
    public void testChargeCount(){
        int fib10 = NodeUtils.chargeCount(100,5,x->x);
        Assert.assertEquals(fib10,293);
    }

}
