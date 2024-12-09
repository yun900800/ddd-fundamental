package org.ddd.fundamental.event.workorder;

import org.ddd.fundamental.event.core.BaseDomainEvent;
import org.ddd.fundamental.event.core.DomainEventType;
import org.ddd.fundamental.workorder.value.WorkOrderId;
import org.ddd.fundamental.workorder.value.WorkOrderValue;

public class WorkOrderEvent extends BaseDomainEvent<WorkOrderValue> {

    private WorkOrderId workOrderId;

    protected WorkOrderEvent(){
        super(DomainEventType.EQUIPMENT,null);
    }
    protected WorkOrderEvent(DomainEventType type, WorkOrderValue data, WorkOrderId workOrderId) {
        super(type, data);
        this.workOrderId = workOrderId;
    }

    public static WorkOrderEvent create(DomainEventType type, WorkOrderValue data,
                                        WorkOrderId workOrderId){
        return new WorkOrderEvent(type,data,workOrderId);
    }

    @Override
    protected Class<WorkOrderValue> clazzT() {
        return WorkOrderValue.class;
    }

    @Override
    protected Class<?> clazz() {
        return WorkOrderEvent.class;
    }

    public WorkOrderId getWorkOrderId() {
        return workOrderId;
    }
}
