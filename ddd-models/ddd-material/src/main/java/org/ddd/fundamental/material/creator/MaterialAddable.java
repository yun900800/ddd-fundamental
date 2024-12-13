package org.ddd.fundamental.material.creator;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.creator.DataAddable;
import org.ddd.fundamental.material.MaterialMaster;
import org.ddd.fundamental.material.domain.model.Material;
import org.ddd.fundamental.material.domain.repository.MaterialRepository;
import org.ddd.fundamental.material.domain.value.ControlProps;
import org.ddd.fundamental.material.helper.MaterialHelper;
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

import static org.ddd.fundamental.material.helper.MaterialHelper.materialLevels;

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


    public static Material createMaterial(String name, Set<String> requiredSets,
                                           Set<String> characterSets,
                                           MaterialType type,Map<String,String> requiredMap,
                                           Map<String,String> characterMap) {

        String unit = requiredMap.get("unit");
        String code = requiredMap.get("code");
        String spec = requiredMap.get("spec");
        PropsContainer requiredPropsContainer = new PropsContainer.Builder(requiredSets)
                .addMap(requiredMap)
                .addProperty("optional","custom")
                .build();

        PropsContainer optionalCharacterContainer = new PropsContainer.Builder(characterSets)
                .addMap(characterMap)
                .build();
        String materialName  = name+"-"+MaterialHelper.generateSerialNo(name,10);
        String materialDesc = MaterialHelper.generateSerialNo("这是一种高级材料",10);
        ChangeableInfo info = ChangeableInfo.create(materialName,materialDesc);
        MaterialMaster materialMaster = new MaterialMaster(code,materialName,
                spec,unit);

        ControlProps materialControlProps = ControlProps.create(CollectionUtils.random(materialLevels()),
                type);
        Material material = new Material(info,materialMaster,requiredPropsContainer,optionalCharacterContainer,materialControlProps);
        return material;
    }

    public static Map<String,String> createRequiredMap(){
        Map<String,String> requiredMap = new HashMap<>();
        String unit = CollectionUtils.random(MaterialHelper.units());
        String code = MaterialHelper.generateCode("XG-spec-code");
        String spec = MaterialHelper.generateSerialNo("XG-spec-00_",10);
        requiredMap.put("materialType",CollectionUtils.random(MaterialHelper.materialTypes()));
        requiredMap.put("unit",unit);
        requiredMap.put("code",code);
        requiredMap.put("spec",spec);
        return requiredMap;
    }

    public static Map<String,String> createCharacterMap(){
        Map<String,String> characterMap = new HashMap<>();
        characterMap.put("weight",CollectionUtils.random(MaterialHelper.numbers())+ "cm");
        characterMap.put("width",CollectionUtils.random(MaterialHelper.numbers())+ "cm");
        characterMap.put("height",CollectionUtils.random(MaterialHelper.numbers())+ "cm");
        return characterMap;
    }

    public static List<Material> createMaterials(){
        List<Material> materials = new ArrayList<>();
        for (int i = 0 ; i< 50;i++) {
            MaterialType type = CollectionUtils.random(Arrays.asList(MaterialType.values()));
            String name = CollectionUtils.random(MaterialHelper.createMaterial().get(type));
            materials.add(createMaterial(name,Set.of("materialType","unit"),
                    Set.of("weight","width"), type, createRequiredMap(),
                    createCharacterMap()));
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
