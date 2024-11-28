package org.ddd.fundamental.infra.hibernate;

import org.ddd.fundamental.core.tenant.TenantId;

public class TenantIdType extends DomainObjectIdCustomType<TenantId>{
    private static final DomainObjectIdTypeDescriptor<TenantId> TYPE_DESCRIPTOR = new DomainObjectIdTypeDescriptor<>(
            TenantId.class, TenantId::new);

    public TenantIdType() {
        super(TYPE_DESCRIPTOR);
    }
}
