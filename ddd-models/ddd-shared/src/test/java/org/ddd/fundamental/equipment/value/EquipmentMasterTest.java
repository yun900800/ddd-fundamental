package org.ddd.fundamental.equipment.value;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.utils.DateTimeUtils;
import org.junit.Assert;
import org.junit.Test;

public class EquipmentMasterTest {
    @Test
    public void testCreateEquipmentMaster() {
        EquipmentMaster equipmentMaster = EquipmentMaster.newBuilder()
                .assetNo("asset2085")
                .info(ChangeableInfo.create("飞利浦车床","描述一下"))
                .size(EquipmentSize.create(2.5,3.6,2.0))
                .standard(MaintainStandard.create("需要人工维护", DateTimeUtils.strToDate("2024-10-12","yyyy-MM-dd")))
                .personInfo(PersonInfo.create(2,"车床资质证书"))
                .qualityInfo(QualityInfo.create("验证未通过","需要三方检测计划",false))
                .build();
        Assert.assertEquals("EquipmentMaster{assetNo='asset2085', info='飞利浦车床, 描述一下, false', size=EquipmentSize{width=2.5, length=3.6, height=2.0}, standard=MaintainStandard{maintainRule='需要人工维护', workTime=Sat Oct 12 00:00:00 CST 2024}, personInfo=PersonInfo{personCount=2, qualification='车床资质证书'}, qualityInfo=QualityInfo{checkStatus='验证未通过', checkPlan='需要三方检测计划', checkResult=false}}",
                equipmentMaster.toString());
    }

    @Test
    public void testCreateStepEquipmentMaster() {
        EquipmentMaster equipmentMaster = EquipmentMaster.newBuilder()
                .assetNo("asset2085")
                .info(ChangeableInfo.create("飞利浦车床","描述一下"))
                .size(EquipmentSize.create(2.5,3.6,2.0))
                .noStandard().build();
        Assert.assertNull(equipmentMaster.getPersonInfo());
        Assert.assertNull(equipmentMaster.getQualityInfo());
        Assert.assertNull(equipmentMaster.getStandard());

        EquipmentMaster equipmentMaster1 = EquipmentMaster.newBuilder()
                .assetNo("asset2085")
                .info(ChangeableInfo.create("飞利浦车床","描述一下"))
                .size(EquipmentSize.create(2.5,3.6,2.0))
                .noStandardWithPerson()
                .personInfo(PersonInfo.create(2,"车床资质证书"))
                .noQualityInfo().build();

        Assert.assertNull(equipmentMaster1.getQualityInfo());
        Assert.assertNull(equipmentMaster1.getStandard());

    }
}
