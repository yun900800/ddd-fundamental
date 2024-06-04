package org.ddd.fundamental.algorithms.recursive;

public class Node {
    private int val;

    private Node next;

    public Node(int val, Node next) {
        this.val = val;
        this.next = next;
    }

    public int getVal() {
        return val;
    }

    public Node getNext() {
        return next;
    }
}
