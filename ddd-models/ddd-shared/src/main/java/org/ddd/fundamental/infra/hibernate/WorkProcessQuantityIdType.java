package org.ddd.fundamental.infra.hibernate;

import org.ddd.fundamental.workprocess.value.quantity.WorkProcessQuantityId;

public class WorkProcessQuantityIdType extends DomainObjectIdCustomType<WorkProcessQuantityId>{
    private static final DomainObjectIdTypeDescriptor<WorkProcessQuantityId> TYPE_DESCRIPTOR = new DomainObjectIdTypeDescriptor<>(
            WorkProcessQuantityId.class, WorkProcessQuantityId::new);

    public WorkProcessQuantityIdType() {
        super(TYPE_DESCRIPTOR);
    }
}
