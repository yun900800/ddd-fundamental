package org.ddd.fundamental.workprocess.creator;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.core.generator.Generators;
import org.ddd.fundamental.creator.DataAddable;
import org.ddd.fundamental.factory.EquipmentId;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.material.value.MaterialType;
import org.ddd.fundamental.redis.config.RedisStoreManager;
import org.ddd.fundamental.shared.api.equipment.EquipmentDTO;
import org.ddd.fundamental.shared.api.equipment.ToolingDTO;
import org.ddd.fundamental.shared.api.material.MaterialDTO;
import org.ddd.fundamental.shared.api.optemplate.WorkProcessTemplateDTO;
import org.ddd.fundamental.workprocess.client.EquipmentClient;
import org.ddd.fundamental.workprocess.client.MaterialClient;
import org.ddd.fundamental.workprocess.domain.model.WorkProcessTemplate;
import org.ddd.fundamental.workprocess.domain.repository.WorkProcessTemplateRepository;
import org.ddd.fundamental.workprocess.enums.BatchManagable;
import org.ddd.fundamental.workprocess.enums.ProductResourceType;
import org.ddd.fundamental.workprocess.value.WorkProcessBeat;
import org.ddd.fundamental.workprocess.value.controller.ReportWorkControl;
import org.ddd.fundamental.workprocess.value.controller.WorkProcessTemplateControl;
import org.ddd.fundamental.workprocess.value.quantity.WorkProcessTemplateQuantity;
import org.ddd.fundamental.workprocess.value.resources.ProductResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
@Order(1)
public class WorkProcessTemplateAddable implements DataAddable {

    private final WorkProcessTemplateRepository templateRepository;

    private final RedisStoreManager manager;

    @Autowired
    private MaterialClient client;

    @Autowired
    private EquipmentClient equipmentClient;

    private List<WorkProcessTemplate> workProcessTemplateList;

    private List<EquipmentId> equipmentIds;

    private List<MaterialId> workInProgressIds;

    private List<MaterialId> rawMaterialIds;

    private List<EquipmentId> toolingEquipmentIds;

    @Autowired
    public WorkProcessTemplateAddable(WorkProcessTemplateRepository templateRepository,
                                        RedisStoreManager manager){
        this.templateRepository = templateRepository;
        this.manager = manager;
    }

