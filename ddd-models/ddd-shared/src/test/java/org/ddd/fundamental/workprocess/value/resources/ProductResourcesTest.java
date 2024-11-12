package org.ddd.fundamental.workprocess.value.resources;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.factory.EquipmentId;
import org.ddd.fundamental.factory.WorkStationId;
import org.ddd.fundamental.workprocess.enums.ProductResourceType;
import org.ddd.fundamental.workprocess.value.resources.ProductResource;
import org.ddd.fundamental.workprocess.value.resources.ProductResources;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductResourcesTest {

    private static Set<ProductResource> createResources(){
        EquipmentId id = EquipmentId.randomId(EquipmentId.class);
        ProductResource<EquipmentId> resource0 =  ProductResource.create(
                id,
                ProductResourceType.EQUIPMENT, ChangeableInfo.create("设备生产资源","这是一类设备生产资源")
        );


        WorkStationId stationId = WorkStationId.randomId(WorkStationId.class);
        ProductResource<EquipmentId> resource1 =  ProductResource.create(
                stationId,
                ProductResourceType.WORK_STATION, ChangeableInfo.create("工位生产资源","这是一类工位生产资源")
        );
        return new HashSet<>(Arrays.asList(resource0,resource1));
    }

    @Test
    public void testProductResources(){
        ProductResources resources = new ProductResources(createResources());
        System.out.println(resources);
    }
}
