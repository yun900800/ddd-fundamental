package org.ddd.fundamental.repository.fundamental.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "tb_order_item")
public class OrderItemModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 订单项产品名称
     */
    private String productName;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * 订单项产品id
     */
    private Long productId;

    /**
     * 单项的金额
     */
    private BigDecimal itemAmount;

    /**
     * 订单项目的数量
     */
    private Integer quantity;

    private Long orderId;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * 订单项描述说明
     */
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(BigDecimal itemAmount) {
        this.itemAmount = itemAmount;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItemModel)) return false;
        OrderItemModel that = (OrderItemModel) o;
        return quantity == that.quantity && Objects.equals(id, that.id) && Objects.equals(productName, that.productName) && Objects.equals(itemAmount, that.itemAmount) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productName, itemAmount, quantity, description);
    }
}
