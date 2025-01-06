package org.ddd.fundamental.equipment.domain.model;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.day.YearModelValue;
import org.ddd.fundamental.day.range.DateRange;
import org.ddd.fundamental.equipment.enums.EquipmentType;
import org.ddd.fundamental.equipment.value.*;
import org.ddd.fundamental.utils.DateTimeUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class EquipmentTest {

    private static Equipment create(){
        return new Equipment(
                YearModelValue.createThreeShift("年度模型",2024),
                EquipmentType.RESOURCE_ONE, EquipmentMaster.newBuilder().assetNo("CQ_1024")
                .info(ChangeableInfo.create("车床一号","这是加工使用的车床"))
                .size(EquipmentSize.create(1000,1200,1500))
                .standard(MaintainStandard.create("需要三个月维修一次",new Date()))
                .personInfo(PersonInfo.create(2,"需要车床技工证书"))
                .qualityInfo(QualityInfo.create("验证已经通过","每年验证一次", true))
                .build()
        );
    }

    private static Equipment createTwoShiftEquipment(){
        return new Equipment(
                YearModelValue.createTwoShift("年度模型",2024),
                EquipmentType.RESOURCE_ONE, EquipmentMaster.newBuilder().assetNo("CQ_1024")
                .info(ChangeableInfo.create("车床一号","这是加工使用的车床"))
                .size(EquipmentSize.create(1000,1200,1500))
                .standard(MaintainStandard.create("需要三个月维修一次",new Date()))
                .personInfo(PersonInfo.create(2,"需要车床技工证书"))
                .qualityInfo(QualityInfo.create("验证已经通过","每年验证一次", true))
                .build()
        );
    }


    @Test
    public void testCreateEquipment() {
        Equipment equipment = create();
        System.out.println(equipment);
    }

    @Test
    public void testEquipmentMinutesOfOutWork(){
        Equipment equipment = create();
        Assert.assertEquals(equipment.minutesOfOutWork(),361,0);
        Assert.assertEquals(equipment.shiftMinutes(), 1079,0);

        Equipment equipment1 = createTwoShiftEquipment();
        Assert.assertEquals(equipment1.minutesOfOutWork(),720,0);
        Assert.assertEquals(equipment1.shiftMinutes(), 720,0);
    }

    @Test
    public void testEquipmentActualWasteMinutes(){
        Equipment equipment = create();
        DateRange range = new DateRange(
                DateTimeUtils.strToDate("2024-10-01 12:58:12","yyyy-MM-dd HH:mm:ss"),
                DateTimeUtils.strToDate("2024-10-01 16:48:12","yyyy-MM-dd HH:mm:ss"),"机器维修");
        DateRange range1 = new DateRange(
                DateTimeUtils.strToDate("2024-09-29 12:58:12","yyyy-MM-dd HH:mm:ss"),
                DateTimeUtils.strToDate("2024-10-01 11:48:12","yyyy-MM-dd HH:mm:ss"),"工单排班不合理");
        equipment.addDateRange(range)
                .addDateRange(range1);
        Assert.assertEquals(equipment.actualWasteMinutes(),2318);
    }
}
