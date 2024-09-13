package org.ddd.fundamental.repository.order;
import org.ddd.fundamental.repository.core.EntityModel;
import org.ddd.fundamental.repository.order.delivery.DefaultDeliveryStrategy;
import org.ddd.fundamental.repository.order.delivery.DeliveryStrategy;
import org.ddd.fundamental.repository.utils.BeanHelperUtils;
import org.ddd.fundamental.repository.utils.EntityModelUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        return BeanHelperUtils.deepCopy(orderItems) ;
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
        this.orderAmount = this.orderAmount.add(orderItem.itemPrice());
        this.deliveryFee = deliveryStrategy.generateDeliveryFee(orderAmount);
        this.updateDirty();
        return this;
    }

    public Order clear() {
        this.orderAmount = BigDecimal.ZERO;
        this.orderItems = new ArrayList<>();
        this.deliveryFee = BigDecimal.ZERO;
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
        this.orderAmount = this.orderAmount.subtract(orderItem.itemPrice());
        this.deliveryFee = deliveryStrategy.generateDeliveryFee(orderAmount);
        return this;
    }


    public Order changeName(String name) {
        this.name = name;
        this.updateDirty();
        return this;
    }

    public Order cancel() {
        this.orderStatus = OrderStatus.CANCEL;
        this.updateDirty();
        return this;
    }

    public Order deleteOrderItemByName(String name){
        List<OrderItem> orderItemList = markDeleteDirty(name);
        clear().addOrderItems(orderItemList);
        return this;
    }

    private List<OrderItem> markDirtyByFunc(Function<OrderItem,OrderItem> markDirtyFunc){
        return getOrderItems().stream().map(markDirtyFunc).collect(Collectors.toList());
    }

    public Order changeOrderItemQty(String name, int qty){
        this.orderItems = markUpdateDirty(name);
        List<OrderItem> orderItemList = changeOrderItemQty(qty,name);
        clear().addOrderItems(orderItemList);
        return this;
    }

    private List<OrderItem> changeOrderItemQty(int qty,String productName){
        Function<OrderItem,OrderItem> markDirtyFunc = v -> {
            if (v.getProductName().equals(productName)) {
                v.changeQuantity(qty);
            }
            return v;
        };
        return markDirtyByFunc(markDirtyFunc);
    }

    public List<OrderItem> markUpdateDirty(String name){
        return (List<OrderItem>) EntityModelUtils.markUpdateDirtyLists(orderItems, name);
    }

    public List<OrderItem> markDeleteDirty(String name){
        return (List<OrderItem>) EntityModelUtils.markDeleteDirtyLists(orderItems, name);
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
