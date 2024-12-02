package org.ddd.fundamental.workprocess.value;

import org.ddd.fundamental.core.ValueObject;
import org.ddd.fundamental.workprocess.value.quantity.WorkProcessQuantity;
import org.ddd.fundamental.workprocess.value.resources.ProductResource;
import org.ddd.fundamental.workprocess.value.resources.ProductResources;
import org.ddd.fundamental.workprocess.value.time.WorkProcessKeyTime;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.time.Instant;
import java.util.Objects;

@MappedSuperclass
@Embeddable
public class WorkProcessValue implements ValueObject, Cloneable {

    /**
     * 工序关键时间
     */
    private WorkProcessKeyTime workProcessKeyTime;

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
                             ProductResources productResources,
                             WorkProcessTemplateId templateId){
        this.productResources = productResources;
        this.workProcessKeyTime = workProcessKeyTime;
        this.templateId = templateId;
    }

    public static WorkProcessValue create(WorkProcessKeyTime workProcessKeyTime,
                                          ProductResources productResources,
                                          WorkProcessTemplateId templateId){
        return new WorkProcessValue(workProcessKeyTime,
                productResources,templateId);
    }

    public WorkProcessValue addResource(ProductResource resource){
        this.productResources = this.productResources.addResource(resource);
        return this;
    }

    public WorkProcessValue removeResource(ProductResource resource){
        this.productResources = this.productResources.removeResource(resource);
        return this;
    }

    public WorkProcessValue start() {
        this.workProcessKeyTime = WorkProcessKeyTime.start();
        return this;
    }

    public WorkProcessValue start(Instant startTime) {
        this.workProcessKeyTime = WorkProcessKeyTime.start(startTime);
        return this;
    }

    public WorkProcessValue finish() {
        this.workProcessKeyTime = this.workProcessKeyTime.finish();
        return this;
    }

    public WorkProcessValue finish(Instant finishTime) {
        this.workProcessKeyTime = this.workProcessKeyTime.finish(finishTime);
        return this;
    }

    public WorkProcessValue interrupt(){
        this.workProcessKeyTime = this.workProcessKeyTime.interrupt();
        return this;
    }

    public WorkProcessValue interrupt(Instant interruptTime) {
        this.workProcessKeyTime = this.workProcessKeyTime.interrupt(interruptTime);
        return this;
    }

    public WorkProcessValue restart() {
        this.workProcessKeyTime = this.workProcessKeyTime.restart();
        return this;
    }

    public WorkProcessValue restart(Instant restartTime) {
        this.workProcessKeyTime = this.workProcessKeyTime.restart(restartTime);
        return this;
    }

    public WorkProcessKeyTime getWorkProcessKeyTime() {
        return workProcessKeyTime.clone();
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
        return Objects.equals(workProcessKeyTime, that.workProcessKeyTime) &&  Objects.equals(productResources, that.productResources) && Objects.equals(templateId, that.templateId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workProcessKeyTime, productResources, templateId);
    }

    @Override
    public String toString() {
        return "WorkProcessValue{" +
                "workProcessKeyTime=" + workProcessKeyTime +
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
