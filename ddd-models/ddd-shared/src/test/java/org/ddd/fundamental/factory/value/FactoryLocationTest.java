package org.ddd.fundamental.factory.value;

import org.ddd.fundamental.factory.EquipmentId;
import org.ddd.fundamental.factory.MachineShopId;
import org.ddd.fundamental.factory.ProductionLineId;
import org.ddd.fundamental.factory.WorkStationId;
import org.junit.Assert;
import org.junit.Test;

public class FactoryLocationTest {
    @Test
    public void testCreateFactoryLocation(){
        FactoryLocation factoryLocation = FactoryLocation.newBuilder()
                .locationDesc("车间五产线3设备2")
                .machineShopId(MachineShopId.randomId(MachineShopId.class))
                .productionLineId(ProductionLineId.randomId(ProductionLineId.class))
                .equipmentId(EquipmentId.randomId(EquipmentId.class))
                .build();
        Assert.assertEquals(factoryLocation.getLocationDesc(),"车间五产线3设备2");
        Assert.assertNull(factoryLocation.getWorkStationId());

        FactoryLocation factoryLocation1 = FactoryLocation.newBuilder()
                .locationDesc("车间五产线3工位5")
                .machineShopId(MachineShopId.randomId(MachineShopId.class))
                .productionLineId(ProductionLineId.randomId(ProductionLineId.class))
                .workStationId(WorkStationId.randomId(WorkStationId.class))
                .build();
        Assert.assertEquals(factoryLocation1.getLocationDesc(),"车间五产线3工位5");
        Assert.assertNull(factoryLocation1.getEquipmentId());
    }
}
