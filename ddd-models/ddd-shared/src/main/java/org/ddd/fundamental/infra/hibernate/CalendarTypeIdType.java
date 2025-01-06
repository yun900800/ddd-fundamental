package org.ddd.fundamental.infra.hibernate;


import org.ddd.fundamental.day.CalendarTypeId;

public class CalendarTypeIdType extends DomainObjectIdCustomType<CalendarTypeId> {
    private static final DomainObjectIdTypeDescriptor<CalendarTypeId> TYPE_DESCRIPTOR = new DomainObjectIdTypeDescriptor<>(
            CalendarTypeId.class, CalendarTypeId::new);

    public CalendarTypeIdType() {
        super(TYPE_DESCRIPTOR);
    }
}
