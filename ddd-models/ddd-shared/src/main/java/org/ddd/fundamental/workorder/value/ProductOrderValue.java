package org.ddd.fundamental.workorder.value;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.core.ValueObject;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.workorder.enums.OrderStatus;
import org.ddd.fundamental.workorder.enums.Urgency;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

@Embeddable
@MappedSuperclass
@Slf4j
public class ProductOrderValue implements ValueObject, Cloneable {

    /**
     * 销售订单号
     */
    private OrderId saleOrderId;

    /**
     * 产品id
     */
    private MaterialId productId;

    /**
     * 产品编号
     */
    private String productCode;

    /**
     * 产品编号
     */
    private String productName;

    /**
     * 产品数量
     */
    private double qty;

    /**
     * 交货日期
     */
    private LocalDate deliveryDate;

    /**
     * 生产组织
     */
    private String organization;

    /**
     * 生产订单状态
     */
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    /**
     * 计划开始时间
     */
    private Instant planStartTime;

    /**
     * 计划结束时间
     */
    private Instant planFinishTime;

    /**
     * 紧急程度
     */
    @Enumerated(EnumType.STRING)
    private Urgency urgency;

    protected ProductOrderValue(){}

    private ProductOrderValue(OrderId saleOrderId,
                              MaterialId productId,
                              double qty,
                              String organization,
                              LocalDate deliveryDate,
                              String productName,
                              String productCode,
                              Instant planStartTime,
                              Instant planFinishTime
                              ){
        this.productId = productId;
        this.qty = qty;
        this.deliveryDate = deliveryDate;
        //上面的属性是必填的
        this.status = OrderStatus.INIT;
        this.urgency = Urgency.LEVEL_ONE;
        //这个属性是默认属性,在对象创建以后不能修改
        this.organization = organization;
        this.saleOrderId = saleOrderId;
        this.productName = productName;
        this.productCode = productCode;
        this.planStartTime = planStartTime;
        this.planFinishTime = planFinishTime;
    }

    public ProductOrderValue changeStatus(OrderStatus orderStatus){
        this.status = orderStatus;
        return this;
    }

    public ProductOrderValue changeUrgency(Urgency urgency){
        this.urgency = urgency;
        return this;
    }

    public ProductOrderValue configOrganization(String organization){
        this.organization = organization;
        return this;
    }

    public ProductOrderValue configSaleOrderId(OrderId saleOrderId){
        this.saleOrderId = saleOrderId;
        return this;
    }

    public ProductOrderValue changePlanStartTime(Instant planStartTime){
        this.planStartTime = planStartTime;
        return this;
    }

    public ProductOrderValue changePlanFinishTime(Instant planFinishTime){
        this.planFinishTime = planFinishTime;
        return this;
    }

    public OrderId getSaleOrderId() {
        return saleOrderId;
    }

    public MaterialId getProductId() {
        return productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getProductName() {
        return productName;
    }

    public double getQty() {
        return qty;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Instant getPlanStartTime() {
        return planStartTime;
    }

    public Instant getPlanFinishTime() {
        return planFinishTime;
    }

    public Urgency getUrgency() {
        return urgency;
    }

    public String getOrganization() {
        return organization;
    }

    @Override
    public ProductOrderValue clone() {
        try {
            ProductOrderValue clone = (ProductOrderValue) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public static class Builder {
        /**
         * 销售订单号
         */
        private OrderId saleOrderId;

        /**
         * 产品id
         */
        private final MaterialId productId;

        /**
         * 产品编号
         */
        private String productCode;

        /**
         * 产品编号
         */
        private String productName;

        /**
         * 产品数量
         */
        private final double qty;

        /**
         * 交货日期
         */
        private final LocalDate deliveryDate;


        /**
         * 计划开始时间
         */
        private Instant planStartTime;

        /**
         * 计划结束时间
         */
        private Instant planFinishTime;

        /**
         * 生产组织
         */
        private String organization;


        public Builder(MaterialId productId,
                       final double qty, LocalDate deliveryDate){
            this.productId = productId;
            this.qty = qty;
            this.deliveryDate = deliveryDate;
        }

        public Builder withSaleOrderId(OrderId saleOrderId){
            this.saleOrderId = saleOrderId;
            return this;
        }

        public Builder withProductCode(String productCode){
            this.productCode = productCode;
            return this;
        }

        public Builder withProductName(String productName){
            this.productName = productName;
            return this;
        }

        public Builder withPlanStartTime(Instant planStartTime){
            this.planStartTime = planStartTime;
            return this;
        }
        public Builder withPlanFinishTime(Instant planFinishTime){
            this.planFinishTime = planFinishTime;
            return this;
        }

        public Builder withOrganization(String organization){
            this.organization = organization;
            return this;
        }

        public ProductOrderValue build(){
            return new ProductOrderValue(
                    this.saleOrderId,
                    this.productId,
                    this.qty,
                    this.organization,
                    this.deliveryDate,
                    this.productName,
                    this.productCode,
                    this.planStartTime,
                    this.planFinishTime
            );
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductOrderValue)) return false;
        ProductOrderValue that = (ProductOrderValue) o;
        return Double.compare(that.qty, qty) == 0 && Objects.equals(productId, that.productId) && Objects.equals(deliveryDate, that.deliveryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, qty, deliveryDate);
    }

    @Override
    public String toString() {
        return "ProductOrderValue{" +
                "saleOrderId=" + saleOrderId +
                ", productId=" + productId +
                ", productCode='" + productCode + '\'' +
                ", productName='" + productName + '\'' +
                ", qty=" + qty +
                ", deliveryDate=" + deliveryDate +
                ", organization='" + organization + '\'' +
                ", status=" + status +
                ", planStartTime=" + planStartTime +
                ", planFinishTime=" + planFinishTime +
                ", urgency=" + urgency +
                '}';
    }
}
