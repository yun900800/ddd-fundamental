package org.ddd.fundamental.material.infra.hibernate;

import org.ddd.fundamental.infra.hibernate.DomainObjectIdCustomType;
import org.ddd.fundamental.infra.hibernate.DomainObjectIdTypeDescriptor;
import org.ddd.fundamental.material.domain.model.MaterialId;

public class MaterialIdType extends DomainObjectIdCustomType<MaterialId> {

    private static final DomainObjectIdTypeDescriptor<MaterialId> TYPE_DESCRIPTOR = new DomainObjectIdTypeDescriptor<>(
            MaterialId.class, MaterialId::new);

    public MaterialIdType() {
        super(TYPE_DESCRIPTOR);
    }
}
