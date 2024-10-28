package org.ddd.fundamental.infra.hibernate;

import org.ddd.fundamental.factory.WorkStationId;

public class WorkStationIdType extends DomainObjectIdCustomType<WorkStationId> {

    private static final DomainObjectIdTypeDescriptor<WorkStationId> TYPE_DESCRIPTOR = new DomainObjectIdTypeDescriptor<>(
            WorkStationId.class, WorkStationId::new);

    public WorkStationIdType() {
        super(TYPE_DESCRIPTOR);
    }
}
