package org.ddd.fundamental.infra.hibernate;

import org.ddd.fundamental.schedule.value.ScheduleId;

public class ScheduleIdType extends DomainObjectIdCustomType<ScheduleId>{
    private static final DomainObjectIdTypeDescriptor<ScheduleId> TYPE_DESCRIPTOR = new DomainObjectIdTypeDescriptor<>(
            ScheduleId.class, ScheduleId::new);

    public ScheduleIdType() {
        super(TYPE_DESCRIPTOR);
    }
}