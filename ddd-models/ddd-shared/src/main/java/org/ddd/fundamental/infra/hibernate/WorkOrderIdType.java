package org.ddd.fundamental.infra.hibernate;

import org.ddd.fundamental.workorder.value.WorkOrderId;

public class WorkOrderIdType extends DomainObjectIdCustomType<WorkOrderId>{
    private static final DomainObjectIdTypeDescriptor<WorkOrderId> TYPE_DESCRIPTOR = new DomainObjectIdTypeDescriptor<>(
            WorkOrderId.class, WorkOrderId::new);

    public WorkOrderIdType() {
        super(TYPE_DESCRIPTOR);
    }
}
