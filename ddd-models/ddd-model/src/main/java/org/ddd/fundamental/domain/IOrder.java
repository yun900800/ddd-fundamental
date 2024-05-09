package org.ddd.fundamental.domain;

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

    K key();

    IOrder<K> nextStatus();

    List<IItem<K>> items();

    List<IItem<String>> mergeItems();

    List<IItem<String>> mergeItemsByKey();

    /**
     * 单据是否合法
     * @return
     */
    boolean validOrder();

    IOrder<K> addItem(IItem<K> item);

    IOrder<K> removeItem(K key);

    List<K> itemKeys();

    boolean contains(K key);

    void setCustomer(ICustomer<K> arg);

    ICustomer<K> getCustomer();


}
