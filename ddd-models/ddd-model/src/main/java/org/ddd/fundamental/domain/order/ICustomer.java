package org.ddd.fundamental.domain.order;

import org.ddd.fundamental.domain.IKey;

import java.util.Set;

public interface ICustomer<K,O extends IOrder<K,IItem<K>>>  extends IKey<K> {

    String name();

    Set<O> friendOrders();

    void addOrder(O order);

}
