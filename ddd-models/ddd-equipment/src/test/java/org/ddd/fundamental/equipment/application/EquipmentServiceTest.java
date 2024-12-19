package org.ddd.fundamental.equipment.application;

import org.ddd.fundamental.equipment.EquipmentAppTest;
import org.ddd.fundamental.equipment.application.command.EquipmentCommandService;
import org.ddd.fundamental.equipment.creator.EquipmentAddable;
import org.ddd.fundamental.equipment.creator.ToolingEquipmentAddable;
import org.ddd.fundamental.equipment.domain.model.Equipment;
import org.ddd.fundamental.equipment.domain.model.ToolingEquipment;
import org.ddd.fundamental.utils.CollectionUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class EquipmentServiceTest extends EquipmentAppTest {

    @Autowired
    private EquipmentCommandService equipmentService;

    @Autowired
    private ToolingEquipmentAddable creator;

    @Autowired
    private EquipmentAddable equipmentAddable;

    @Test
    public void testAddToolingToEquipment(){
        List<Equipment> equipmentList = equipmentAddable.getEquipments();
        List<ToolingEquipment> toolingEquipments = creator.getToolingEquipments();
        Equipment equipment = CollectionUtils.random(equipmentList);
        ToolingEquipment toolingEquipment = CollectionUtils.random(toolingEquipments);
        equipmentService.addToolingToEquipment(toolingEquipment.id(),
                equipment.id());
    }
}
