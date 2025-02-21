package org.ddd.fundamental.material.domain.model;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.changeable.NameDescInfo;
import org.ddd.fundamental.material.MaterialCharacter;
import org.ddd.fundamental.material.MaterialMaster;
import org.ddd.fundamental.material.value.PropsContainer;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

public class MaterialTest {
    @Test
    public void testCreateMaterialWithRequiredProps(){
        PropsContainer requiredPropsContainer = new PropsContainer.Builder(Set.of("materialType","unit"))
                .addProperty("materialType","rawMaterial")
                .addProperty("unit","个")
                .addProperty("code","test")
                .addProperty("spec","x37b")
                .addProperty("optional","custom")
                .build();
        MaterialMaster materialMaster = MaterialMaster.create(NameDescInfo.create("螺纹钢混合1","这是一种高级的钢材1"), MaterialCharacter.create(
                "XG-code",
                "XG-spec-002","瓶"
        ));
        Material material = Material.create(materialMaster,requiredPropsContainer,null,null);
        Assert.assertEquals(material.getMaterialRequiredProps().get("materialType"),"rawMaterial");
        Assert.assertEquals(material.getMaterialOptionalProps().get("spec"),"x37b");
        Assert.assertEquals(material.getMaterialOptionalProps().size(),3);
        Assert.assertEquals(material.getMaterialRequiredProps().size(),2);
    }
}
