package org.ddd.fundamental.event.workprocess;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.core.DomainEvent;
import org.ddd.fundamental.event.core.BaseDomainEvent;
import org.ddd.fundamental.event.core.DomainEventType;
import org.ddd.fundamental.workorder.value.WorkOrderId;
import org.ddd.fundamental.workorder.value.WorkOrderValue;
import org.ddd.fundamental.workprocess.value.WorkProcessId;
import org.ddd.fundamental.workprocess.value.WorkProcessValue;
import org.ddd.fundamental.workprocess.value.resources.ProductResources;

import java.time.Instant;

/**
 * 注意,事件类一定要有get方法,否则在传递的时候可能会出现空值
 */
public class WorkProcessRecordCreated extends BaseDomainEvent<WorkProcessValue> implements DomainEvent {

    private ProductResources productResources;

    private WorkProcessId workProcessId;

    private Instant occurredOn;

    private WorkOrderId workOrderId;

    public WorkOrderValue getWorkOrderValue() {
        return workOrderValue;
    }

    private WorkOrderValue workOrderValue;

    protected WorkProcessRecordCreated(){
        super(DomainEventType.WORK_PROCESS,null);
    }
    protected WorkProcessRecordCreated(DomainEventType type, WorkProcessValue data, WorkOrderId workOrderId,
                                       ProductResources productResources, WorkProcessId workProcessId,WorkOrderValue workOrderValue) {
        super(type, data);
        this.workOrderId = workOrderId;
        this.workProcessId = workProcessId;
        this.productResources = productResources;
        this.occurredOn = Instant.now();
        this.workOrderValue = workOrderValue;
    }

    public static WorkProcessRecordCreated create(DomainEventType type, WorkProcessValue data, WorkOrderId workOrderId,
                                                  ProductResources productResources, WorkProcessId workProcessId, WorkOrderValue workOrderValue){
        return new WorkProcessRecordCreated(type,data, workOrderId, productResources,workProcessId,workOrderValue);
    }

    @Override
    public String toString() {
        return "WorkProcessRecordCreated{" +
                "productResources=" + productResources +
                ",data=" + this.getData() +
                ", workProcessId=" + workProcessId +
                ", occurredOn=" + occurredOn +
                ", workOrderId=" + workOrderId +
                ", workOrderValue=" + workOrderValue +
                '}';
    }

    public ProductResources getProductResources() {
        return productResources;
    }
    public WorkProcessId getWorkProcessId() {
        return workProcessId;
    }

    public WorkOrderId getWorkOrderId() {
        return workOrderId;
    }

    @Override
    protected Class<WorkProcessValue> clazzT() {
        return WorkProcessValue.class;
    }

    @Override
    protected Class<?> clazz() {
        return WorkProcessRecordCreated.class;
    }

    @Override
    public Instant occurredOn() {
        return occurredOn;
    }

    public Instant getOccurredOn() {
        return occurredOn;
    }
}
