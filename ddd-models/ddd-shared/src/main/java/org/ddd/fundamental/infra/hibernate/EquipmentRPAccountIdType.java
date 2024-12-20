package org.ddd.fundamental.infra.hibernate;

import org.ddd.fundamental.equipment.value.EquipmentRPAccountId;
public class EquipmentRPAccountIdType extends DomainObjectIdCustomType<EquipmentRPAccountId> {

    private static final DomainObjectIdTypeDescriptor<EquipmentRPAccountId> TYPE_DESCRIPTOR = new DomainObjectIdTypeDescriptor<>(
            EquipmentRPAccountId.class, EquipmentRPAccountId::new);

    public EquipmentRPAccountIdType() {
        super(TYPE_DESCRIPTOR);
    }
}
