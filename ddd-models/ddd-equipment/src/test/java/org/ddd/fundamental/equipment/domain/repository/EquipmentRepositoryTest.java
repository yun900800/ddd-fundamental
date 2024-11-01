package org.ddd.fundamental.equipment.domain.repository;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.day.YearModelValueObject;
import org.ddd.fundamental.day.range.DateRange;
import org.ddd.fundamental.equipment.EquipmentAppTest;
import org.ddd.fundamental.equipment.domain.model.Equipment;
import org.ddd.fundamental.equipment.domain.model.EquipmentType;
import org.ddd.fundamental.equipment.value.*;
import org.ddd.fundamental.factory.EquipmentId;
import org.ddd.fundamental.utils.DateUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class EquipmentRepositoryTest extends EquipmentAppTest {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Test
    public void testCreateEquipment(){
        Equipment equipment = new Equipment(
                YearModelValueObject.createThreeShift("年度模型"),
                EquipmentType.RESOURCE_ONE, EquipmentMaster.newBuilder().assetNo("CQ_1024")
                .info(ChangeableInfo.create("车床一号","这是加工使用的车床"))
                .size(EquipmentSize.create(1000,1200,1500))
                .standard(MaintainStandard.create("需要三个月维修一次",new Date()))
                .personInfo(PersonInfo.create(2,"需要车床技工证书"))
                .qualityInfo(QualityInfo.create("验证已经通过","每年验证一次", true))
                .build()
        );
        equipmentRepository.save(equipment);
    }

    @Test
    public void testAddDateRange() {
        EquipmentId id = new EquipmentId("664304ca-0aa5-4d2c-af4a-b12f1c4da02f");
        Equipment equipment = equipmentRepository.findById(id).get();
        DateRange range = new DateRange(
                DateUtils.strToDate("2024-10-01 12:58:12","yyyy-MM-dd HH:mm:ss"),
                DateUtils.strToDate("2024-10-01 16:48:12","yyyy-MM-dd HH:mm:ss"),"机器维修");
        DateRange range1 = new DateRange(
                DateUtils.strToDate("2024-09-29 12:58:12","yyyy-MM-dd HH:mm:ss"),
                DateUtils.strToDate("2024-10-01 11:48:12","yyyy-MM-dd HH:mm:ss"),"工单排班不合理");
        equipment.addDateRange(range)
                .addDateRange(range1);
        equipmentRepository.save(equipment);
    }
}
