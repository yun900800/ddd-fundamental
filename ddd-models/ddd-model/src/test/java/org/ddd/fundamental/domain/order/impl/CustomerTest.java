package org.ddd.fundamental.domain.order.impl;

import org.ddd.fundamental.domain.order.ICustomer;
import org.ddd.fundamental.domain.order.IOrder;
import org.ddd.fundamental.domain.order.impl.*;
import org.junit.Assert;
import org.junit.Test;

public class CustomerTest {

    private IOrder<String> createOrder() {
        IOrder<String> order = new PurchaseOrder("原材料采购订单");
        order.addItem(new RawMaterial("这是原材料",100));
        order.addItem(new WorkInProgress("这是半成品",20));
        order.addItem(new FinishedProduct("这是成品",1));
        return order;
    }

    @Test
    public void testCreateCustomer() {
        ICustomer<String> customer = new Customer("这是一个优质客户");
        IOrder<String> order = createOrder();
        customer.addOrder(order);
        IOrder<String> order1 = createOrder();
        customer.addOrder(order1);
        Assert.assertEquals(customer.friendOrders().size(),2);
        Assert.assertEquals(order.getCustomer(),order1.getCustomer());
    }
}
