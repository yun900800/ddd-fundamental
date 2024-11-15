package org.ddd.fundamental.workorder.value;

import org.ddd.fundamental.core.ValueObject;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.workorder.enums.WorkOrderType;
import org.ddd.fundamental.workprocess.value.CraftsmanShipId;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import java.time.Instant;
import java.util.Objects;

@MappedSuperclass
@Embeddable
public class WorkOrderValue implements ValueObject,Cloneable {

    @Enumerated(EnumType.STRING)
    private WorkOrderType workOrderType;

    /**
     * 产品id
     */
    private MaterialId productId;

    /**
     * 工艺id
     */
    private CraftsmanShipId craftsmanShipId;

    /**
     * 工单预计开始时间
     */
    private Instant startTime;

    /**
     * 工单预计结束时间
     */
    private Instant endTime;

    /**
     * 工单生产数量
     */
    private Double productQty;

    /**
     * 客户名字
     */
    private String customerName;

    @SuppressWarnings("unused")
    private WorkOrderValue(){}

    private WorkOrderValue(WorkOrderType workOrderType, MaterialId productId,
                          Instant startTime, Instant endTime,
                          Double productQty, String customerName,
                           CraftsmanShipId craftsmanShipId) {
        this.workOrderType = workOrderType;
        this.productId = productId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.productQty = productQty;
        this.customerName = customerName;
        this.craftsmanShipId = craftsmanShipId;
    }

    public static WorkOrderValue create(WorkOrderType workOrderType, MaterialId productId,
                                        Instant startTime, Instant endTime,
                                        Double productQty, String customerName,
                                        CraftsmanShipId craftsmanShipId){
        return new WorkOrderValue(workOrderType,
                productId,startTime,endTime,
                productQty,customerName,
                craftsmanShipId);
    }

    public WorkOrderType getWorkOrderType() {
        return workOrderType;
    }

    public MaterialId getProductId() {
        return productId;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public Double getProductQty() {
        return productQty;
    }

    public String getCustomerName() {
        return customerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkOrderValue)) return false;
        WorkOrderValue that = (WorkOrderValue) o;
        return workOrderType == that.workOrderType && Objects.equals(productId, that.productId) && Objects.equals(startTime, that.startTime) && Objects.equals(endTime, that.endTime) && Objects.equals(productQty, that.productQty) && Objects.equals(customerName, that.customerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workOrderType, productId, startTime, endTime, productQty, customerName);
    }

    @Override
    public String toString() {
        return "WorkOrderValue{" +
                "workOrderType=" + workOrderType +
                ", productId=" + productId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", productQty=" + productQty +
                ", customerName='" + customerName + '\'' +
                '}';
    }

    @Override
    public WorkOrderValue clone() {
        try {
            WorkOrderValue clone = (WorkOrderValue) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
