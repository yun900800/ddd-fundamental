package org.ddd.fundamental.material.domain.repository;

import com.alibaba.fastjson.JSON;
import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.material.MaterialMaster;
import org.ddd.fundamental.material.MaterialTest;
import org.ddd.fundamental.material.domain.model.Material;
import org.ddd.fundamental.material.domain.model.MaterialId;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MaterialRepositoryTest extends MaterialTest {

    @Autowired
    private MaterialRepository materialRepository;
    @Test
    public void testQueryMaterialById(){
        ChangeableInfo info = ChangeableInfo.create("螺纹钢","这是一种通用的钢材");
        MaterialMaster materialMaster = new MaterialMaster("XG-code","锡膏",
                "XG-spec-001","瓶");
        Material material = new Material(info,materialMaster);
        Map<String,String> json = new HashMap<>();
        json.put("name","rabbitMQ");
        material.changeJson(JSON.toJSONString(json));
        materialRepository.save(material);
        MaterialId id = material.id();
        Material queryData = materialRepository.getById(id);
        Assert.assertEquals(queryData.name(),"螺纹钢");

        Material queryMaterial = new Material(info,null);
        queryMaterial.changeId(null);
        queryMaterial.resetMaterialProps();
        queryMaterial.resetMaterialJson();
        Example<Material> example = Example.of(queryMaterial);
        List<Material> dataList = materialRepository.findAll(example);
        Assert.assertEquals(dataList.size(),4);
    }

}
