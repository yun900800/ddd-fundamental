package org.ddd.fundamental.workprocess.value;

import org.ddd.fundamental.core.ValueObject;
import org.ddd.fundamental.workprocess.value.quantity.WorkProcessQuantity;
import org.ddd.fundamental.workprocess.value.resources.ProductResources;
import org.ddd.fundamental.workprocess.value.time.WorkProcessKeyTime;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@MappedSuperclass
@Embeddable
public class WorkProcessValue implements ValueObject, Cloneable {

    /**
     * 工序关键时间
     */
    private WorkProcessKeyTime workProcessKeyTime;

    /**
     * 工序数量
     */
    private WorkProcessQuantity workProcessQuantity;

    /**
     * 工序资源
     */
    private ProductResources productResources;

    /**
     * 工序模板id
     */
    private WorkProcessTemplateId templateId;

    @SuppressWarnings("unused")
    private WorkProcessValue(){}

    private WorkProcessValue(WorkProcessKeyTime workProcessKeyTime,
                             WorkProcessQuantity workProcessQuantity,
                             ProductResources productResources,
                             WorkProcessTemplateId templateId){
        this.productResources = productResources;
        this.workProcessKeyTime = workProcessKeyTime;
        this.workProcessQuantity = workProcessQuantity;
        this.templateId = templateId;
    }

    public static WorkProcessValue create(WorkProcessKeyTime workProcessKeyTime,
                                          WorkProcessQuantity workProcessQuantity,
                                          ProductResources productResources,
                                          WorkProcessTemplateId templateId){
        return new WorkProcessValue(workProcessKeyTime,workProcessQuantity,
                productResources,templateId);
    }

    public WorkProcessKeyTime getWorkProcessKeyTime() {
        return workProcessKeyTime.clone();
    }

    public WorkProcessQuantity getWorkProcessQuantity() {
        return workProcessQuantity.clone();
    }

    public ProductResources getProductResources() {
        return productResources;
    }

    public WorkProcessTemplateId getTemplateId() {
        return templateId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkProcessValue)) return false;
        WorkProcessValue that = (WorkProcessValue) o;
        return Objects.equals(workProcessKeyTime, that.workProcessKeyTime) && Objects.equals(workProcessQuantity, that.workProcessQuantity) && Objects.equals(productResources, that.productResources) && Objects.equals(templateId, that.templateId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workProcessKeyTime, workProcessQuantity, productResources, templateId);
    }

    @Override
    public String toString() {
        return "WorkProcessValue{" +
                "workProcessKeyTime=" + workProcessKeyTime +
                ", workProcessQuantity=" + workProcessQuantity +
                ", productResources=" + productResources +
                ", templateId=" + templateId +
                '}';
    }


    @Override
    public WorkProcessValue clone() {
        try {
            WorkProcessValue clone = (WorkProcessValue) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
