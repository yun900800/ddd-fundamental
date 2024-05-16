package org.ddd.fundamental.domain.order;

import org.ddd.fundamental.domain.IKey;

import java.util.Set;

public interface ICustomer<K>  extends IKey<K> {

    String name();

    Set<IOrder<K>> friendOrders();

    void addOrder(IOrder<K> order);


}
