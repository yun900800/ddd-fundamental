package org.ddd.fundamental.infra.hibernate;

import org.ddd.fundamental.infra.hibernate.DomainObjectIdCustomType;
import org.ddd.fundamental.infra.hibernate.DomainObjectIdTypeDescriptor;
import org.ddd.fundamental.workprocess.value.WorkProcessId;

public class WorkProcessIdType extends DomainObjectIdCustomType<WorkProcessId> {
    private static final DomainObjectIdTypeDescriptor<WorkProcessId> TYPE_DESCRIPTOR = new DomainObjectIdTypeDescriptor<>(
            WorkProcessId.class, WorkProcessId::new);

    public WorkProcessIdType() {
        super(TYPE_DESCRIPTOR);
    }
}
