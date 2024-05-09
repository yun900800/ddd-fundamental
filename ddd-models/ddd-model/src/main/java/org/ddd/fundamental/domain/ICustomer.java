package org.ddd.fundamental.domain;

import java.util.Set;

public interface ICustomer<K> {

    K key();

    String name();

    Set<IOrder<K>> friendOrders();

    void addOrder(IOrder<K> order);


}
