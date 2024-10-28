package org.ddd.fundamental.material.value;

import com.alibaba.fastjson.JSON;
import org.ddd.fundamental.core.ValueObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;
import java.util.Set;

public class PropsContainerTest {
    @Test
    public void testCreateMaterialPropsContainer(){
        PropsContainer container = new PropsContainer.Builder(Set.of("materialType","purpose"))
                .addProperty("materialType","原材料")
                .addProperty("purpose","生产电路板")
                .addProperty("storageCondition","干燥")
                .addProperty("purchaseCycle","两个月")
                .build();
        Assert.assertTrue(container.getRequiredSet().contains("materialType"));
        Assert.assertEquals(container.getRequiredMap().get("materialType"),"原材料");
        Assert.assertEquals(container.getOptionalMap().get("purchaseCycle"),"两个月");
    }

    @Test
    public void testRemoveRequiredPropertyThrowException(){
        PropsContainer container = new PropsContainer.Builder(Set.of("materialType","purpose"))
                .addProperty("materialType","原材料")
                .addProperty("purpose","生产电路板")
                .addProperty("storageCondition","干燥")
                .addProperty("purchaseCycle","两个月")
                .build();
        container.removeOptionalProps("purchaseCycle");
    }

    @Test(expected = RuntimeException.class)
    public void testAddMapToContainerThrowException(){
        PropsContainer container = new PropsContainer.Builder(Set.of("materialType","purpose"))
                .addMap(new MaterialProps("测试","hello","2个月").toMap()).build();
    }

    @Test
    public void testAddMapToContainer(){

        Map<String,String> map = new MaterialProps1("测试","hello","2个月","rawMaterial").toMap();
        PropsContainer container = new PropsContainer.Builder(Set.of("materialType","purpose")).addMap(map).build();
    }
}

class MaterialProps implements ValueObject {
    private String purpose;

    private String storageCondition;

    private String purchaseCycle;

    @SuppressWarnings("unused")
    MaterialProps(){}

    public MaterialProps(String purpose,
                         String storageCondition,
                         String purchaseCycle){
        this.purpose = purpose;
        this.storageCondition = storageCondition;
        this.purchaseCycle = purchaseCycle;
    }

    public Map<String,String> toMap() {
        String jsonString = JSON.toJSONString(this);
        return JSON.parseObject(jsonString,Map.class);
    }

    public String getPurpose() {
        return purpose;
    }

    public String getStorageCondition() {
        return storageCondition;
    }

    public String getPurchaseCycle() {
        return purchaseCycle;
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
