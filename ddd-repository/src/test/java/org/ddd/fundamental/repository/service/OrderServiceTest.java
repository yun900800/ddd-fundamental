package org.ddd.fundamental.repository.service;

import org.ddd.fundamental.repository.App;
import org.ddd.fundamental.repository.order.Order;
import org.ddd.fundamental.repository.order.OrderItem;
import org.ddd.fundamental.repository.order.OrderStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = App.class)
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    Long orderId = -1L;

    @Test
    public void testSaveOrder() {
        Order order = Order.create("特殊水果订单","水果真好吃");
        OrderItem orderItem = OrderItem.create(
                "香蕉", BigDecimal.valueOf(4.25), 5, "香蕉特别好吃");
        OrderItem orderItem1 = OrderItem.create(
                "橘子", BigDecimal.valueOf(2.25), 2, "橘子有点酸");
        order.addOrderItem(orderItem).addOrderItem(orderItem1);
        orderService.save(order);

        Order newOrder = orderService.loadOrder(order.getId());
        Assert.assertEquals(BigDecimal.valueOf(25.75), newOrder.getOrderAmount());
    }

    @Before
    public void init() {
        Order order = Order.create("特殊水果订单","水果真好吃");
        OrderItem orderItem = OrderItem.create(
                "香蕉", BigDecimal.valueOf(4.25), 5, "香蕉特别好吃");
        OrderItem orderItem1 = OrderItem.create(
                "橘子", BigDecimal.valueOf(2.25), 2, "橘子有点酸");
        order.addOrderItem(orderItem).addOrderItem(orderItem1);
        orderService.save(order);
        orderId = order.getId();
    }

    @Test
    public void testUpdateOrder() {
        orderService.changeOrderName("修改订单名字",orderId);
    }

    @Test
    public void testChangeOrderItemQty() {
        orderService.changeOrderItemQty("橘子",10,orderId);
        Order order = orderService.loadOrder(orderId);
        Assert.assertEquals(order.getDeliveryFee(),BigDecimal.valueOf(5));
    }

    @Test
    public void testCancelOrder() {
        orderService.cancel(orderId);
        Order order = orderService.loadOrder(orderId);
        Assert.assertEquals(OrderStatus.CANCEL,order.getOrderStatus());
        Assert.assertEquals(order.getOrderAmount(),BigDecimal.valueOf(25.75));
        Assert.assertEquals(order.getDeliveryFee(),BigDecimal.valueOf(5));
    }


}
