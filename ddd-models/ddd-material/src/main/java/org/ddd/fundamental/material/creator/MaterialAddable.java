package org.ddd.fundamental.material.creator;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.creator.DataAddable;
import org.ddd.fundamental.event.material.ProductEventCreated;
import org.ddd.fundamental.material.MaterialMaster;
import org.ddd.fundamental.material.application.MaterialConverter;
import org.ddd.fundamental.material.domain.model.Material;
import org.ddd.fundamental.material.domain.repository.MaterialRepository;
import org.ddd.fundamental.material.domain.value.ControlProps;
import org.ddd.fundamental.material.enums.MaterialInputOutputType;
import org.ddd.fundamental.material.helper.MaterialHelper;
import org.ddd.fundamental.material.producer.MaterialProducer;
import org.ddd.fundamental.material.value.MaterialType;
import org.ddd.fundamental.material.value.PropsContainer;
import org.ddd.fundamental.redis.config.RedisStoreManager;
import org.ddd.fundamental.shared.api.material.MaterialDTO;
import org.ddd.fundamental.utils.CollectionUtils;
import org.ddd.fundamental.utils.EnumsUtils;
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

    private final MaterialProducer producer;

    private List<Material> materialList;


    @Autowired(required = false)
    public MaterialAddable(MaterialRepository repository,
                             RedisStoreManager manager,
                           MaterialProducer producer
    ){
        this.repository = repository;
        this.manager = manager;
        this.producer = producer;
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
        MaterialMaster materialMaster = MaterialMaster.create(code,materialName,
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
        MaterialType type = CollectionUtils.random(Arrays.asList(MaterialType.values()));
        requiredMap.put("materialType",CollectionUtils.random(MaterialHelper.materialTypes()));
        requiredMap.put("unit",unit);
        requiredMap.put("code",code);
        requiredMap.put("spec",spec);
        requiredMap.put("inputOrOutputType",CollectionUtils.random(
                MaterialHelper.inputOutputTypes().get(type)
        ).name());

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
            materials.add(createMaterial(name,Set.of("materialType","unit","inputOrOutputType"),
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
                MaterialDTO.create(v.getMaterialMaster(),v.id(),
                        v.getMaterialType(),
                        EnumsUtils.findEnumValue(MaterialInputOutputType.class,
                                v.getMaterialRequiredProps().get("inputOrOutputType"))))
                .collect(Collectors.toList());
    }

    private void sendEvent(){
        List<ProductEventCreated> events = MaterialConverter.entityToEvent(materialList);
        for  (ProductEventCreated event: events) {
            producer.sendProductEventCreated(event);
        }
    }
    @Override
    @Transactional
    public void execute() {
        log.info("MaterialAddable execute create all materials start");
        materialList = createMaterials();
        repository.persistAll(materialList);
        sendEvent();

        log.info("MaterialAddable execute create all materials finished");
        manager.storeDataListToCache(entityToDTO(materialList));
    }
}
