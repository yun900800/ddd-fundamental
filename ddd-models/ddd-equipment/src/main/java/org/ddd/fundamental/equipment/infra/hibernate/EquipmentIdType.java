package org.ddd.fundamental.equipment.infra.hibernate;

import org.ddd.fundamental.factory.EquipmentId;
import org.ddd.fundamental.infra.hibernate.DomainObjectIdCustomType;
import org.ddd.fundamental.infra.hibernate.DomainObjectIdTypeDescriptor;

public class EquipmentIdType extends DomainObjectIdCustomType<EquipmentId> {
    private static final DomainObjectIdTypeDescriptor<EquipmentId> TYPE_DESCRIPTOR = new DomainObjectIdTypeDescriptor<>(
            EquipmentId.class, EquipmentId::new);

    public EquipmentIdType() {
        super(TYPE_DESCRIPTOR);
    }
}
