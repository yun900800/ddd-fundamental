package org.ddd.fundamental.event.workorder;

import org.ddd.fundamental.event.core.BaseDomainEvent;
import org.ddd.fundamental.event.core.DomainEventType;
import org.ddd.fundamental.workorder.value.WorkOrderValue;

public class WorkOrderEvent extends BaseDomainEvent<WorkOrderValue> {
    protected WorkOrderEvent(DomainEventType type, WorkOrderValue data) {
        super(type, data);
    }

    public static WorkOrderEvent create(DomainEventType type, WorkOrderValue data){
        return new WorkOrderEvent(type,data);
    }

    @Override
    protected Class<WorkOrderValue> clazzT() {
        return WorkOrderValue.class;
    }

    @Override
    protected Class<?> clazz() {
        return WorkOrderEvent.class;
    }
}
