package org.ddd.fundamental.material.application;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.material.MaterialMaster;
import org.ddd.fundamental.material.domain.enums.BatchClassifyType;
import org.ddd.fundamental.material.domain.model.Material;
import org.ddd.fundamental.material.domain.model.MaterialBatch;
import org.ddd.fundamental.material.domain.repository.MaterialBatchRepository;
import org.ddd.fundamental.material.domain.repository.MaterialRepository;
import org.ddd.fundamental.material.domain.value.ControlProps;
import org.ddd.fundamental.material.domain.value.MaterialBatchValue;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.material.value.PropsContainer;
import org.ddd.fundamental.shared.api.material.enums.MaterialType;
import org.ddd.fundamental.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
@Slf4j
public class MaterialCreator {

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private MaterialBatchRepository batchRepository;

    private List<Material> materialList;

    private List<MaterialBatch> materialBatches;

    private static List<String> materialTypes(){
        return Arrays.asList("rawMaterial","workInProgress","production");
    }

    private static List<String> units(){
        return Arrays.asList("个","瓶","箱","颗","台","桶");
    }

    private static List<Integer> numbers(){
        return Arrays.asList(1,2,3,4,5,6,7,8,9,10,12,15,18,30);
    }

    private static Map<MaterialType, List<String>> createMaterial(){
        Map<MaterialType,List<String>> map = new HashMap<>();
        map.put(MaterialType.RAW_MATERIAL,Arrays.asList(
                "螺纹钢","锡膏","测试仪器","螺钉","纸张"
        ));
        map.put(MaterialType.WORKING_IN_PROGRESS,Arrays.asList(
                "工序1的在制品","工序5的在制品","工序3的在制品","工序8的在制品","工序9的在制品"
        ));
        map.put(MaterialType.PRODUCTION,Arrays.asList(
                "鼠标","主板","酒瓶塞子","电脑","玩具"
        ));
        return map;
    }

    private static Material createMaterial(String name, int index, Set<String> requiredSets,
                                           Set<String> characterSets,
                                           MaterialType type) {

        String unit = CollectionUtils.random(units());
        String code = "XG-spec-" +index;
        String spec = "XG-spec-00_" +index;
        PropsContainer requiredPropsContainer = new PropsContainer.Builder(requiredSets)
                .addProperty("materialType", CollectionUtils.random(materialTypes()))
                .addProperty("unit",unit)
                .addProperty("code",code)
                .addProperty("spec",spec)
                .addProperty("optional","custom")
                .build();

        PropsContainer optionalCharacterContainer = new PropsContainer.Builder(characterSets)
                .addProperty("weight", CollectionUtils.random(numbers())+ "g")
                .addProperty("width", CollectionUtils.random(numbers())+ "cm")
                .addProperty("height",CollectionUtils.random(numbers())+ "cm")
                .build();

        ChangeableInfo info = ChangeableInfo.create(name+"-"+index,"这是一种高级的材料——"+index);
        MaterialMaster materialMaster = new MaterialMaster(code,name+"-"+index,
                spec,unit);

        ControlProps materialControlProps = ControlProps.create("默认等级",
                type);
        Material material = new Material(info,materialMaster,requiredPropsContainer,optionalCharacterContainer,materialControlProps);
        return material;
    }

    private static List<Material> createMaterials(){
        List<Material> materials = new ArrayList<>();
        for (int i = 0 ; i< 50;i++) {
            MaterialType type = CollectionUtils.random(Arrays.asList(MaterialType.values()));
            String name = CollectionUtils.random(createMaterial().get(type));
            materials.add(createMaterial(name,(i+1), Set.of("materialType","unit"),
                    Set.of("weight","width"), type));
        }
        return materials;
    }

    public static List<String> workProcessNames(){
        return Arrays.asList("工序1","工序2","工序3","工序4","工序5");

    }

    public static List<MaterialBatch> createMaterialBatch(){
        List<MaterialBatch> materialBatches1 = new ArrayList<>();
        for (int i = 0 ; i < 200; i++) {
            materialBatches1.add(
                    createMaterialBatch(CollectionUtils.random(workProcessNames()),
                            i+1)
            );
        }
        return materialBatches1;
    }

    private static MaterialBatch createMaterialBatch(String workProcessName,int index) {
        Material material = CollectionUtils.random(createMaterials());
        MaterialId  materialId = material.id();
        int batchNumber = CollectionUtils.random(numbers());
        BatchClassifyType batchClassifyType = CollectionUtils.random(Arrays.asList(
                BatchClassifyType.PRODUCT_BATCH,
                BatchClassifyType.ERP_BATCH
        ));
        MaterialBatch batch = MaterialBatch.create(new MaterialBatchValue(
                materialId,
                batchNumber, batchClassifyType
        ), ChangeableInfo.create(workProcessName+ index + material.name() + index +
                "批次", "这是在"+ workProcessName+ index+ "为"+ material.name() + index +"产生的批次"));
        return batch;
    }

    @PostConstruct
    public void init(){
        materialRepository.deleteAll();
        log.info("删除基础物料成功");
        materialList = createMaterials();
        materialRepository.saveAll(materialList);
        log.info("创建基础物料成功");

        batchRepository.deleteAll();
        log.info("删除基础物料批次成功");
        materialBatches = createMaterialBatch();
        batchRepository.saveAll(materialBatches);
        log.info("创建基础物料批次成功");

    }

    public List<Material> getMaterialList() {
        return new ArrayList<>(materialList);
    }

    public List<MaterialBatch> getMaterialBatches() {
        return new ArrayList<>(materialBatches);
    }
}
