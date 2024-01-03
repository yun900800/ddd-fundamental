package org.ddd.fundamental.repository.order;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class OrderTest {

    @Test
    public void testCreateNoItemOrder() {
        Order order = Order.create("水果订单","水果真好吃");
        Assert.assertEquals("水果订单",order.getName());
        Assert.assertEquals("水果真好吃",order.getDescription());
        Assert.assertEquals(-1,order.getId(),0);
        Assert.assertNotNull(order.getOrderItems());
        Assert.assertTrue(order.getOrderItems().isEmpty());
        Assert.assertEquals(order.getOrderAmount(), BigDecimal.ZERO);
    }



    @Test
    public void testOrderAddOrderItem() {
        Order order = Order.create("特殊水果订单","水果真好吃");
        OrderItem orderItem = OrderItem.create(
                "香蕉", BigDecimal.valueOf(4.25), 5, "香蕉特别好吃");
        OrderItem orderItem1 = OrderItem.create(
                "橘子", BigDecimal.valueOf(2.25), 2, "橘子有点酸");
        order.addOrderItem(orderItem).addOrderItem(orderItem1);
        Assert.assertNotNull(order.getOrderItems());
        Assert.assertEquals(order.getOrderItems().size(),2);
        Assert.assertNotEquals(order.getOrderAmount(), BigDecimal.ZERO);
        Assert.assertEquals(order.getOrderAmount(), BigDecimal.valueOf(25.75));
    }

    @Test
    public void testOrderAddSameNameOrderItem() {
        Order order = Order.create("特殊水果订单","水果真好吃");
        OrderItem orderItem = OrderItem.create(
                "香蕉", BigDecimal.valueOf(4.25), 5, "香蕉特别好吃");
        OrderItem orderItem1 = OrderItem.create(
                "橘子", BigDecimal.valueOf(2.25), 2, "橘子有点酸");
        OrderItem orderItem2 = OrderItem.create(
                "橘子", BigDecimal.valueOf(2.25), 1, "橘子有点酸");
        order.addOrderItem(orderItem).addOrderItem(orderItem1).addOrderItem(orderItem2);
        Assert.assertNotNull(order.getOrderItems());
        Assert.assertEquals(order.getOrderItems().size(),3);
        Assert.assertNotEquals(order.getOrderAmount(), BigDecimal.ZERO);
        Assert.assertEquals(BigDecimal.valueOf(28).setScale(2),order.getOrderAmount());
    }

    @Test
    public void testOrderRemoveOrderItem() {
        Order order = Order.create("特殊水果订单","水果真好吃");
        OrderItem orderItem = OrderItem.create(
                "香蕉", BigDecimal.valueOf(4.25), 5, "香蕉特别好吃");
        OrderItem orderItem1 = OrderItem.create(
                "橘子", BigDecimal.valueOf(2.25), 2, "橘子有点酸");
        OrderItem orderItem2 = OrderItem.create(
                "橘子", BigDecimal.valueOf(2.25), 1, "橘子有点酸");
        order.addOrderItem(orderItem).addOrderItem(orderItem1)
                .addOrderItem(orderItem2).removeOrderItem(orderItem1);
        Assert.assertNotNull(order.getOrderItems());
        Assert.assertEquals(order.getOrderItems().size(),2);
        Assert.assertNotEquals(order.getOrderAmount(), BigDecimal.ZERO);
        Assert.assertEquals(BigDecimal.valueOf(23.50).setScale(2),order.getOrderAmount());
    }

    @Test
    public void testOrderRemoveNotSameOrderItem() {
        Order order = Order.create("特殊水果订单","水果真好吃");
        OrderItem orderItem = OrderItem.create(
                "香蕉", BigDecimal.valueOf(4.25), 5, "香蕉特别好吃");
        OrderItem orderItem1 = OrderItem.create(
                "橘子", BigDecimal.valueOf(2.25), 2, "橘子有点酸");
        OrderItem orderItem2 = OrderItem.create(
                "橘子", BigDecimal.valueOf(2.25), 1, "橘子有点酸");
        order.addOrderItem(orderItem).addOrderItem(orderItem1)
                .addOrderItem(orderItem2);
        OrderItem orderItem3 = OrderItem.create(
                "橘子", BigDecimal.valueOf(2.25), 5, "橘子有点酸");
        order.removeOrderItem(orderItem3);
        Assert.assertNotNull(order.getOrderItems());
        Assert.assertEquals(order.getOrderItems().size(),2);
        Assert.assertNotEquals(order.getOrderAmount(), BigDecimal.ZERO);
        Assert.assertEquals(BigDecimal.valueOf(16.75).setScale(2),order.getOrderAmount());
    }
}
