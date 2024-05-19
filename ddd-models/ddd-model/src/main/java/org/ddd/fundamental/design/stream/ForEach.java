package org.ddd.fundamental.design.stream;

@FunctionalInterface
public interface ForEach <T>{

    /**
     * 迭代器遍历
     * @param item 被迭代的每一项
     * */
    void apply(T item);
}
