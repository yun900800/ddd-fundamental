package org.ddd.fundamental.workprocess.domain.repository;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.day.range.DateRange;
import org.ddd.fundamental.factory.EquipmentId;
import org.ddd.fundamental.factory.WorkStationId;
import org.ddd.fundamental.utils.DateUtils;
import org.ddd.fundamental.workprocess.WorkProcessAppTest;
import org.ddd.fundamental.workprocess.application.WorkProcessCreator;
import org.ddd.fundamental.workprocess.domain.model.WorkProcessTemplate;
import org.ddd.fundamental.workprocess.domain.model.WorkProcessTemplateControlEntity;
import org.ddd.fundamental.workprocess.enums.BatchManagable;
import org.ddd.fundamental.workprocess.enums.ProductResourceType;
import org.ddd.fundamental.workprocess.value.WorkProcessTemplateId;
import org.ddd.fundamental.workprocess.value.controller.ReportWorkControl;
import org.ddd.fundamental.workprocess.value.controller.WorkProcessTemplateControl;
import org.ddd.fundamental.workprocess.value.quantity.WorkProcessTemplateQuantity;
import org.ddd.fundamental.workprocess.value.time.AuxiliaryWorkTime;
import org.ddd.fundamental.workprocess.value.resources.ProductResource;
import org.ddd.fundamental.workprocess.value.WorkProcessBeat;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class WorkProcessTemplateRepositoryTest extends WorkProcessAppTest {

    private static WorkProcessTemplate createTemplate(){
        WorkProcessTemplate workProcessTemplate = new WorkProcessTemplate(
                ChangeableInfo.create("主板加工工序","这是用来加工新能源车的主板的工序"),
//                create(),
                WorkProcessBeat.create(1000,15),
                WorkProcessCreator.createWorkProcessTemplateControl(),
                WorkProcessCreator.createWorkProcessTemplateQuantity()
        );

        EquipmentId id = EquipmentId.randomId(EquipmentId.class);
        ProductResource<EquipmentId> resource0 =  ProductResource.create(
                id,
                ProductResourceType.EQUIPMENT, ChangeableInfo.create("设备生产资源","这是一类设备生产资源")
        );


        WorkStationId stationId = WorkStationId.randomId(WorkStationId.class);
        ProductResource<EquipmentId> resource1 =  ProductResource.create(
                stationId,
                ProductResourceType.WORK_STATION, ChangeableInfo.create("工位生产资源","这是一类工位生产资源")
        );

        workProcessTemplate.addResource(resource0).addResource(resource1);
        return workProcessTemplate;
    }

    @Autowired
    private WorkProcessTemplateRepository workProcessTemplateRepository;
    private static AuxiliaryWorkTime create(){
        DateRange setTimeRange = new DateRange(
                DateUtils.strToDate("2024-10-01 09:30:12","yyyy-MM-dd HH:mm:ss"),
                DateUtils.strToDate("2024-10-01 09:55:12","yyyy-MM-dd HH:mm:ss"),"工序设置时间");
        DateRange offlineTimeRange = new DateRange(
                DateUtils.strToDate("2024-10-01 14:30:12","yyyy-MM-dd HH:mm:ss"),
                DateUtils.strToDate("2024-10-01 16:55:12","yyyy-MM-dd HH:mm:ss"),"工序下线时间");

        DateRange checkTimeRange = new DateRange(
                DateUtils.strToDate("2024-10-01 16:59:12","yyyy-MM-dd HH:mm:ss"),
                DateUtils.strToDate("2024-10-01 18:59:12","yyyy-MM-dd HH:mm:ss"),"工序下线时间");
        AuxiliaryWorkTime auxiliaryWorkTime = AuxiliaryWorkTime.create(setTimeRange,offlineTimeRange,checkTimeRange);
        return auxiliaryWorkTime;
    }

    @Test
    public void createWorkProcessTemplate() {
        WorkProcessTemplate workProcessTemplate = new WorkProcessTemplate(
                ChangeableInfo.create("主板加工工序","这是用来加工新能源车的主板的工序"),
//                create(),
                WorkProcessBeat.create(1000,15),
                WorkProcessCreator.createWorkProcessTemplateControl(),
                WorkProcessCreator.createWorkProcessTemplateQuantity()
        );

        EquipmentId id = EquipmentId.randomId(EquipmentId.class);
        ProductResource<EquipmentId> resource0 =  ProductResource.create(
                id,
                ProductResourceType.EQUIPMENT, ChangeableInfo.create("设备生产资源","这是一类设备生产资源")
        );


        WorkStationId stationId = WorkStationId.randomId(WorkStationId.class);
        ProductResource<EquipmentId> resource1 =  ProductResource.create(
                stationId,
                ProductResourceType.WORK_STATION, ChangeableInfo.create("工位生产资源","这是一类工位生产资源")
        );

        workProcessTemplate.addResource(resource0).addResource(resource1);
        workProcessTemplateRepository.save(workProcessTemplate);
    }

    @Test
    public void changeWorkProcessTemplate(){
        WorkProcessTemplate workProcessTemplate = new WorkProcessTemplate(
                ChangeableInfo.create("主板加工工序测试","这是用来加工新能源车的主板的工序1"),
//                create(),
                WorkProcessBeat.create(1000,15),
                WorkProcessCreator.createWorkProcessTemplateControl(),
                WorkProcessCreator.createWorkProcessTemplateQuantity()
        );

        EquipmentId id = EquipmentId.randomId(EquipmentId.class);
        ProductResource<EquipmentId> resource0 =  ProductResource.create(
                id,
                ProductResourceType.EQUIPMENT, ChangeableInfo.create("设备生产资源","这是一类设备生产资源")
        );

        WorkStationId stationId = WorkStationId.randomId(WorkStationId.class);
        ProductResource<EquipmentId> resource1 =  ProductResource.create(
                stationId,
                ProductResourceType.WORK_STATION, ChangeableInfo.create("工位生产资源","这是一类工位生产资源")
        );

        workProcessTemplate.addResource(resource0).addResource(resource1);
        workProcessTemplateRepository.save(workProcessTemplate);
        workProcessTemplate.changeName("主板加工工序测试修改").changeDesc("主板加工工序测试修改描述")
                .enableUse().changeWorkProcessBeat(WorkProcessBeat.create(1500,30));
        workProcessTemplateRepository.save(workProcessTemplate);
        Assert.assertEquals(workProcessTemplate.getWorkProcessInfo().getName(),"主板加工工序测试修改");
        Assert.assertEquals(workProcessTemplate.getWorkProcessInfo().getDesc(),"主板加工工序测试修改描述");
        Assert.assertEquals(workProcessTemplate.getWorkProcessInfo().isUse(),true);
        Assert.assertEquals(workProcessTemplate.getWorkProcessBeat(),WorkProcessBeat.create(1500,30));
    }

    @Test
    public void testChangeQuantity(){
        WorkProcessTemplate template = createTemplate();
        workProcessTemplateRepository.save(template);
        WorkProcessTemplateQuantity quantity = WorkProcessTemplateQuantity.newBuilder()
                .targetQualifiedRate(99.6)
                .transferPercent(1500).overCrossPercent(105.0).build();
        template.changeQuantity(quantity);
        workProcessTemplateRepository.save(template);
        Assert.assertEquals(template.getWorkProcessTemplateQuantity(),quantity);
    }

    @Test
    public void testChangeControl() {
        WorkProcessTemplate template = createTemplate();
        WorkProcessTemplateId id = template.id();
        workProcessTemplateRepository.save(template);
        WorkProcessTemplateControl control = new WorkProcessTemplateControl.Builder(
                2, BatchManagable.SERIAL
        ).canSplit(false).isAllowedChecked(true).nextProcessSyncMinutes(15)
                .build();
        template.changeWorkProcessTemplateControl(control);
        workProcessTemplateRepository.save(template);
        WorkProcessTemplate queryTemplate = workProcessTemplateRepository.getById(id);
        Assert.assertEquals(queryTemplate.getWorkProcessTemplateControl(),control);
    }

    @Test
    public void testChangeTemplateControl(){
        WorkProcessTemplate template = createTemplate();
        WorkProcessTemplateId id = template.id();
        workProcessTemplateRepository.save(template);
        template.enableSplit();
        template.allowChecked();
        template.changeName("测试修改名字哈哈");
        workProcessTemplateRepository.save(template);
        WorkProcessTemplate queryTemplate = workProcessTemplateRepository.getById(id);
        Assert.assertEquals(queryTemplate.getWorkProcessTemplateControl().getCanSplit(), true);
        Assert.assertEquals(queryTemplate.getWorkProcessTemplateControl().getAllowedChecked(), true);
        Assert.assertEquals(queryTemplate.getWorkProcessInfo().getName(), "测试修改名字哈哈");
    }

    @Test
    public void testAddControl() {
        WorkProcessTemplate template = createTemplate();
        template.setControl(WorkProcessTemplateControlEntity.create(
              new WorkProcessTemplateControl.Builder(5,BatchManagable.SERIAL).canSplit(true)
                      .reportWorkControl(ReportWorkControl.create(true,""))
                      .nextProcessSyncMinutes(15).build()
        ));
        workProcessTemplateRepository.save(template);
    }

    @Test
    public void testRemoveControl() {
        WorkProcessTemplate template = createTemplate();
        WorkProcessTemplateId id = template.id();
        template.setControl(WorkProcessTemplateControlEntity.create(
                new WorkProcessTemplateControl.Builder(5,BatchManagable.SERIAL).canSplit(true)
                        .reportWorkControl(ReportWorkControl.create(true,""))
                        .nextProcessSyncMinutes(15).build()
        ));
        workProcessTemplateRepository.save(template);
        template = workProcessTemplateRepository.findById(id).get();
        template.setControl(null);
        workProcessTemplateRepository.save(template);
    }
}
