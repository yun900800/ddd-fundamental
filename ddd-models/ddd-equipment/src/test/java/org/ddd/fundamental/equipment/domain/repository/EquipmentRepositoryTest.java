package org.ddd.fundamental.equipment.domain.repository;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.day.YearModelValueObject;
import org.ddd.fundamental.day.range.DateRange;
import org.ddd.fundamental.equipment.EquipmentAppTest;
import org.ddd.fundamental.equipment.domain.model.Equipment;
import org.ddd.fundamental.equipment.domain.model.EquipmentType;
import org.ddd.fundamental.equipment.domain.model.ToolingEquipment;
import org.ddd.fundamental.equipment.value.*;
import org.ddd.fundamental.factory.EquipmentId;
import org.ddd.fundamental.utils.CollectionUtils;
import org.ddd.fundamental.utils.DateUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public class EquipmentRepositoryTest extends EquipmentAppTest {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private ToolingEquipmentRepository toolingRepository;

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
    //@Transactional// 避免延迟加载出错
    public void testAddDateRange() {
        List<Equipment> equipmentList = equipmentRepository.findAll();
        Equipment randomEquipment = CollectionUtils.random(equipmentList);
        EquipmentId id = randomEquipment.id();
        Equipment equipment = equipmentRepository.findById(id).get();
        DateRange range = new DateRange(
                DateUtils.strToDate("2024-10-01 12:58:12","yyyy-MM-dd HH:mm:ss"),
                DateUtils.strToDate("2024-10-01 16:48:12","yyyy-MM-dd HH:mm:ss"),"机器维修");
        DateRange range1 = new DateRange(
                DateUtils.strToDate("2024-09-29 12:58:12","yyyy-MM-dd HH:mm:ss"),
                DateUtils.strToDate("2024-10-01 11:48:12","yyyy-MM-dd HH:mm:ss"),"工单排班不合理");
        DateRange range2 = new DateRange(
                DateUtils.strToDate("2024-09-24 12:58:12","yyyy-MM-dd HH:mm:ss"),
                DateUtils.strToDate("2024-10-25 11:48:12","yyyy-MM-dd HH:mm:ss"),"人员操作失误");
        DateRange range3 = new DateRange(
                DateUtils.strToDate("2024-09-23 12:58:12","yyyy-MM-dd HH:mm:ss"),
                DateUtils.strToDate("2024-10-24 11:48:12","yyyy-MM-dd HH:mm:ss"),"机器技术故障");
        equipment.addDateRange(range)
                .addDateRange(range1)
                .addDateRange(range2)
                .addDateRange(range3);
        equipmentRepository.save(equipment);
    }

    @Test
    public void testRemoveDateRange(){
        List<Equipment> equipmentList = equipmentRepository.findAll();
        Equipment randomEquipment = CollectionUtils.random(equipmentList);
        EquipmentId id = randomEquipment.id();
        Equipment equipment = equipmentRepository.findById(id).get();
        DateRange range = new DateRange(
                DateUtils.strToDate("2024-10-01 12:58:12","yyyy-MM-dd HH:mm:ss"),
                DateUtils.strToDate("2024-10-01 16:48:12","yyyy-MM-dd HH:mm:ss"),"机器维修");
        equipment.removeDateRange(range);
        equipmentRepository.save(equipment);
    }

    @Test
    public void testAddToolingToEquipment(){
        List<ToolingEquipment> toolingEquipments = toolingRepository.findAll();
        List<Equipment> equipments = equipmentRepository.findAll();
        Equipment randomEquipment = CollectionUtils.random(equipments);

        ToolingEquipment tooling1 = CollectionUtils.random(toolingEquipments);
        ToolingEquipment tooling2 = CollectionUtils.random(toolingEquipments);
        randomEquipment.addToolingId(tooling1.id()).addToolingId(tooling2.id());
        equipmentRepository.save(randomEquipment);
        tooling1.enableUse();
        toolingRepository.save(tooling1);
        tooling2.enableUse();
        toolingRepository.save(tooling2);

    }

}
