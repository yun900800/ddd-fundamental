package org.ddd.fundamental.equipment.domain.repository;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.day.YearModelValue;
import org.ddd.fundamental.day.range.DateRange;
import org.ddd.fundamental.day.range.DateRangeValue;
import org.ddd.fundamental.equipment.EquipmentAppTest;
import org.ddd.fundamental.equipment.domain.model.Equipment;
import org.ddd.fundamental.equipment.domain.model.EquipmentResource;
import org.ddd.fundamental.equipment.domain.model.EquipmentType;
import org.ddd.fundamental.equipment.domain.model.ToolingEquipment;
import org.ddd.fundamental.equipment.value.*;
import org.ddd.fundamental.factory.EquipmentId;
import org.ddd.fundamental.utils.CollectionUtils;
import org.ddd.fundamental.utils.DateUtils;
import org.ddd.fundamental.workorder.value.WorkOrderId;
import org.ddd.fundamental.workprocess.enums.ProductResourceType;
import org.ddd.fundamental.workprocess.value.WorkProcessId;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class EquipmentRepositoryTest extends EquipmentAppTest {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private ToolingEquipmentRepository toolingRepository;

    @Autowired
    private EquipmentResourceRepository resourceRepository;

    @Test
    public void testCreateEquipment(){
        Equipment equipment = new Equipment(
                YearModelValue.createThreeShift("年度模型"),
                EquipmentType.RESOURCE_ONE, EquipmentMaster.newBuilder().assetNo("CQ_1024")
                .info(ChangeableInfo.create("车床一号","这是加工使用的车床"))
                .size(EquipmentSize.create(1000,1200,1500))
                .standard(MaintainStandard.create("需要三个月维修一次",new Date()))
                .personInfo(PersonInfo.create(2,"需要车床技工证书"))
                .qualityInfo(QualityInfo.create("验证已经通过","每年验证一次", true))
                .build()
        );
        EquipmentId id = equipment.id();
        EquipmentResource resource = EquipmentResource.create(EquipmentResourceValue.create(
                id, ProductResourceType.EQUIPMENT, ChangeableInfo.create("生产设备测试新增","这是一种生产设备用于测试新增")
        ));
        equipment.setResource(resource);
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
        equipmentRepository.save(randomEquipment);
        tooling1.enableUse();
        toolingRepository.save(tooling1);
        tooling2.enableUse();
        toolingRepository.save(tooling2);

    }


    @Test
    public void testChangeResource(){
        List<Equipment> equipments = equipmentRepository.findAll();
        Equipment equipment = CollectionUtils.random(equipments);
        EquipmentResource resource = equipment.getEquipmentResource();
        resource.getEquipmentResourceValue().addRange(
                EquipmentPlanRange.create(DateRangeValue.create(Instant.now(),Instant.now(),""),
                        WorkOrderId.randomId(WorkOrderId.class), WorkProcessId.randomId(WorkProcessId.class))
                );
        equipmentRepository.save(equipment);
        //resourceRepository.deleteById(id);

    }

    @Test
    public void testRemoveEquipment() {
        List<Equipment> equipments = equipmentRepository.findAll();
        Equipment equipment = CollectionUtils.random(equipments);
        EquipmentId id = equipment.id();
        equipmentRepository.deleteById(id);
    }

    @Test
    public void testQueryByName(){
        List<Equipment> equipments = equipmentRepository.queryByName("车床");
        Assert.assertTrue(equipments.size()==3 || equipments.size()==2);
        EquipmentResource equipmentResource = equipments.get(0).getEquipmentResource();
        Assert.assertEquals(0,equipmentResource.created(),0);
    }

    @Test
    public void testSetCurrentUseDateRange(){
        List<Equipment> equipments = equipmentRepository.findAll();
        Equipment equipment = CollectionUtils.random(equipments);
        equipment.getEquipmentResource().setCurrentUseDateRange(
                EquipmentPlanRange.create(DateRangeValue.createByDuration(Instant.now(),3600*4),
                        WorkOrderId.randomId(WorkOrderId.class), WorkProcessId.randomId(WorkProcessId.class))

        );
        equipmentRepository.save(equipment);
    }

    @Test
    public void testFinishUseRange() throws InterruptedException {
        List<Equipment> equipments = equipmentRepository.findAll();
        Equipment equipment = CollectionUtils.random(equipments);
        EquipmentId id = equipment.id();
        Instant start = Instant.now().minusSeconds(3600 * 4);
        equipment.getEquipmentResource().setCurrentUseDateRange(
                EquipmentPlanRange.create(DateRangeValue.createByDuration(start, 3600 * 2),
                        WorkOrderId.randomId(WorkOrderId.class), WorkProcessId.randomId(WorkProcessId.class))

        );
        equipmentRepository.save(equipment);
        TimeUnit.SECONDS.sleep(1);
        equipment = equipmentRepository.findById(id).get();
        equipment.getEquipmentResource().finishUseRange();
        equipmentRepository.save(equipment);
    }

    @Test
    public void testAddPlanDateRange() {
        List<Equipment> equipments = equipmentRepository.findAll();
        Equipment equipment = CollectionUtils.random(equipments);
        EquipmentId id = equipment.id();
        Instant t0 = Instant.now();
        Instant t1 = t0.plusSeconds(3600*2);
        Instant t2 = t1.plusSeconds(3600*3);
        Instant t3 = t2.plusSeconds(3600*4);
        equipment.getEquipmentResource().addPlanDateRange(EquipmentPlanRange.create(DateRangeValue.create(t0,t1,"这是为工单1准备的"),
                WorkOrderId.randomId(WorkOrderId.class), WorkProcessId.randomId(WorkProcessId.class)));
        equipment.getEquipmentResource().addPlanDateRange(EquipmentPlanRange.create(DateRangeValue.create(t1.plusSeconds(1),t2,"这是为工单2准备的"),
                WorkOrderId.randomId(WorkOrderId.class), WorkProcessId.randomId(WorkProcessId.class)));
        equipment.getEquipmentResource().addPlanDateRange(EquipmentPlanRange.create(DateRangeValue.create(t2.plusSeconds(1),t3,"这是为工单3准备的"),
                WorkOrderId.randomId(WorkOrderId.class), WorkProcessId.randomId(WorkProcessId.class)));
        equipmentRepository.save(equipment);
        equipment = equipmentRepository.findById(id).get();
        Assert.assertEquals(equipment.getEquipmentResource().getEquipmentResourceValue().getPlanRanges().size(),3);
    }

    @Test
    public void testRemovePlanDateRange() {
        List<Equipment> equipments = equipmentRepository.findAll();
        Equipment equipment = CollectionUtils.random(equipments);
        EquipmentId id = equipment.id();
        Instant t0 = Instant.now();
        Instant t1 = t0.plusSeconds(3600*2);
        Instant t2 = t1.plusSeconds(3600*3);
        Instant t3 = t2.plusSeconds(3600*4);
        equipment.getEquipmentResource().addPlanDateRange(

                EquipmentPlanRange.create(DateRangeValue.create(t0,t1,"这是为工单1准备的"),
                        WorkOrderId.randomId(WorkOrderId.class), WorkProcessId.randomId(WorkProcessId.class))
        );
        equipment.getEquipmentResource().addPlanDateRange(

                EquipmentPlanRange.create(DateRangeValue.create(t1.plusSeconds(1),t2,"这是为工单2准备的"),
                        WorkOrderId.randomId(WorkOrderId.class), WorkProcessId.randomId(WorkProcessId.class))
        );
        equipment.getEquipmentResource().addPlanDateRange(
                EquipmentPlanRange.create(DateRangeValue.create(t2.plusSeconds(1),t3,"这是为工单3准备的"),
                        WorkOrderId.randomId(WorkOrderId.class), WorkProcessId.randomId(WorkProcessId.class))

        );
        equipmentRepository.save(equipment);
        equipment = equipmentRepository.findById(id).get();
        List<EquipmentPlanRange> rangeValues = equipment.getEquipmentResource().getEquipmentResourceValue().getPlanRanges();
        equipment.getEquipmentResource().removePlanDateRange(rangeValues.get(0));
        equipmentRepository.save(equipment);
        equipment = equipmentRepository.findById(id).get();
        Assert.assertEquals(equipment.getEquipmentResource().getEquipmentResourceValue().getPlanRanges().size(),2);
    }
}
