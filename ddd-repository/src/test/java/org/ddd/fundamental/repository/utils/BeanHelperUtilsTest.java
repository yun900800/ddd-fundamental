package org.ddd.fundamental.repository.utils;

import org.ddd.fundamental.repository.order.OrderItem;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BeanHelperUtilsTest {

    @Test
    public void testDeepCopy() {
        OrderItem orderItem = OrderItem.create("香蕉",
                BigDecimal.ONE);
        OrderItem orderItem1 = OrderItem.create("橘子",
                BigDecimal.TEN);
        List<OrderItem> list = new ArrayList<>();
        list.add(orderItem);
        list.add(orderItem1);
        List<OrderItem> desc = BeanHelperUtils.deepCopy(list);
        Assert.assertEquals(2,desc.size());
        OrderItem orderItem0 = desc.get(0);
        Assert.assertEquals("香蕉",orderItem0.getProductName());
        Assert.assertEquals(BigDecimal.ONE,orderItem0.getItemAmount());
        Assert.assertEquals(1.0,orderItem0.getProductId(),0);
    }
}


