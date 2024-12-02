package org.ddd.fundamental.material.creator;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.creator.DataAddable;
import org.ddd.fundamental.material.MaterialMaster;
import org.ddd.fundamental.material.domain.model.Material;
import org.ddd.fundamental.material.domain.repository.MaterialRepository;
import org.ddd.fundamental.material.domain.value.ControlProps;
import org.ddd.fundamental.material.value.MaterialType;
import org.ddd.fundamental.material.value.PropsContainer;
import org.ddd.fundamental.redis.config.RedisStoreManager;
import org.ddd.fundamental.shared.api.material.MaterialDTO;
import org.ddd.fundamental.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
@Order(1)
public class MaterialAddable implements DataAddable {

    private final MaterialRepository repository;

    private final RedisStoreManager manager;

    private List<Material> materialList;

    @Autowired
    public MaterialAddable(MaterialRepository repository,
                             RedisStoreManager manager
    ){
        this.repository = repository;
        this.manager = manager;
    }

    public List<Material> getMaterialList() {
        return new ArrayList<>(materialList);
    }

    private static List<String> materialTypes(){
        return Arrays.asList("rawMaterial","workInProgress","production");
    }

    private static List<String> units(){
        return Arrays.asList("个","瓶","箱","颗","台","桶");
    }

    public static List<Integer> numbers(){
        return Arrays.asList(1,2,3,4,5,6,7,8,9,10,12,15,18,30);
    }

    private static Map<MaterialType, List<String>> createMaterial(){
        Map<MaterialType,List<String>> map = new HashMap<>();
        map.put(MaterialType.RAW_MATERIAL, Arrays.asList(
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

    public static List<Material> createMaterials(){
        List<Material> materials = new ArrayList<>();
        for (int i = 0 ; i< 50;i++) {
            MaterialType type = CollectionUtils.random(Arrays.asList(MaterialType.values()));
            String name = CollectionUtils.random(createMaterial().get(type));
            materials.add(createMaterial(name,(i+1), Set.of("materialType","unit"),
                    Set.of("weight","width"), type));
        }
        return materials;
    }

    private static List<MaterialDTO> entityToDTO(List<Material> materials) {
        if (org.springframework.util.CollectionUtils.isEmpty(materials)) {
            return new ArrayList<>();
        }
        return materials.stream().map(v->
                MaterialDTO.create(v.getMaterialMaster(),v.id()))
                .collect(Collectors.toList());
    }
    @Override
    @Transactional
    public void execute() {
        log.info("MaterialAddable execute create all materials start");
        materialList = createMaterials();
        repository.persistAll(materialList);
        log.info("MaterialAddable execute create all materials finished");
        manager.storeDataListToCache(entityToDTO(materialList));
    }
}
