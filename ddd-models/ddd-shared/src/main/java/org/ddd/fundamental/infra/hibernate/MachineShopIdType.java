package org.ddd.fundamental.infra.hibernate;

import org.ddd.fundamental.factory.MachineShopId;

public class MachineShopIdType extends DomainObjectIdCustomType<MachineShopId> {

    private static final DomainObjectIdTypeDescriptor<MachineShopId> TYPE_DESCRIPTOR = new DomainObjectIdTypeDescriptor<>(
            MachineShopId.class, MachineShopId::new);

    public MachineShopIdType() {
        super(TYPE_DESCRIPTOR);
    }
}
