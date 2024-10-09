package org.ddd.fundamental.invoice.infra.hibernate;

import org.ddd.fundamental.core.infra.hibernate.DomainObjectIdCustomType;
import org.ddd.fundamental.core.infra.hibernate.DomainObjectIdTypeDescriptor;
import org.ddd.fundamental.invoice.domain.InvoiceItemId;

public class InvoiceItemIdType extends DomainObjectIdCustomType<InvoiceItemId> {

    private static final DomainObjectIdTypeDescriptor<InvoiceItemId> TYPE_DESCRIPTOR =
            new DomainObjectIdTypeDescriptor<>(InvoiceItemId.class, InvoiceItemId::new);

    public InvoiceItemIdType() {
        super(TYPE_DESCRIPTOR);
    }
}
