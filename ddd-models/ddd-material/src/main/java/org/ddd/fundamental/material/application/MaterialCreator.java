package org.ddd.fundamental.material.application;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.material.MaterialMaster;
import org.ddd.fundamental.material.domain.model.Material;
import org.ddd.fundamental.material.domain.repository.MaterialRepository;
import org.ddd.fundamental.material.value.PropsContainer;
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

    private List<Material> materialList;

    private static List<String> materialTypes(){
        return Arrays.asList("rawMaterial","workInProgress","production");
    }

    private static List<String> units(){
        return Arrays.asList("个","瓶","箱","颗","台","桶");
    }

    private static List<Integer> numbers(){
        return Arrays.asList(1,2,3,4,5,6,7,8,9,10);
    }

    private static Material createMaterial(String name, int index, Set<String> requiredSets, Set<String> characterSets) {

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
        MaterialMaster materialMaster = new MaterialMaster(code,"锡膏",
                spec,unit);
        Material material = new Material(info,materialMaster,requiredPropsContainer,optionalCharacterContainer);
        return material;
    }

    private static List<Material> createMaterials(){
        List<Material> materials = new ArrayList<>();
        for (int i = 0 ; i< 50;i++) {
            materials.add(createMaterial("螺纹钢",(i+1), Set.of("materialType","unit"),
                    Set.of("weight","width")));
        }
        return materials;
    }

    @PostConstruct
    public void init(){
        materialRepository.deleteAll();
        log.info("删除基础物料成功");
        materialList = createMaterials();
        materialRepository.saveAll(materialList);
        log.info("创建基础物料成功");
    }


}