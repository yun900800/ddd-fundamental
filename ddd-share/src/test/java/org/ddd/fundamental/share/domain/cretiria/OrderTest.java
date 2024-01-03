package org.ddd.fundamental.share.domain.cretiria;

import org.ddd.fundamental.share.domain.creteria.Order;
import org.ddd.fundamental.share.domain.creteria.OrderBy;
import org.ddd.fundamental.share.domain.creteria.OrderType;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

public class OrderTest {

    @Test
    public void testCreateOrder() {
        OrderBy orderBy = new OrderBy("createOn");
        Order order = new Order(orderBy, OrderType.DESC);
        Assert.assertEquals(order.orderBy(),orderBy);
        Assert.assertEquals(order.orderType(),OrderType.DESC);

        Order noOrder = Order.none();
        Assert.assertEquals(noOrder.orderBy(),new OrderBy(""));
        Assert.assertEquals(noOrder.orderType(),OrderType.NONE);

        Order descOrder = Order.desc("createOn");
        Assert.assertEquals(descOrder.orderBy(),orderBy);
        Assert.assertEquals(descOrder.orderType(),OrderType.DESC);

        Order ascOrder = Order.asc("createOn");
        Assert.assertEquals(ascOrder.orderBy(),orderBy);
        Assert.assertEquals(ascOrder.orderType(),OrderType.ASC);
    }

    @Test
    public void testFromValues() {
        OrderBy orderBy = new OrderBy("createOn");
        Order order = Order.fromValues(Optional.of("createOn"), Optional.of("DESC"));
        Assert.assertEquals(order.orderBy(),orderBy);
        Assert.assertEquals(order.orderType(),OrderType.DESC);
        order = Order.fromValues(Optional.of("createOn"), Optional.of("NONE"));
        Assert.assertEquals(order.orderType(),OrderType.NONE);

        order = Order.fromValues(Optional.of("createOn"), Optional.of("ASC"));
        Assert.assertEquals(order.orderType(),OrderType.ASC);

        order = Order.fromValues(Optional.of("createOn"), Optional.empty());
        Assert.assertEquals(order.orderType(),OrderType.ASC);

        order = Order.fromValues(Optional.of("createOn"), null);
        Assert.assertEquals(order.orderType(),OrderType.ASC);

        order = Order.fromValues(Optional.of("createOn"), Optional.of(""));
        Assert.assertEquals(order.orderType(),OrderType.ASC);

        order = Order.fromValues(Optional.of("createOn"), Optional.of("asc"));
        Assert.assertEquals(order.orderType(),OrderType.ASC);

        order = Order.fromValues(Optional.of("createOn"), Optional.of("aaa"));
        Assert.assertEquals(order.orderType(),OrderType.NONE);
    }
}
