package org.ddd.fundamental.model.impl;

import org.ddd.fundamental.model.IOrder;
import org.junit.Assert;
import org.junit.Test;

public class PurchaseOrderTest {

    private IOrder<String> createOrder() {
        IOrder<String> order = new PurchaseOrder("原材料采购订单");
        order.addItem(new RawMaterial("这是原材料"));
        order.addItem(new WorkInProgress("这是半成品"));
        order.addItem(new FinishedProduct("这是成品"));
        return order;
    }

    @Test
    public void testCreateOrder() {
        IOrder<String> order = new PurchaseOrder("原材料采购订单");
        Assert.assertFalse(order.validOrder());

        Assert.assertEquals(order.name(),"原材料采购订单");
    }

    @Test
    public void testAddItemToOrder() {
        IOrder<String> order = createOrder();
        Assert.assertTrue(order.validOrder());
    }

    @Test
    public void testItemKeys() {
        IOrder<String> order = createOrder();
        Assert.assertEquals(order.itemKeys().size(),3);
    }

    @Test
    public void testContains() {
        IOrder<String> order = createOrder();
        String key1 = order.itemKeys().get(0);
        String key2 = "not-found";
        Assert.assertTrue(order.contains(key1));
        Assert.assertFalse(order.contains(key2));
    }
}
