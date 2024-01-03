package org.ddd.fundamental.algorithm.basedata.binaryheap;

public class MaxPQ<Key extends Comparable<Key>>{

    private Key[] pq;

    private int N = 0;

    public MaxPQ(int cap) {
        this.pq = (Key[])new Comparable[cap+1];
    }

    public Key[] getPq() {
        return pq;
    }

    public void setPq(Key[] pq) {
        this.pq = pq;
    }

    public int getN() {
        return N;
    }

    public void setN(int n) {
        N = n;
    }

    public Key max(){
        return pq[1];
    }

    public int left(int root) {
        return root * 2;
    }

    public int right(int root) {
        return root * 2 + 1;
    }

    public int parent(int root) {
        return root / 2;
    }

    public void insert(Key e) {
        this.N++;
        this.pq[N] = e;
        this.swim(N);
    }
    public Key deleteMax() {
        Key max = this.max();
        exchange(1, N);
        this.pq[N] = null;
        this.N--;
        this.sink(1);
        return max;
    }

    private void swim(int k) {
        while (k > 1 && less(parent(k), k)) {
            exchange(parent(k),k);
            k = parent(k);
        }
    }
    private void sink(int k) {
        while (left(k) < N){
            int older = left(k);
            if (right(k)<=N && less(left(k), right(k))) {
                older = right(k);
            }
            //上面获取左节点和右节点的最大值
            if (less(older, k)) {
                break;
            }
            exchange(older, k);
            k = older;
        }
    }

    private void exchange(int i, int j) {
        Key temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
    }

    private boolean less(int i, int j) {
        return pq[i].compareTo(pq[j]) < 0;
    }
}
