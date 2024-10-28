package org.ddd.fundamental.infra.hibernate;

import org.ddd.fundamental.material.value.MaterialRecordId;

public class MaterialRecordIdType extends DomainObjectIdCustomType<MaterialRecordId> {

    private static final DomainObjectIdTypeDescriptor<MaterialRecordId> TYPE_DESCRIPTOR = new DomainObjectIdTypeDescriptor<>(
            MaterialRecordId.class, MaterialRecordId::new);

    public MaterialRecordIdType() {
        super(TYPE_DESCRIPTOR);
    }
}
