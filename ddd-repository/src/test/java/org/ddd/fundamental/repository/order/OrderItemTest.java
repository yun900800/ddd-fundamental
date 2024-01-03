package org.ddd.fundamental.repository.order;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class OrderItemTest {

    @Test
    public void testCreateOrderItem() {
        OrderItem orderItem = OrderItem.create("香蕉", BigDecimal.valueOf(4.25));
        Assert.assertEquals("香蕉", orderItem.getProductName());
        Assert.assertEquals(BigDecimal.valueOf(4.25), orderItem.getItemAmount());
        Assert.assertEquals(1, orderItem.getQuantity());
        Assert.assertEquals(-1, orderItem.getId(),0);
        Assert.assertEquals("这是好吃点为您推荐的优质商品", orderItem.getDescription());

        orderItem = OrderItem.create("橘子", BigDecimal.valueOf(2.25), 5, "这个橘子真甜");
        Assert.assertEquals("橘子", orderItem.getProductName());
        Assert.assertEquals(BigDecimal.valueOf(2.25), orderItem.getItemAmount());
        Assert.assertEquals(5, orderItem.getQuantity());
        Assert.assertEquals(-1, orderItem.getId(),0);
        Assert.assertEquals("这个橘子真甜", orderItem.getDescription());
    }


}
