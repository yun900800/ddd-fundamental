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
}
