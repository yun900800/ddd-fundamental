package org.ddd.fundamental.factory;

import org.ddd.fundamental.core.DomainObjectId;

/**
 * 车间id
 */
public class MachineShopId extends DomainObjectId {
    public MachineShopId(String uuid) {
        super(uuid);
    }
}
