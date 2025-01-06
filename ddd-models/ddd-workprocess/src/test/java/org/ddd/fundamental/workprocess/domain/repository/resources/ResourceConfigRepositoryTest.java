package org.ddd.fundamental.workprocess.domain.repository.resources;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.factory.EquipmentId;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.tuple.Tuple;
import org.ddd.fundamental.tuple.TwoTuple;
import org.ddd.fundamental.workprocess.WorkProcessAppTest;
import org.ddd.fundamental.workprocess.domain.model.resources.ResourceConfigEntity;
import org.ddd.fundamental.workprocess.enums.ProductResourceType;
import org.ddd.fundamental.workprocess.value.WorkProcessTemplateId;
import org.ddd.fundamental.workprocess.value.resources.ProductResource;
import org.ddd.fundamental.workprocess.value.step.ResourceConfig;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashSet;

public class ResourceConfigRepositoryTest extends WorkProcessAppTest {

    @Autowired(required = false)
    private ResourceConfigRepository repository;

    private static TwoTuple<ResourceConfigEntity, WorkProcessTemplateId> create(){
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
        ResourceConfigEntity entity =  ResourceConfigEntity.create(resourceConfig);
        return Tuple.tuple(entity,entity.id());
    }

    @Test
    public void testCreateResourceConfig(){
        TwoTuple<ResourceConfigEntity, WorkProcessTemplateId> twoTuple = create();
        ResourceConfigEntity resourceConfig = twoTuple.first;
        repository.persist(resourceConfig);
        WorkProcessTemplateId id = twoTuple.second;
        ResourceConfigEntity queryResourceConfig = repository.findById(id).orElse(null);
        Assert.assertEquals(queryResourceConfig.size(),3,0);
    }
}
