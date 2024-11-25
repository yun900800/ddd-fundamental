package org.ddd.fundamental.event.equipment;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.equipment.value.EquipmentMaster;
import org.ddd.fundamental.equipment.value.EquipmentSize;
import org.ddd.fundamental.event.core.DomainEventType;
import org.junit.Test;

public class EquipmentMasterEventTest {

    @Test
    public void testCreateEquipmentMasterEvent(){
        EquipmentMasterEvent event = new EquipmentMasterEvent(
                DomainEventType.EQUIPMENT, EquipmentMaster.newBuilder()
                .assetNo("15x9").info(ChangeableInfo.create("hello","nice"))
                .size(EquipmentSize.create(15.0,28,29))
                .noStandard().build()
        );
        System.out.println(event);
    }
}
