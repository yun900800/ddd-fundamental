package org.ddd.fundamental.material.domain.value;

import org.ddd.fundamental.core.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.Date;
import java.util.Objects;

/**
 * 生产属性,用于生成批次
 */
@Embeddable
@MappedSuperclass
public class ProductionProps implements ValueObject, Cloneable {

    /**
     * 生产设备名称
     */
    private String productEquipment;

    /**
     * 工单号
     */
    private String workOrderNo;

    /**
     * 生产日期
     */
    private Date productTime;

    /**
     * 失效日期
     */
    private Date invalidTime;

    @SuppressWarnings("unused")
    private ProductionProps(){}

    public ProductionProps(String productEquipment,
                           String workOrderNo,
                           Date productionTime,
                           Date invalidTime){
        this.productEquipment = productEquipment;
        this.workOrderNo = workOrderNo;
        this.invalidTime = invalidTime;
        this.productTime = productionTime;
    }

    public String getProductEquipment() {
        return productEquipment;
    }

    public String getWorkOrderNo() {
        return workOrderNo;
    }

    public Date getProductTime() {
        return productTime;
    }

    public Date getInvalidTime() {
        return invalidTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductionProps)) return false;
        ProductionProps that = (ProductionProps) o;
        return Objects.equals(productEquipment, that.productEquipment) && Objects.equals(workOrderNo, that.workOrderNo) && Objects.equals(productTime, that.productTime) && Objects.equals(invalidTime, that.invalidTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productEquipment, workOrderNo, productTime, invalidTime);
    }

    @Override
    public ProductionProps clone() {
        try {
            ProductionProps clone = (ProductionProps) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public String toString() {
        return "ProductionProps{" +
                "productEquipment='" + productEquipment + '\'' +
                ", workOrderNo='" + workOrderNo + '\'' +
                ", productTime=" + productTime +
                ", invalidTime=" + invalidTime +
                '}';
    }
}
