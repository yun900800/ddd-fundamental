package org.ddd.fundamental.workorder.infra.hibernate;

import org.ddd.fundamental.infra.hibernate.DomainObjectIdCustomType;
import org.ddd.fundamental.infra.hibernate.DomainObjectIdTypeDescriptor;
import org.ddd.fundamental.workorder.domain.value.YearModelId;

public class YearModelIdType extends DomainObjectIdCustomType<YearModelId> {
    private static final DomainObjectIdTypeDescriptor<YearModelId> TYPE_DESCRIPTOR = new DomainObjectIdTypeDescriptor<>(
            YearModelId.class, YearModelId::new);

    public YearModelIdType() {
        super(TYPE_DESCRIPTOR);
    }
}
