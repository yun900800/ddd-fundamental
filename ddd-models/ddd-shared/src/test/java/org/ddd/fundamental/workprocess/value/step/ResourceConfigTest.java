package org.ddd.fundamental.workprocess.value.step;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.factory.EquipmentId;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.workprocess.enums.ProductResourceType;
import org.ddd.fundamental.workprocess.value.resources.ProductResource;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ResourceConfigTest {

    private static ResourceConfig create(){
        ProductResource<MaterialId> materialResources =
                ProductResource.create(new MaterialId("221454ec-6683-4399-9b56-abe26e9f8b18"),
                        ProductResourceType.MATERIAL,
                        ChangeableInfo.create("锡膏原材料","一种贵重的锡膏"));

        ProductResource<EquipmentId> equipmentResources =
                ProductResource.create(new EquipmentId("4bf3f532-fe0b-4a07-90d0-b974337966d2"),
                        ProductResourceType.EQUIPMENT,
                        ChangeableInfo.create("电路板机床","一种特殊的数控机床"));
        ProductResource<EquipmentId> toolingResources =
                ProductResource.create(new EquipmentId("9b2087c1-f24f-4222-8f84-37761970fdbf"),
                        ProductResourceType.TOOLING,
                        ChangeableInfo.create("工装刀具","一种特殊的切割刀具"));
        ResourceConfig resourceConfig = ResourceConfig.create(new HashSet<>(
                Arrays.asList(materialResources,equipmentResources,toolingResources)
        ));
        return resourceConfig;
    }

    @Test
    public void testCreateResourceConfig(){
        ResourceConfig resourceConfig = create();
        Assert.assertEquals(resourceConfig.size(),3,0);
    }

    @Test
    public void testAddResourceOrRemoveResource(){
        ResourceConfig resourceConfig = create();
        MaterialId addedId = MaterialId.randomId(MaterialId.class);
        ProductResource<MaterialId> addedMaterial =
                ProductResource.create(addedId,
                        ProductResourceType.MATERIAL,
                        ChangeableInfo.create("锡膏XO","一种普通的锡膏"));
        resourceConfig.addResource(addedMaterial);
        Assert.assertEquals(resourceConfig.size(),4,0);

        ProductResource<MaterialId> removeMaterial =
                ProductResource.create(addedId,
                        ProductResourceType.MATERIAL,
                        ChangeableInfo.create("锡膏XO","一种普通的锡膏"));
        resourceConfig.removeResource(removeMaterial);
        Assert.assertEquals(resourceConfig.size(),3,0);
    }

    @Test
    public void testGroupResource(){
        ResourceConfig resourceConfig = create();
        MaterialId addedId = MaterialId.randomId(MaterialId.class);
        ProductResource<MaterialId> addedMaterial =
                ProductResource.create(addedId,
                        ProductResourceType.MATERIAL,
                        ChangeableInfo.create("锡膏XO","一种普通的锡膏"));
        EquipmentId equipmentId = EquipmentId.randomId(EquipmentId.class);
        ProductResource<MaterialId> addedEquipment =
                ProductResource.create(equipmentId,
                        ProductResourceType.EQUIPMENT,
                        ChangeableInfo.create("数控机床","一种普通的机床"));
        resourceConfig.addResource(addedMaterial).addResource(addedEquipment);
        Map<ProductResourceType, Set<ProductResource>> objMap = resourceConfig.groupByType();
        Assert.assertEquals(objMap.get(ProductResourceType.MATERIAL).size(),2);
        Assert.assertEquals(objMap.get(ProductResourceType.EQUIPMENT).size(),2);
        Assert.assertEquals(objMap.get(ProductResourceType.TOOLING).size(),1);
    }

    @Test
    public void testResourceClone(){
        ResourceConfig resourceConfig = create();
        ResourceConfig copyConfig  = resourceConfig.clone();
        MaterialId addedId = MaterialId.randomId(MaterialId.class);
        ProductResource<MaterialId> addedMaterial =
                ProductResource.create(addedId,
                        ProductResourceType.MATERIAL,
                        ChangeableInfo.create("锡膏XO","一种普通的锡膏"));
        resourceConfig.addResource(addedMaterial);
        Assert.assertEquals(resourceConfig.size(),4,0);
        Assert.assertEquals(copyConfig.size(),3,0);

    }
}
