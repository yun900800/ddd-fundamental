package org.ddd.fundamental.material.domain.repository;

import com.alibaba.fastjson.JSON;
import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.day.Auditable;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.material.MaterialMaster;
import org.ddd.fundamental.material.MaterialAppTest;
import org.ddd.fundamental.material.domain.model.Material;
import org.ddd.fundamental.material.value.MaterialType;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MaterialRepositoryTest extends MaterialAppTest {

    @Autowired
    private MaterialRepository materialRepository;

    private Material createMaterial(){
        ChangeableInfo info = ChangeableInfo.create("螺纹钢","这是一种通用的钢材");
        MaterialMaster materialMaster = new MaterialMaster("XG-code","锡膏",
                "XG-spec-001","瓶");
        Material material = new Material(info,materialMaster);
        Map<String,String> json = new HashMap<>();
        json.put("name","rabbitMQ");
        material.changeJson(JSON.toJSONString(json));
        materialRepository.save(material);
        return material;
    }
    @Test
    public void testQueryMaterialById(){
        Material material = createMaterial();
        MaterialId id = material.id();
        Material queryData = materialRepository.getById(id);
        Assert.assertEquals(queryData.name(),"螺纹钢");

        Material queryMaterial = new Material(material.getChangeableInfo(),null);
        queryMaterial.changeId(null);
        queryMaterial.resetRequiredProps();
        queryMaterial.resetRequiredCharacter();
        queryMaterial.resetOptionalCharacter();
        queryMaterial.resetOptionalProps();
        queryMaterial.resetMaterialJson();
        //Example query 支持json查询吗
        //queryMaterial.changeJson(JSON.toJSONString(json));
        Auditable auditable = new Auditable(0L);
        auditable.changeCreateTime(null).changeUpdateTime(null);
        queryMaterial.changeAuditable(auditable);
        Example<Material> example = Example.of(queryMaterial);
        List<Material> dataList = materialRepository.findAll(example);
        Assert.assertEquals(dataList.size(),1);
    }

    //json查询的三种方法中的第一种
    @Test
    public void testJsonQueryMaterial() {
        createMaterial();
        List<Material> materialList = materialRepository.findByAttribute("$.name","rabbitMQ");
        Assert.assertEquals(materialList.size(),1);
    }

    @Test
    public void testQueryByMaterialType () {
        List<Material> materials = materialRepository.getByMaterialType(MaterialType.PRODUCTION.name());
        Assert.assertTrue(materials.size() > 0);
    }

}