    public List<WorkProcessTemplate> createWorkProcessList() {
        List<WorkProcessTemplate> workProcessNews = new ArrayList<>();
        Generators.fill(workProcessNews,()->createWorkProcessTemplate(),20);
        return workProcessNews;
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

    private static List<Boolean> trueOrFalse(){
        return Arrays.asList(true,false);
    }

    private static List<BatchManagable> batchManagables() {
        return Arrays.asList(BatchManagable.values());
    }

    private static List<Double> doubleList(){
        return Arrays.asList(95.2,99.5,99.6,96.4,98.8,97.6);
    }

    public static WorkProcessTemplateControl createWorkProcessTemplateControl(){
        return new WorkProcessTemplateControl.Builder(1, org.ddd.fundamental.utils.CollectionUtils.random(batchManagables()))
                .canSplit(org.ddd.fundamental.utils.CollectionUtils.random(trueOrFalse())).isAllowedChecked(org.ddd.fundamental.utils.CollectionUtils.random(trueOrFalse()))
                .nextProcessSyncMinutes(20.0)
                .reportWorkControl(ReportWorkControl.create(org.ddd.fundamental.utils.CollectionUtils.random(trueOrFalse()),"测试报工规则"))
                .build();
    }

    public static WorkProcessTemplateQuantity createWorkProcessTemplateQuantity(){
        return WorkProcessTemplateQuantity.newBuilder().targetQualifiedRate(
                        org.ddd.fundamental.utils.CollectionUtils.random(doubleList())
                ).transferPercent(org.ddd.fundamental.utils.CollectionUtils.random(doubleList()))
                .overCrossPercent(org.ddd.fundamental.utils.CollectionUtils.random(doubleList()))
                .build();
    }

    public WorkProcessTemplate createWorkProcessTemplate(){
        WorkProcessTemplate workProcessTemplate = new WorkProcessTemplate(
                org.ddd.fundamental.utils.CollectionUtils.random(createWorkProcessInfo()),
//                CollectionUtils.random(createAuxiliaryWorkTimes()),
                WorkProcessBeat.create(1000,15),
                createWorkProcessTemplateControl(),
                createWorkProcessTemplateQuantity()
        );
        for (ProductResource resource: createProductResource()) {
            workProcessTemplate.addResource(resource);
        }
        return workProcessTemplate;
    }

    private List<EquipmentId> createEquipmentIds() {
        log.info("开始查询设备id");
        List<EquipmentDTO> equipmentDTOS = equipmentClient.equipments();
        log.info("结束查询设备id");
        equipmentIds =  equipmentDTOS.stream().map(v->v.id()).collect(Collectors.toList());
        return equipmentIds;
    }

    private List<MaterialId> createWorkInProgressIds(){
        log.info("开始查询在制品id");
        List<MaterialDTO> materialDTOS = client.materialsByMaterialType(MaterialType.WORKING_IN_PROGRESS);
        log.info("结束查询在制品id");
        workInProgressIds = materialDTOS.stream().map(v->v.id()).collect(Collectors.toList());
        return workInProgressIds;
    }

    private List<MaterialId> createRawMaterialIds(){
        log.info("开始查询在制品id");
        List<MaterialDTO> materialDTOS = client.materialsByMaterialType(MaterialType.RAW_MATERIAL);
        log.info("结束查询在制品id");
        rawMaterialIds = materialDTOS.stream().map(v->v.id()).collect(Collectors.toList());
        return rawMaterialIds;
    }

    private List<EquipmentId> createToolingIds(){
        log.info("开始查询工装id");
        List<ToolingDTO> equipmentDTOS = equipmentClient.toolingList();
        log.info("结束查询工装id");
        this.toolingEquipmentIds =  equipmentDTOS.stream().map(v->v.id()).collect(Collectors.toList());
        return toolingEquipmentIds;
    }

    public List<ProductResource> createProductResource(){
        if (org.springframework.util.CollectionUtils.isEmpty(equipmentIds)) {
            createEquipmentIds();
        }
        EquipmentId equipmentId = org.ddd.fundamental.utils.CollectionUtils.random(equipmentIds);
        if (org.springframework.util.CollectionUtils.isEmpty(toolingEquipmentIds)) {
            createToolingIds();
        }
        EquipmentId toolingId = org.ddd.fundamental.utils.CollectionUtils.random(toolingEquipmentIds);
        if (org.springframework.util.CollectionUtils.isEmpty(workInProgressIds)) {
            createWorkInProgressIds();
        }
        MaterialId workInProgressId = org.ddd.fundamental.utils.CollectionUtils.random(workInProgressIds);
        if (org.springframework.util.CollectionUtils.isEmpty(rawMaterialIds)) {
            createRawMaterialIds();
        }
        MaterialId rawMaterialId = org.ddd.fundamental.utils.CollectionUtils.random(rawMaterialIds);
        List<ProductResource> resources = new ArrayList<>();
        if (null != equipmentId) {
            ProductResource equipment = ProductResource.create(equipmentId, ProductResourceType.EQUIPMENT,ChangeableInfo.create(
                    "设备生产资源","这是一个设备生产资源"
            ));
            resources.add(equipment);
        }

        if (null != toolingId) {
            ProductResource tooling = ProductResource.create(toolingId, ProductResourceType.TOOLING,ChangeableInfo.create(
                    "工装生产资源","这是一个工装生产资源"
            ));
            resources.add(tooling);
        }
        if (workInProgressId !=null) {
            ProductResource workInProgress = ProductResource.create(workInProgressId, ProductResourceType.MATERIAL,ChangeableInfo.create(
                    "在制品生产资源","这是一个在制品生产资源"
            ));
            resources.add(workInProgress);
        }

        if (rawMaterialId != null) {
            ProductResource rawMaterial = ProductResource.create(rawMaterialId, ProductResourceType.MATERIAL,ChangeableInfo.create(
                    "原材料生产资源","这是一个原材料生产资源"
            ));
            resources.add(rawMaterial);
        }

        return resources;
    }

    public static List<WorkProcessTemplateDTO> entityToDTO(List<WorkProcessTemplate> templateList){
        if (CollectionUtils.isEmpty(templateList)){
            return new ArrayList<>();
        }
        return templateList.stream()
                .map(v->WorkProcessTemplateDTO.create(v.id(),
                        v.getWorkProcessInfo(),v.getResources())
                )
                .collect(Collectors.toList());
    }

    public List<WorkProcessTemplate> getWorkProcessTemplateList() {
        return workProcessTemplateList;
    }

    @Override
    @Transactional
    public void execute() {

        workProcessTemplateList = createWorkProcessList();
        log.info("add {} workProcessTemplates start ", workProcessTemplateList.size());
        templateRepository.saveAll(workProcessTemplateList);
        log.info("add all workProcessTemplates finished ");
        manager.storeDataListToCache(entityToDTO(workProcessTemplateList));
    }
}