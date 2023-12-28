package org.ddd.fundamental.repository.order;

import org.aspectj.weaver.ast.Or;
import org.ddd.fundamental.repository.core.EntityModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Order extends EntityModel<Long> {

    /**
     * 订单名称
     */
    private String name;

    /**
     * 订单金额
     */
    private BigDecimal orderAmount;

    /**
     * 订单描述
     */
    private String description;

    public String getName() {
        return name;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public String getDescription() {
        return description;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    private List<OrderItem> orderItems = new ArrayList<>();

    private Order(String name, String description) {
        this(-1L);
        this.name = name;
        this.description = description;
        this.orderAmount = BigDecimal.ZERO;
    }

    public static Order create(String name, String description) {
        return new Order(name,description);
    }

    public Order addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        this.orderAmount = this.orderAmount.add(orderItem.getItemAmount()
                .multiply(BigDecimal.valueOf(orderItem.getQuantity())));
        return this;
    }

    public Order clear() {
        this.orderAmount = BigDecimal.ZERO;
        this.orderItems = new ArrayList<>();
        return this;
    }


    public Order addOrderItems(List<OrderItem> orderItems) {
        for (OrderItem orderItem: orderItems) {
            addOrderItem(orderItem);
        }
        return this;
    }


    public Order removeOrderItem(OrderItem orderItem) {
        this.orderItems.remove(orderItem);
        this.orderAmount = this.orderAmount.subtract(orderItem.getItemAmount()
                .multiply(BigDecimal.valueOf(orderItem.getQuantity())));
        return this;
    }


    public Order changeName(String name) {
        this.name = name;
        return this;
    }



    public Order(Long aLong) {
        super(aLong);
    }
}
