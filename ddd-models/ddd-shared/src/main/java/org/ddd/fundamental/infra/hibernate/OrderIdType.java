package org.ddd.fundamental.infra.hibernate;

import org.ddd.fundamental.workorder.value.OrderId;

public class OrderIdType extends DomainObjectIdCustomType<OrderId> {

    private static final DomainObjectIdTypeDescriptor<OrderId> TYPE_DESCRIPTOR = new DomainObjectIdTypeDescriptor<>(
            OrderId.class, OrderId::new);

    public OrderIdType() {
        super(TYPE_DESCRIPTOR);
    }
}
