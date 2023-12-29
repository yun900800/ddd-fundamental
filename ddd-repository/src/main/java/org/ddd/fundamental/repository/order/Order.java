package org.ddd.fundamental.repository.order;
import org.ddd.fundamental.repository.core.EntityModel;
import org.ddd.fundamental.repository.order.delivery.DefaultDeliveryStrategy;
import org.ddd.fundamental.repository.order.delivery.DeliveryStrategy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Order extends EntityModel<Long> {

    private static final BigDecimal defaultDeliveryFee = BigDecimal.TEN;

    /**
     * 订单名称
     */
    private String name;

    /**
     * 订单金额
     */
    private BigDecimal orderAmount;

    /**
     * 配送费
     */
    private BigDecimal deliveryFee;

    /**
     * 订单描述
     */
    private String description;

    /**
     * 订单状态
     */
    private OrderStatus orderStatus;

    private DeliveryStrategy deliveryStrategy = new DefaultDeliveryStrategy();

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
        this.orderStatus = OrderStatus.NEW;
        this.deliveryFee = defaultDeliveryFee;
    }

    private Order(String name, String description,String orderStatus) {
        this(name,description);
        this.orderStatus = OrderStatus.fromStatus(orderStatus);
    }

    public static Order create(String name, String description) {
        return new Order(name,description);
    }

    public static Order load(String name, String description,String orderStatus) {
        return new Order(name,description,orderStatus);
    }

    public Order addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        this.orderAmount = this.orderAmount.add(orderItem.getItemAmount()
                .multiply(BigDecimal.valueOf(orderItem.getQuantity())));
        this.deliveryFee = deliveryStrategy.generateDeliveryFee(orderAmount);
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
        this.deliveryFee = deliveryStrategy.generateDeliveryFee(orderAmount);
        return this;
    }


    public Order changeName(String name) {
        this.name = name;
        return this;
    }

    public Order cancel() {
        this.orderStatus = OrderStatus.CANCEL;
        return this;
    }

    /**
     * 改变计费策略
     * @param strategy
     * @return
     */
    public Order changeDeliveryStrategy(DeliveryStrategy strategy) {
        this.deliveryStrategy = strategy;
        return this;
    }
    public BigDecimal getDeliveryFee() {
        return deliveryFee;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public Order(Long aLong) {
        super(aLong);
    }
}
