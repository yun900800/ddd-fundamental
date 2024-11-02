package org.ddd.fundamental.infra.hibernate;

import org.ddd.fundamental.factory.ToolingEquipmentId;

public class ToolingEquipmentIdType extends DomainObjectIdCustomType<ToolingEquipmentId>{
    private static final DomainObjectIdTypeDescriptor<ToolingEquipmentId> TYPE_DESCRIPTOR = new DomainObjectIdTypeDescriptor<>(
            ToolingEquipmentId.class, ToolingEquipmentId::new);

    public ToolingEquipmentIdType() {
        super(TYPE_DESCRIPTOR);
    }
}
