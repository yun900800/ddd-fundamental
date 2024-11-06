package org.ddd.fundamental.material.application;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.material.MaterialMaster;
import org.ddd.fundamental.material.domain.model.Material;
import org.ddd.fundamental.material.domain.repository.MaterialRepository;
import org.ddd.fundamental.material.value.PropsContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class MaterialCreator {

    @Autowired
    private MaterialRepository materialRepository;

    private List<Material> materialList;

    private static List<Material> createMaterials(){
        List<Material> materials = new ArrayList<>();
        PropsContainer requiredPropsContainer = new PropsContainer.Builder(Set.of("materialType","unit"))
                .addProperty("materialType","rawMaterial")
                .addProperty("unit","个")
                .addProperty("code","test")
                .addProperty("spec","x37b")
                .addProperty("optional","custom")
                .build();
        ChangeableInfo info = ChangeableInfo.create("螺纹钢混合1","这是一种高级的钢材1");
        MaterialMaster materialMaster = new MaterialMaster("XG-code","锡膏",
                "XG-spec-002","瓶");
        Material material = new Material(info,materialMaster,requiredPropsContainer,null);
        materials.add(material);
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
