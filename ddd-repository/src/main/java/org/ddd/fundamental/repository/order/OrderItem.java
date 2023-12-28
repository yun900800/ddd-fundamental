package org.ddd.fundamental.repository.order;

import org.ddd.fundamental.repository.core.EntityModel;

import java.math.BigDecimal;
import java.util.Objects;

public class OrderItem extends EntityModel<Long> {

    private static final String DEFAULT_DESC = "这是好吃点为您推荐的优质商品";
    private static final int DEFAULT_QUANTITY = 1;


    /**
     * 订单项名称
     */
    private String name;

    /**
     * 单项的金额
     */
    private BigDecimal itemAmount;

    /**
     * 订单项目的数量
     */
    private int quantity;

    /**
     * 订单项描述说明
     */
    private String description;

    private OrderItem(String name, BigDecimal itemAmount, int quantity, String description) {
        this(-1L);
        this.description = description;
        this.name = name;
        this.itemAmount = itemAmount;
        this.quantity = quantity;
    }

    public static OrderItem create(String name, BigDecimal itemAmount) {
        return new OrderItem(name,itemAmount, DEFAULT_QUANTITY, DEFAULT_DESC);
    }

    public static OrderItem create(String name, BigDecimal itemAmount, int quantity, String description) {
        return new OrderItem(name,itemAmount, quantity, description);
    }

    public OrderItem(Long id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public BigDecimal getItemAmount() {
        return itemAmount;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return name.equals(orderItem.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
