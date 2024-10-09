package org.ddd.fundamental.invoice.domain;

import org.ddd.fundamental.core.domain.DomainObjectId;
import org.springframework.lang.NonNull;

public class InvoiceId extends DomainObjectId {
    public InvoiceId(@NonNull String uuid){
        super(uuid);
    }
}
