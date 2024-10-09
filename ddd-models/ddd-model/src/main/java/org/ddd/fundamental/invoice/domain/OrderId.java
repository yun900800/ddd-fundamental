package org.ddd.fundamental.invoice.domain;

import org.ddd.fundamental.core.domain.DomainObjectId;
import org.springframework.lang.NonNull;

public class OrderId extends DomainObjectId {
    public OrderId(@NonNull String uuid){
        super(uuid);
    }
}
