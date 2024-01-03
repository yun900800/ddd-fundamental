package org.ddd.fundamental.algorithm.basedata.linklist;

public class LinkNode<T> {

    private T data;

    private LinkNode<T> next;

    public LinkNode(T data){
        this.data = data;
        this.next = null;
    }

    public void setNext(LinkNode<T> next) {
        this.next = next;
    }

    public T getData() {
        return data;
    }

    public LinkNode<T> getNext() {
        return next;
    }
}
