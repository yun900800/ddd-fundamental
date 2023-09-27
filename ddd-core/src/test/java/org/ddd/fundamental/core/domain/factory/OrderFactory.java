package org.ddd.fundamental.core.domain.factory;

import org.ddd.fundamental.core.domain.entity.Identity;
import org.ddd.fundamental.core.exception.OrderCreationException;

public final class OrderFactory extends EntityFactoryBase<Order,OrderVO>{

    public final static OrderFactory INSTANCE = new OrderFactory();
    @Override
    public Order create(OrderVO voInfo) throws OrderCreationException {
        if (null == voInfo) {
            throw new OrderCreationException();
        }
        return new Order(new Identity(1000L));
    }

    @Override
    public Order load(OrderVO voInfo) throws OrderCreationException {
        if (null == voInfo) {
            throw new OrderCreationException();
        }
        return new Order(new Identity(1000L));
    }
}
