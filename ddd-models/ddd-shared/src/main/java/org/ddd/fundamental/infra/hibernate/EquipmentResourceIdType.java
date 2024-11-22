package org.ddd.fundamental.infra.hibernate;

import org.ddd.fundamental.equipment.value.EquipmentResourceId;

public class EquipmentResourceIdType extends DomainObjectIdCustomType<EquipmentResourceId> {

    private static final DomainObjectIdTypeDescriptor<EquipmentResourceId> TYPE_DESCRIPTOR = new DomainObjectIdTypeDescriptor<>(
            EquipmentResourceId.class, EquipmentResourceId::new);

    public EquipmentResourceIdType() {
        super(TYPE_DESCRIPTOR);
    }
}
