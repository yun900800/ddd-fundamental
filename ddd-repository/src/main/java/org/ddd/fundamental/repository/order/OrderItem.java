package org.ddd.fundamental.repository.order;

import org.ddd.fundamental.repository.core.EntityModel;
import org.ddd.fundamental.repository.utils.MapUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class OrderItem extends EntityModel<Long> {

    private static Map<Long,String> products;
    private static Map<String,Long> reversedProducts;

    static {
        products = Map.of(1L,"香蕉",2L,"橘子",
                3L,"苹果", 4L,"库尔勒香梨");
        reversedProducts = MapUtils.reverseMap(products);
    }

    private static final String DEFAULT_DESC = "这是好吃点为您推荐的优质商品";
    private static final int DEFAULT_QUANTITY = 1;

    /**
     * 订单项产品名称
     */
    private String productName;

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

    /**
     * 订单项对应的产品Id
     */
    private Long productId;

    private OrderItem(String productName, BigDecimal itemAmount, int quantity, String description) {
        this(-1L);
        this.description = description;
        this.productName = productName;
        this.productId = reversedProducts.get(this.productName);
        this.itemAmount = itemAmount;
        this.quantity = quantity;
    }

    public static OrderItem create(String productName, BigDecimal itemAmount) {
        return new OrderItem(productName,itemAmount, DEFAULT_QUANTITY, DEFAULT_DESC);
    }

    public static OrderItem create(String productName, BigDecimal itemAmount, int quantity, String description) {
        return new OrderItem(productName,itemAmount, quantity, description);
    }

    public OrderItem(Long id) {
        super(id);
    }

    public OrderItem changeQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getProductName() {
        return productName;
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

    public Long getProductId() {
        return productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return Objects.equals(productName,orderItem.productName)
                && Objects.equals(productId, orderItem.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, productId);
    }
}
