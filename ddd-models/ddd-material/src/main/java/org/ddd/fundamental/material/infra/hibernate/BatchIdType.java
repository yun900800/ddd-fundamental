package org.ddd.fundamental.material.infra.hibernate;

import org.ddd.fundamental.infra.hibernate.DomainObjectIdCustomType;
import org.ddd.fundamental.infra.hibernate.DomainObjectIdTypeDescriptor;
import org.ddd.fundamental.material.domain.value.BatchId;

public class BatchIdType extends DomainObjectIdCustomType<BatchId> {
    private static final DomainObjectIdTypeDescriptor<BatchId> TYPE_DESCRIPTOR = new DomainObjectIdTypeDescriptor<>(
            BatchId.class, BatchId::new);

    public BatchIdType() {
        super(TYPE_DESCRIPTOR);
    }
}
