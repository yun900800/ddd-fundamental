package org.ddd.fundamental.material;

import org.junit.Assert;
import org.junit.Test;

public class MaterialMasterTest {

    @Test
    public void testCreateMaterialMaster(){
        MaterialMaster materialMaster = new MaterialMaster("XG-code","锡膏",
                "XG-spec-001","瓶");
        Assert.assertEquals("XG-code, 锡膏 XG-spec-001, 瓶",materialMaster.toString());
    }
}
