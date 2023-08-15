package org.ddd.fundamental.algorithm.basedata.linklist;

import java.util.ArrayList;
import java.util.List;

public class LinkList<T extends Comparable<T>> {

    private LinkNode<T> head;

    public LinkList() {
        this.head = null;
    }

    public LinkList(LinkNode<T> head) {
        this.head = head;
    }

    public LinkList<T> addData(T data) {
        LinkNode<T> temp = new LinkNode(data);
        if (this.head != null) {
            temp.setNext(this.head);
        }
        this.head = temp;
        return this;
    }

    public LinkList<T> addSortData(T data) {
        LinkNode<T> temp = new LinkNode(data);
        if (this.head == null) {
            this.head = temp;
        } else {
            LinkNode<T> nodePre = this.findPreOfCur(data);
            if (null == nodePre) {
                temp.setNext(this.head);
                this.head = temp;
            } else {
                LinkNode<T> nodeNext = nodePre.getNext();
                nodePre.setNext(temp);
                temp.setNext(nodeNext);
            }
        }
        return this;
    }

    public LinkNode findPreOfCur(T data) {
        LinkNode<T> p = this.head;
        LinkNode<T> pre = null;
        while (p!=null && p.getData().compareTo(data)< 0) {
            pre = p;
            p = p.getNext();
        }
        return pre;
    }

    public LinkNode<T> mergeLinkList(LinkNode<T> linkP, LinkNode<T> linkQ) {
        LinkNode<T> dummy = new LinkNode<>(null);
        LinkNode<T> cur = dummy;
        LinkNode<T> p = linkP;
        LinkNode<T> q = linkQ;
        while (p != null && q!= null) {
            if (p.getData().compareTo(q.getData()) < 0) {
                cur.setNext(p);
                p = p.getNext();
            } else {
                cur.setNext(q);
                q = q.getNext();
            }
            cur = cur.getNext();
        }
        if (p == null) {
            cur.setNext(q);
        }
        if (q == null) {
            cur.setNext(p);
        }
        return dummy;
    }

    public LinkNode<T> findLastK(int k) {
        if (k<=0) {
            return null;
        }
        LinkNode<T> p = head;
        while (k-->0 ) {
            if(p == null) {
                return null;
            }
            p = p.getNext();
        }
        LinkNode<T> q = head;
        while (p!= null) {
            p = p.getNext();
            q = q.getNext();
        }
        return q;
    }


    public List<T> forIterator() {
        List<T> collectors = new ArrayList<>();
        for (LinkNode<T> p = this.head;p !=null; p = p.getNext()) {
            collectors.add(p.getData());
        }
        return collectors;
    }

    public List<T> recursivePre() {
        LinkNode<T> head = this.head;
        List<T> collectors = new ArrayList<>();
        this.recursivePreAndCollect(head,collectors);
        return collectors;
    }

    private void recursivePreAndCollect(LinkNode<T> head, List<T> collectors) {
        if (head == null) {
            return;
        }
        T data = head.getData();
        collectors.add(data);
        this.recursivePreAndCollect(head.getNext(), collectors);
    }

    public List<T> recursivePost() {
        LinkNode<T> head = this.head;
        List<T> collectors = new ArrayList<>();
        this.recursivePostAndCollect(head,collectors);
        return collectors;
    }

    private void recursivePostAndCollect(LinkNode<T> head, List<T> collectors) {
        if (head == null) {
            return;
        }

        this.recursivePostAndCollect(head.getNext(), collectors);
        T data = head.getData();
        collectors.add(data);
    }

    public void setHead(LinkNode<T> head) {
        this.head = head;
    }

    public LinkNode<T> getHead() {
        return head;
    }
}
