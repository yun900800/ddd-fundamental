package org.ddd.fundamental.invoice.infra.hibernate;

import org.ddd.fundamental.core.infra.hibernate.DomainObjectIdCustomType;
import org.ddd.fundamental.core.infra.hibernate.DomainObjectIdTypeDescriptor;
import org.ddd.fundamental.invoice.domain.InvoiceId;

public class InvoiceIdType extends DomainObjectIdCustomType<InvoiceId> {

    private static final DomainObjectIdTypeDescriptor<InvoiceId> TYPE_DESCRIPTOR =
            new DomainObjectIdTypeDescriptor<>(InvoiceId.class, InvoiceId::new);

    public InvoiceIdType() {
        super(TYPE_DESCRIPTOR);
    }
}
