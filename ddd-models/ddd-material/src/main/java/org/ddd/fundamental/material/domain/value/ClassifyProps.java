package org.ddd.fundamental.material.domain.value;

import org.ddd.fundamental.core.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

/**
 * 分类属性
 */
@Embeddable
@MappedSuperclass
public class ClassifyProps implements ValueObject, Cloneable {

    private String erpBatchNo;

    private String productBatchNo;

    private String otherProps;

    @SuppressWarnings("unused")
    private ClassifyProps(){}

    private ClassifyProps(String erpBatchNo,
                          String productBatchNo,
                          String otherProps){
        this.erpBatchNo = erpBatchNo;
        this.productBatchNo = productBatchNo;
        this.otherProps = otherProps;
    }

    public String getErpBatchNo() {
        return erpBatchNo;
    }

    public String getProductBatchNo() {
        return productBatchNo;
    }

    public String getOtherProps() {
        return otherProps;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClassifyProps)) return false;
        ClassifyProps that = (ClassifyProps) o;
        return Objects.equals(erpBatchNo, that.erpBatchNo) && Objects.equals(productBatchNo, that.productBatchNo) && Objects.equals(otherProps, that.otherProps);
    }

    @Override
    public int hashCode() {
        return Objects.hash(erpBatchNo, productBatchNo, otherProps);
    }

    @Override
    public String toString() {
        return "ClassifyProps{" +
                "erpBatchNo='" + erpBatchNo + '\'' +
                ", productBatchNo='" + productBatchNo + '\'' +
                ", otherProps='" + otherProps + '\'' +
                '}';
    }

    @Override
    public ClassifyProps clone() {
        try {
            ClassifyProps clone = (ClassifyProps) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
