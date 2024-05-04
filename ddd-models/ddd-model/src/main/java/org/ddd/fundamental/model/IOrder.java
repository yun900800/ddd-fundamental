package org.ddd.fundamental.model;

import java.util.List;

/**
 * 定义一个单据的接口
 */
public interface IOrder<K> {
    /**
     * 单据名字
     * @return
     */
    String name();

    IOrder<K> nextStatus();

    List<IItem<K>> items();

    /**
     * 单据是否合法
     * @return
     */
    boolean validOrder();

    IOrder<K> addItem(IItem<K> item);

    List<K> itemKeys();

    boolean contains(K key);

}
