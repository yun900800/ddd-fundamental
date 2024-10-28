package org.ddd.fundamental.infra.hibernate;

import org.ddd.fundamental.factory.EquipmentId;

public class EquipmentIdType extends DomainObjectIdCustomType<EquipmentId> {

    private static final DomainObjectIdTypeDescriptor<EquipmentId> TYPE_DESCRIPTOR = new DomainObjectIdTypeDescriptor<>(
            EquipmentId.class, EquipmentId::new);

    public EquipmentIdType() {
        super(TYPE_DESCRIPTOR);
    }
}
