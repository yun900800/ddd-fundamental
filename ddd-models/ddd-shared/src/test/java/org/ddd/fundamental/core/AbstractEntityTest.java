package org.ddd.fundamental.core;

import org.junit.Assert;
import org.junit.Test;

public class AbstractEntityTest {

    @Test
    public void testCreateAbstractEntity(){
        RawMaterial rawMaterial = new RawMaterial("螺纹钢");
        String id = rawMaterial.id().toUUID();
        Assert.assertTrue(id.length() == 36);
        Assert.assertEquals(rawMaterial.getName(),"螺纹钢");
    }

}

class RawMaterial extends AbstractEntity<RawMaterialId>{
    private String name;
    private RawMaterial(){}
    RawMaterial(String name){
        super(DomainObjectId.randomId(RawMaterialId.class));
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
class RawMaterialId extends DomainObjectId{

    public RawMaterialId(String uuid) {
        super(uuid);
    }
}
