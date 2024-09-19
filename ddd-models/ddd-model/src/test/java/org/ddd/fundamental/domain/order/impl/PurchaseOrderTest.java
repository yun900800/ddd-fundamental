package org.ddd.fundamental.domain.order.impl;

import org.ddd.fundamental.constants.ItemType;
import org.ddd.fundamental.domain.order.ICustomer;
import org.ddd.fundamental.domain.order.IItem;
import org.ddd.fundamental.domain.order.IOrder;
import org.ddd.fundamental.domain.order.impl.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class PurchaseOrderTest {

    private IOrder<String,IItem<String>> createOrder() {
        IOrder<String,IItem<String>> order = new PurchaseOrder("原材料采购订单");
        order.addItem(new RawMaterial("这是原材料",100));
        order.addItem(new WorkInProgress("这是半成品",20));
        order.addItem(new FinishedProduct("这是成品",1));
        return order;
    }

    private IOrder<String,IItem<String>> createMultipleOrder() {
        IOrder<String,IItem<String>> order = new PurchaseOrder("原材料采购订单");
        IItem<String> rawMaterial = new RawMaterial("这是原材料",100);
        IItem<String> workInProgress = new WorkInProgress("这是半成品",20);
        IItem<String> finishedProduct = new FinishedProduct("这是成品",1);
        order.addItem(rawMaterial);
        order.addItem(workInProgress);
        order.addItem(finishedProduct);

        IItem<String> rawMaterial1 = new RawMaterial("这是原材料",80);
        order.addItem(rawMaterial1);

        order.addItem(Item.copy(rawMaterial).changeQuantity(50));
        order.addItem(Item.copy(workInProgress).changeQuantity(15));
        order.addItem(Item.copy(finishedProduct).changeQuantity(2));
        return order;
    }

    private IOrder<String,IItem<String>> createEmptyOrder(){
        IOrder<String,IItem<String>> order = new PurchaseOrder("原材料采购订单");
        return order;
    }

    @Test
    public void testCreateOrder() {

        IOrder<String,IItem<String>> order = new PurchaseOrder("原材料采购订单");
        Assert.assertFalse(order.validOrder());
    }

    @Test
    public void testAddItemToOrder() {
        IOrder<String,IItem<String>> emptyOrder = createEmptyOrder();
        Assert.assertFalse(emptyOrder.validOrder());
        IOrder<String,IItem<String>> order = createOrder();
        Assert.assertTrue(order.validOrder());
    }

    @Test
    public void testItemKeys() {
        IOrder<String,IItem<String>> order = createOrder();
        Assert.assertEquals(order.itemKeys().size(),3);
    }

    @Test
    public void testContains() {
        IOrder<String,IItem<String>> order = createOrder();
        String key1 = order.itemKeys().get(0);
        String key2 = "not-found";
        Assert.assertTrue(order.contains(key1));
        Assert.assertFalse(order.contains(key2));
    }

    @Test
    public void testRemoveItem() {
        IOrder<String,IItem<String>> order = createOrder();
        String key1 = order.itemKeys().get(0);
        order.removeItem(key1);
        Assert.assertFalse(order.contains(key1));
    }

    @Test
    public void testMergeItems() {
        IOrder<String,IItem<String>> order = createMultipleOrder();
        List<IItem<String>> itemList = order.mergeItems();
        Assert.assertEquals(3,itemList.size());
        for (IItem<String> item: itemList) {
            if (item.type() == ItemType.RAW_MATERIAL) {
                Assert.assertEquals(230.0, item.quantity(),0.0);
            }
            if (item.type() == ItemType.WORK_IN_PROGRESS) {
                Assert.assertEquals(35.0, item.quantity(),0.0);
            }
            if (item.type() == ItemType.FINISHED_PRODUCT) {
                Assert.assertEquals(3.0, item.quantity(),0.0);
            }
        }
    }

    @Test
    public void testMergeItemsByKey() {
        IOrder<String,IItem<String>> order = createMultipleOrder();
        List<IItem<String>> itemList = order.mergeItemsByKey();
        Assert.assertEquals(4,itemList.size());
        for (IItem<String> item: itemList) {
            if (item.type() == ItemType.RAW_MATERIAL) {
                Assert.assertTrue(item.quantity()==150 || item.quantity()==80);
            }
            if (item.type() == ItemType.WORK_IN_PROGRESS) {
                Assert.assertEquals(35.0, item.quantity(),0.0);
            }
            if (item.type() == ItemType.FINISHED_PRODUCT) {
                Assert.assertEquals(3.0, item.quantity(),0.0);
            }
        }
    }

    @Test
    public void testSetCustomer() {
        IOrder<String,IItem<String>> order = createOrder();
        IOrder<String,IItem<String>> order1 = createOrder();
        ICustomer<String,IOrder<String,IItem<String>>> customer = new Customer("这是一个优质客户");
        order.setCustomer(customer);
        order1.setCustomer(customer);
        Assert.assertEquals(customer.friendOrders().size(),2);
        Assert.assertEquals(order.getCustomer(),order1.getCustomer());

        ICustomer<String,IOrder<String,IItem<String>>> customer1 = new Customer("这是另外一个优质客户");
        order.setCustomer(customer1);
        Assert.assertEquals(customer.friendOrders().size(),1);
        Assert.assertNotEquals(order.getCustomer(),order1.getCustomer());
        Assert.assertEquals(customer1.friendOrders().size(),1);
    }
}
