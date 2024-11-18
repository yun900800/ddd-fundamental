package org.ddd.fundamental.workprocess.application;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.core.generator.Generators;
import org.ddd.fundamental.day.range.DateRange;
import org.ddd.fundamental.factory.EquipmentId;
import org.ddd.fundamental.factory.ToolingEquipmentId;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.shared.api.equipment.EquipmentDTO;
import org.ddd.fundamental.shared.api.equipment.ToolingDTO;
import org.ddd.fundamental.shared.api.material.MaterialDTO;
import org.ddd.fundamental.material.value.MaterialType;
import org.ddd.fundamental.utils.CollectionUtils;
import org.ddd.fundamental.utils.DateUtils;
import org.ddd.fundamental.workprocess.client.EquipmentClient;
import org.ddd.fundamental.workprocess.client.MaterialClient;
import org.ddd.fundamental.workprocess.domain.model.CraftsmanShipTemplate;
import org.ddd.fundamental.workprocess.enums.ProductResourceType;
import org.ddd.fundamental.workprocess.value.WorkProcessTemplateId;
import org.ddd.fundamental.workprocess.value.controller.GapRangeControl;
import org.ddd.fundamental.workprocess.value.controller.ReportWorkControl;
import org.ddd.fundamental.workprocess.value.controller.WorkOrderControl;
import org.ddd.fundamental.workprocess.value.controller.WorkProcessTemplateControl;
import org.ddd.fundamental.workprocess.value.quantity.WorkProcessTemplateQuantity;
import org.ddd.fundamental.workprocess.value.resources.ProductResource;
import org.ddd.fundamental.workprocess.value.resources.ProductResources;
import org.ddd.fundamental.workprocess.value.time.AuxiliaryWorkTime;
import org.ddd.fundamental.workprocess.value.WorkProcessBeat;
import org.ddd.fundamental.workprocess.domain.model.WorkProcessTemplate;
import org.ddd.fundamental.workprocess.domain.repository.CraftsmanShipRepository;
import org.ddd.fundamental.workprocess.domain.repository.WorkProcessTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class WorkProcessCreator {

    private List<WorkProcessTemplate> workProcessList;

    @Autowired
    private WorkProcessTemplateRepository templateRepository;

    @Autowired
    private CraftsmanShipRepository craftsmanShipRepository;

    @Autowired
    private MaterialClient client;

    @Autowired
    private EquipmentClient equipmentClient;

    private List<EquipmentId> equipmentIds;

    private List<ToolingEquipmentId> toolingEquipmentIds;

    private List<CraftsmanShipTemplate> craftsmanShipTemplates;

    public List<WorkProcessTemplate> createWorkProcessList() {
        List<WorkProcessTemplate> workProcessNews = new ArrayList<>();
        Generators.fill(workProcessNews,()->createWorkProcessNew(),20);
        return workProcessNews;
    }

    private static List<Boolean> trueOrFalse(){
        return Arrays.asList(true,false);
    }

    private static List<Double> doubleList(){
        return Arrays.asList(95.2,99.5,99.6,96.4,98.8,97.6);
    }

    public static WorkProcessTemplateControl createWorkProcessTemplateControl(){
        return new WorkProcessTemplateControl.Builder(1,CollectionUtils.random(trueOrFalse()))
                .canSplit(CollectionUtils.random(trueOrFalse())).isAllowedChecked(CollectionUtils.random(trueOrFalse()))
                .gapRangeControl(GapRangeControl.create(CollectionUtils.random(trueOrFalse()), CollectionUtils.random(trueOrFalse())))
                .reportWorkControl(ReportWorkControl.create(CollectionUtils.random(trueOrFalse()),"测试报工规则"))
                .workOrderControl(WorkOrderControl.create(CollectionUtils.random(trueOrFalse()),CollectionUtils.random(trueOrFalse()),CollectionUtils.random(trueOrFalse())))
                .build();
    }

    public static WorkProcessTemplateQuantity createWorkProcessTemplateQuantity(){
        return WorkProcessTemplateQuantity.newBuilder().targetQualifiedRate(
                        CollectionUtils.random(doubleList())
                ).transferPercent(CollectionUtils.random(doubleList()))
                .overCrossPercent(CollectionUtils.random(doubleList()))
                .build();
    }

    public ProductResources createProductResources() {
        return new ProductResources(new HashSet<>(createProductResource()));
    }

    private List<EquipmentId> createEquipmentIds() {
        log.info("开始查询设备id");
        List<EquipmentDTO> equipmentDTOS = equipmentClient.equipments();
        log.info("结束查询设备id");
        equipmentIds =  equipmentDTOS.stream().map(v->v.id()).collect(Collectors.toList());
        return equipmentIds;
    }

    private List<ToolingEquipmentId> createToolingIds(){
        List<ToolingDTO> equipmentDTOS = equipmentClient.toolingList();
        this.toolingEquipmentIds =  equipmentDTOS.stream().map(v->v.id()).collect(Collectors.toList());
        return toolingEquipmentIds;
    }


    public List<ProductResource> createProductResource(){

        if (org.springframework.util.CollectionUtils.isEmpty(equipmentIds)) {
            createEquipmentIds();
        }
        EquipmentId equipmentId = CollectionUtils.random(equipmentIds);
        if (org.springframework.util.CollectionUtils.isEmpty(toolingEquipmentIds)) {
            createToolingIds();
        }
        ToolingEquipmentId toolingId = CollectionUtils.random(toolingEquipmentIds);
        ProductResource equipment = ProductResource.create(equipmentId, ProductResourceType.EQUIPMENT,ChangeableInfo.create(
            "设备生产资源","这是一个设备生产资源"
        ));
        ProductResource tooling = ProductResource.create(toolingId, ProductResourceType.TOOLING,ChangeableInfo.create(
                "工装生产资源","这是一个工装生产资源"
        ));
        return Arrays.asList(equipment, tooling);
    }

    public WorkProcessTemplate createWorkProcessNew(){
        WorkProcessTemplate workProcessTemplate = new WorkProcessTemplate(
                CollectionUtils.random(createWorkProcessInfo()),
                CollectionUtils.random(createAuxiliaryWorkTimes()),
                WorkProcessBeat.create(1000,15),
                createWorkProcessTemplateControl(),
                createWorkProcessTemplateQuantity()
        );
//        for (ProductResource resource: createProductResource()) {
//            workProcessTemplate.addResource(resource);
//        }
        return workProcessTemplate;
    }

    public static List<AuxiliaryWorkTime> createAuxiliaryWorkTimes(){
        String[] dates = {
                "2021-10-01","2021-10-02","2021-10-03","2021-10-04",
                "2021-10-05","2021-10-06","2021-10-07","2021-10-08",
                "2021-10-09","2021-10-10","2021-10-11","2021-10-12",
                "2021-10-13","2021-10-14","2021-10-15","2021-10-16"
        };
        return createAuxiliaryWorkTimes(dates);
    }

    public static List<ChangeableInfo> createWorkProcessInfo(){
        String[] processNames = {
                "拉伸工序","弯曲工序","翻边工序","整形工序",
                "落料工序", "冲孔工序", "切角工序","修边工序",
                "翻孔工序", "斜楔冲孔工序",
                "冲压工序","焊装工序","涂装工序", "总装工序"
        };
        return createWorkProcessInfo(processNames);
    }

    public static List<ChangeableInfo> createWorkProcessInfo(String[] processNames){
        List<ChangeableInfo> workProcessInfo = new ArrayList<>();
        for (String processName: processNames) {
            Generators.fill(workProcessInfo,()->ChangeableInfo.create(processName,
                            "这是对"+processName+"的描述,请仔细查阅"),
                    1);
        }
        return workProcessInfo;
    }

    public static List<AuxiliaryWorkTime> createAuxiliaryWorkTimes(String[] dates){
        List<AuxiliaryWorkTime> list = new ArrayList<>();
        for (String date: dates) {
            Generators.fill(list,()->createAuxiliaryWorkTime(date),
                    1);
        }
        return list;
    }

    private static AuxiliaryWorkTime createAuxiliaryWorkTime(String date){
        DateRange setTimeRange = new DateRange(
                DateUtils.strToDate(date+" 09:30:12","yyyy-MM-dd HH:mm:ss"),
                DateUtils.strToDate(date+" 09:55:12","yyyy-MM-dd HH:mm:ss"),"工序设置时间");
        DateRange offlineTimeRange = new DateRange(
                DateUtils.strToDate(date+" 14:30:12","yyyy-MM-dd HH:mm:ss"),
                DateUtils.strToDate(date+" 16:55:12","yyyy-MM-dd HH:mm:ss"),"工序下线时间");

        DateRange checkTimeRange = new DateRange(
                DateUtils.strToDate(date+" 16:59:12","yyyy-MM-dd HH:mm:ss"),
                DateUtils.strToDate(date+" 18:59:12","yyyy-MM-dd HH:mm:ss"),"工序检查时间");
        return AuxiliaryWorkTime.create(setTimeRange,offlineTimeRange,checkTimeRange);
    }

    private List<WorkProcessTemplateId> randomTemplateIds(List<WorkProcessTemplateId> templateIds) {
        int size = templateIds.size();
        int randomSize = new Random().nextInt(size/2);
        while (randomSize == 0 || randomSize >= 5){
            randomSize = new Random().nextInt(size/2);
        }
        List<WorkProcessTemplateId> results = new ArrayList<>();
        for (int i = 0 ; i < randomSize; i++) {
            results.add(CollectionUtils.random(templateIds));
        }
        return results;
    }

    private CraftsmanShipTemplate createCraftsmanShipTemplate(MaterialId materialId) {
        List<WorkProcessTemplateId> templateIds = workProcessList.stream().map(v->v.id()).collect(Collectors.toList());
        CraftsmanShipTemplate craftsmanShip = new CraftsmanShipTemplate(randomTemplateIds(templateIds),templateRepository,
                materialId);
        return craftsmanShip;
    }

    public List<CraftsmanShipTemplate> createCraftsmanShipTemplates(){
        List<MaterialDTO> materialDTOS = client.materialsByMaterialType(MaterialType.PRODUCTION);
        List<CraftsmanShipTemplate> craftsmanShipTemplates = new ArrayList<>();
        for (MaterialDTO materialDTO: materialDTOS) {
            craftsmanShipTemplates.add(createCraftsmanShipTemplate(materialDTO.id()));
        }
        return craftsmanShipTemplates;
    }
    @PostConstruct
    public void init(){
        templateRepository.deleteAll();
        log.info("删除所有工序成功");
        workProcessList = createWorkProcessList();
        templateRepository.saveAll(workProcessList);
        log.info("创建{}个工序成功",workProcessList.size());

        log.info("删除所有工艺成功");
        craftsmanShipRepository.deleteAll();
        craftsmanShipTemplates = createCraftsmanShipTemplates();
        craftsmanShipRepository.saveAll(craftsmanShipTemplates);
        log.info("创建{}个工艺成功",craftsmanShipTemplates.size());
    }

    public List<WorkProcessTemplate> getWorkProcessList() {
        return new ArrayList<>(workProcessList);
    }

    public List<CraftsmanShipTemplate> getCraftsmanShipTemplates() {
        return new ArrayList<>(craftsmanShipTemplates);
    }
}
