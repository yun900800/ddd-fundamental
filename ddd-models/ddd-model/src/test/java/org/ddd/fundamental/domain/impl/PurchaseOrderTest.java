package org.ddd.fundamental.domain.impl;

import org.ddd.fundamental.constants.ItemType;
import org.ddd.fundamental.domain.IItem;
import org.ddd.fundamental.domain.IOrder;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class PurchaseOrderTest {

    private IOrder<String> createOrder() {
        IOrder<String> order = new PurchaseOrder("原材料采购订单");
        order.addItem(new RawMaterial("这是原材料",100));
        order.addItem(new WorkInProgress("这是半成品",20));
        order.addItem(new FinishedProduct("这是成品",1));
        return order;
    }

    private IOrder<String> createMultipleOrder() {
        IOrder<String> order = new PurchaseOrder("原材料采购订单");
        order.addItem(new RawMaterial("这是原材料",100));
        order.addItem(new WorkInProgress("这是半成品",20));
        order.addItem(new FinishedProduct("这是成品",1));

        order.addItem(new RawMaterial("这是原材料",50));
        order.addItem(new WorkInProgress("这是半成品",15));
        order.addItem(new FinishedProduct("这是成品",2));
        return order;
    }

    private IOrder<String> createEmptyOrder(){
        IOrder<String> order = new PurchaseOrder("原材料采购订单");
        return order;
    }

    @Test
    public void testCreateOrder() {

        IOrder<String> order = new PurchaseOrder("原材料采购订单");
        Assert.assertFalse(order.validOrder());
    }

    @Test
    public void testAddItemToOrder() {
        IOrder<String> emptyOrder = createEmptyOrder();
        Assert.assertFalse(emptyOrder.validOrder());
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

    @Test
    public void testRemoveItem() {
        IOrder<String> order = createOrder();
        String key1 = order.itemKeys().get(0);
        order.removeItem(key1);
        Assert.assertFalse(order.contains(key1));
    }

    @Test
    public void testMergeItems() {
        IOrder<String> order = createMultipleOrder();
        List<IItem<String>> itemList = order.mergeItems();
        Assert.assertEquals(3,itemList.size());
        for (IItem<String> item: itemList) {
            if (item.type() == ItemType.RAW_MATERIAL) {
                Assert.assertEquals(150.0, item.quantity(),0.0);
            }
            if (item.type() == ItemType.WORK_IN_PROGRESS) {
                Assert.assertEquals(35.0, item.quantity(),0.0);
            }
            if (item.type() == ItemType.FINISHED_PRODUCT) {
                Assert.assertEquals(3.0, item.quantity(),0.0);
            }
        }
    }
}
