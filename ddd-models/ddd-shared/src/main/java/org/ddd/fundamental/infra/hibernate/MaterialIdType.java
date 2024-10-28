package org.ddd.fundamental.infra.hibernate;

import org.ddd.fundamental.material.value.MaterialId;

public class MaterialIdType extends DomainObjectIdCustomType<MaterialId> {

    private static final DomainObjectIdTypeDescriptor<MaterialId> TYPE_DESCRIPTOR = new DomainObjectIdTypeDescriptor<>(
            MaterialId.class, MaterialId::new);

    public MaterialIdType() {
        super(TYPE_DESCRIPTOR);
    }
}
