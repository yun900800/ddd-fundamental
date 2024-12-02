package org.ddd.fundamental.equipment.domain.repository;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.equipment.EquipmentAppTest;
import org.ddd.fundamental.equipment.application.ToolingEquipmentCreator;
import org.ddd.fundamental.equipment.creator.ToolingEquipmentAddable;
import org.ddd.fundamental.equipment.domain.model.ToolingEquipment;
import org.ddd.fundamental.equipment.enums.ToolingType;
import org.ddd.fundamental.equipment.value.MaintainStandard;
import org.ddd.fundamental.equipment.value.ToolingEquipmentValue;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class ToolingEquipmentRepositoryTest extends EquipmentAppTest {

    @Autowired
    private ToolingEquipmentRepository repository;

    @Test
    public void testCreateToolingEquipment(){
        ToolingEquipment toolingEquipment = new ToolingEquipment(ToolingEquipmentValue
                .create(ChangeableInfo.create("加工中心","这是刮刀设备"),
                        MaintainStandard.create("这个不需要检查",new Date()),
                        ToolingType.SB,"VMC-850"),
                "ASSET_GZ_GD_1","GZ_GD_1");

        repository.save(toolingEquipment);
        repository.saveAll(ToolingEquipmentAddable.createToolingList());
    }

    @Test
    public void testBatchCreateToolingEquipments(){
        repository.saveAll(ToolingEquipmentAddable.createToolingList());
    }
}
