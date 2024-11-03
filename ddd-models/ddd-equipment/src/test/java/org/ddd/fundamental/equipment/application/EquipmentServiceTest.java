package org.ddd.fundamental.equipment.application;

import org.ddd.fundamental.equipment.EquipmentAppTest;
import org.ddd.fundamental.equipment.domain.model.Equipment;
import org.ddd.fundamental.equipment.domain.model.ToolingEquipment;
import org.ddd.fundamental.utils.CollectionUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class EquipmentServiceTest extends EquipmentAppTest {

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private ToolingEquipmentCreator creator;

    @Test
    public void testAddToolingToEquipment(){
        List<Equipment> equipmentList = creator.getEquipments();
        List<ToolingEquipment> toolingEquipments = creator.getToolingEquipments();
        Equipment equipment = CollectionUtils.random(equipmentList);
        ToolingEquipment toolingEquipment = CollectionUtils.random(toolingEquipments);
        equipmentService.addToolingToEquipment(toolingEquipment.id(),
                equipment.id());
    }
}
