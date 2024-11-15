package org.ddd.fundamental.infra.hibernate;

import org.ddd.fundamental.infra.hibernate.DomainObjectIdCustomType;
import org.ddd.fundamental.infra.hibernate.DomainObjectIdTypeDescriptor;
import org.ddd.fundamental.workprocess.value.CraftsmanShipId;

public class CraftsmanShipIdType extends DomainObjectIdCustomType<CraftsmanShipId> {
    private static final DomainObjectIdTypeDescriptor<CraftsmanShipId> TYPE_DESCRIPTOR = new DomainObjectIdTypeDescriptor<>(
            CraftsmanShipId.class, CraftsmanShipId::new);

    public CraftsmanShipIdType() {
        super(TYPE_DESCRIPTOR);
    }
}
