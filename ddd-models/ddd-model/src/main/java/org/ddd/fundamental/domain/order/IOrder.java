package org.ddd.fundamental.domain.order;

import org.ddd.fundamental.domain.IKey;

import java.util.List;

/**
 * 定义一个单据的接口
 * 这个单据的接口实际上对应了三个不同的泛型类型的KEY
 * 它们分别是订单对应的key,订单明细对应的key,以及客户信息对应的key
 * 实际上应该是三个不同的泛型参数,但是因为所有的key都是同一个类型实际上可以定义一个
 */
public interface IOrder<K> extends IKey<K> {
    /**
     * 单据名字
     * @return
     */
    String name();

    IOrder<K> nextStatus();

    List<IItem<K>> items();

    <K> List<IItem<K>> mergeItems();

    <K> List<IItem<K>> mergeItemsByKey();

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
