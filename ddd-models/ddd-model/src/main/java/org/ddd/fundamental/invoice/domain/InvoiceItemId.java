package org.ddd.fundamental.invoice.domain;

import org.ddd.fundamental.core.domain.DomainObjectId;
import org.springframework.lang.NonNull;

public class InvoiceItemId extends DomainObjectId {
    public InvoiceItemId(@NonNull String uuid){
        super(uuid);
    }
}
