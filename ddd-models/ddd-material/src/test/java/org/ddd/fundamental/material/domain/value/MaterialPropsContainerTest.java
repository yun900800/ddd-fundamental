package org.ddd.fundamental.material.domain.value;

import com.alibaba.fastjson.JSON;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;
import java.util.Set;

public class MaterialPropsContainerTest {

    @Test
    public void testCreateMaterialPropsContainer(){
        MaterialPropsContainer container = new MaterialPropsContainer(Set.of("materialType","purpose"));
        container.addProperty("materialType","原材料")
                .addProperty("purpose","生产电路板")
                .addProperty("storageCondition","干燥")
                .addProperty("purchaseCycle","两个月");
        Assert.assertTrue(container.getRequiredSet().contains("materialType"));
        Assert.assertEquals(container.getRequiredMap().get("materialType"),"原材料");
        Assert.assertEquals(container.getOptionalMap().get("purchaseCycle"),"两个月");
    }

    @Test(expected = RuntimeException.class)
    public void testRemoveRequiredPropertyThrowException(){
        MaterialPropsContainer container = new MaterialPropsContainer(Set.of("materialType","purpose"));
        container.addProperty("materialType","原材料")
                .addProperty("purpose","生产电路板")
                .addProperty("storageCondition","干燥")
                .addProperty("purchaseCycle","两个月");
        container.removeProperty("purpose");
    }

    @Test(expected = RuntimeException.class)
    public void testAddMapToContainerThrowException(){
        MaterialPropsContainer container = new MaterialPropsContainer(Set.of("materialType","purpose"));
        container.addMap(new MaterialProps("测试","hello","2个月").toMap());
    }

    @Test
    public void testAddMapToContainer(){
        MaterialPropsContainer container = new MaterialPropsContainer(Set.of("materialType","purpose"));
        Map<String,String> map = new MaterialProps1("测试","hello","2个月","rawMaterial").toMap();
        container.addMap(map);
    }


}

class MaterialProps1 extends MaterialProps {
    private String materialType;

    @SuppressWarnings("unused")
    MaterialProps1(){
    }

    public MaterialProps1(String purpose,
                          String storageCondition,
                          String purchaseCycle,
                          String materialType){
        super(purpose,storageCondition,purchaseCycle);
        this.materialType = materialType;
    }

    public String getMaterialType() {
        return materialType;
    }
}
