package org.ddd.fundamental.infra.hibernate;

import org.ddd.fundamental.day.YearModelId;

public class YearModelIdType extends DomainObjectIdCustomType<YearModelId> {

    private static final DomainObjectIdTypeDescriptor<YearModelId> TYPE_DESCRIPTOR = new DomainObjectIdTypeDescriptor<>(
            YearModelId.class, YearModelId::new);

    public YearModelIdType() {
        super(TYPE_DESCRIPTOR);
    }
}
