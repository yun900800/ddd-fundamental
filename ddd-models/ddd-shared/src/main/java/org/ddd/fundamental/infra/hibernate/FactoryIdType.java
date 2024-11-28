package org.ddd.fundamental.infra.hibernate;

import org.ddd.fundamental.core.tenant.FactoryId;

public class FactoryIdType extends DomainObjectIdCustomType<FactoryId>{
    private static final DomainObjectIdTypeDescriptor<FactoryId> TYPE_DESCRIPTOR = new DomainObjectIdTypeDescriptor<>(
            FactoryId.class, FactoryId::new);

    public FactoryIdType() {
        super(TYPE_DESCRIPTOR);
    }
}
