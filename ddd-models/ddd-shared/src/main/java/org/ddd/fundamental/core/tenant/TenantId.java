package org.ddd.fundamental.core.tenant;

import org.ddd.fundamental.core.DomainObjectId;

public class TenantId extends DomainObjectId {
    public TenantId(String uuid) {
        super(uuid);
    }
}
