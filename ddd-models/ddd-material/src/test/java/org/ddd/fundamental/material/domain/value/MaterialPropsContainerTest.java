package org.ddd.fundamental.material.domain.value;

import org.junit.Assert;
import org.junit.Test;

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


}
