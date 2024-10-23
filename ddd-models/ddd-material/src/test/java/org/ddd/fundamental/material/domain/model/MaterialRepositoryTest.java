package org.ddd.fundamental.material.domain.model;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.material.MaterialMaster;
import org.ddd.fundamental.material.MaterialTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MaterialRepositoryTest extends MaterialTest {

    @Autowired
    private MaterialRepository repository;

    @Test
    public void testSaveMaterial() {
        ChangeableInfo info = ChangeableInfo.create("螺纹钢","这是一种通用的钢材");
        MaterialMaster materialMaster = new MaterialMaster("XG-code","锡膏",
                "XG-spec-001","瓶");
        Material material = new Material(info,materialMaster);
        repository.save(material);
        Assert.assertEquals(material.name(),"螺纹钢");
    }

    @Test
    public void testChangeNameOfMaterial(){
        ChangeableInfo info = ChangeableInfo.create("螺纹钢","这是一种通用的钢材");
        MaterialMaster materialMaster = new MaterialMaster("XG-code","锡膏",
                "XG-spec-001","瓶");
        Material material = new Material(info,materialMaster);
        repository.save(material);
        material.changeName("特种螺纹钢");
        repository.save(material);
        Assert.assertEquals(material.name(),"特种螺纹钢");
    }

    @Test
    public void testChangeMaterialProps() {
        ChangeableInfo info = ChangeableInfo.create("螺纹钢混合1","这是一种高级的钢材1");
        MaterialMaster materialMaster = new MaterialMaster("XG-code","锡膏",
                "XG-spec-002","瓶");
        Material material = new Material(info,materialMaster);
        material.putMaterialProps("usage","生产电路板1")
                .putMaterialProps("storageCondition","干燥")
                .putMaterialProps("purchaseCycle","三个月");
        repository.save(material);

        MaterialId id = material.id();
        material = repository.findById(id).get();
        Assert.assertEquals("生产电路板1",material.getMaterialProps().get("usage"));
        Assert.assertEquals("干燥",material.getMaterialProps().get("storageCondition"));
        Assert.assertEquals("三个月",material.getMaterialProps().get("purchaseCycle"));
    }
}
