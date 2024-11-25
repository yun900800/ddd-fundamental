package org.ddd.fundamental.event.equipment;

import org.ddd.fundamental.equipment.value.EquipmentMaster;
import org.ddd.fundamental.event.core.BaseDomainEvent;
import org.ddd.fundamental.event.core.DomainEventType;

public class EquipmentMasterEvent extends BaseDomainEvent<EquipmentMaster> {

    protected EquipmentMasterEvent(DomainEventType type, EquipmentMaster data) {
        super(type, data);
    }

    @Override
    protected Class<EquipmentMaster> clazzT() {
        return EquipmentMaster.class;
    }

    @Override
    protected Class<?> clazz() {
        return EquipmentMasterEvent.class;
    }
}
