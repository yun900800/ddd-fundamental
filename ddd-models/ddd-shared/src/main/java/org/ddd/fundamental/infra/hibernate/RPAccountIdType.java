package org.ddd.fundamental.infra.hibernate;

import org.ddd.fundamental.equipment.value.RPAccountId;

public class RPAccountIdType extends DomainObjectIdCustomType<RPAccountId>{
    private static final DomainObjectIdTypeDescriptor<RPAccountId> TYPE_DESCRIPTOR = new DomainObjectIdTypeDescriptor<>(
            RPAccountId.class, RPAccountId::new);

    public RPAccountIdType() {
        super(TYPE_DESCRIPTOR);
    }
}
