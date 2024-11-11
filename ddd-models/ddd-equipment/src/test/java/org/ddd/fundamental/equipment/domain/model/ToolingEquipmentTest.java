package org.ddd.fundamental.equipment.domain.model;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.equipment.value.MaintainStandard;
import org.ddd.fundamental.equipment.value.ToolingEquipmentValue;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class ToolingEquipmentTest {

    @Test
    public void testCreate(){
        ToolingEquipment toolingEquipment = new ToolingEquipment(ToolingEquipmentValue
                .create(ChangeableInfo.create("刮刀","这是刮刀设备"),
                        MaintainStandard.create("这个不需要检查",new Date())),
                "ASSET_GZ_GD_1","GZ_GD_1");
        System.out.println(toolingEquipment);
        Assert.assertEquals(toolingEquipment.getAssetCode(),"GZ_GD_1");
        Assert.assertEquals(toolingEquipment.getAssetNo(),"ASSET_GZ_GD_1");
    }
}
