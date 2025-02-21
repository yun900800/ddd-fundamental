package org.ddd.fundamental.material.domain.model;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.material.domain.repository.MaterialRepository;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.material.MaterialMaster;
import org.ddd.fundamental.material.MaterialAppTest;
import org.ddd.fundamental.material.value.PropsContainer;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class MaterialRepositoryTest extends MaterialAppTest {

    @Autowired
    private MaterialRepository repository;

    @Test
    public void testSaveMaterial() {
        //ChangeableInfo info = ChangeableInfo.create("螺纹钢","这是一种通用的钢材");
        MaterialMaster materialMaster = MaterialMaster.create("XG-code","锡膏",
                "XG-spec-001","瓶");
        Material material = Material.create(materialMaster);
        repository.save(material);
        Assert.assertEquals(material.name(),"螺纹钢");
    }

    @Test
    public void testChangeNameOfMaterial(){
        //ChangeableInfo info = ChangeableInfo.create("螺纹钢","这是一种通用的钢材");
        MaterialMaster materialMaster = MaterialMaster.create("XG-code","锡膏",
                "XG-spec-001","瓶");
        Material material = Material.create(materialMaster);
        repository.save(material);
        material.changeName("特种螺纹钢");
        repository.save(material);
        Assert.assertEquals(material.name(),"特种螺纹钢");
    }

    @Test
    public void testChangeMaterialProps() {
        ChangeableInfo info = ChangeableInfo.create("螺纹钢混合1","这是一种高级的钢材1");
        MaterialMaster materialMaster = MaterialMaster.create("XG-code","锡膏",
                "XG-spec-002","瓶");
        PropsContainer props = new PropsContainer.Builder(Set.of("usage","storageCondition","purchaseCycle"))
                .addProperty("usage","生产电路板1")
                .addProperty("storageCondition","干燥")
                .addProperty("purchaseCycle","三个月")
                .build();
        Material material = Material.create(materialMaster,props,null,null);
        repository.save(material);

        MaterialId id = material.id();
        material = repository.findById(id).get();
        Assert.assertEquals("生产电路板1",material.getMaterialRequiredProps().get("usage"));
        Assert.assertEquals("干燥",material.getMaterialRequiredProps().get("storageCondition"));
        Assert.assertEquals("三个月",material.getMaterialRequiredProps().get("purchaseCycle"));
    }
}
