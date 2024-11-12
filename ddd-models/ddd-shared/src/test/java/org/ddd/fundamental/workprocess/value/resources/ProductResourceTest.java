package org.ddd.fundamental.workprocess.value.resources;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.factory.EquipmentId;
import org.ddd.fundamental.factory.WorkStationId;
import org.ddd.fundamental.workprocess.enums.ProductResourceType;
import org.ddd.fundamental.workprocess.value.resources.ProductResource;
import org.junit.Assert;
import org.junit.Test;

public class ProductResourceTest {

    @Test
    public void createProductResource(){
        EquipmentId id = EquipmentId.randomId(EquipmentId.class);
        ProductResource<EquipmentId> resource0 =  ProductResource.create(
                id,
                ProductResourceType.EQUIPMENT, ChangeableInfo.create("设备生产资源","这是一类设备生产资源")
        );
        Assert.assertEquals(id, resource0.getId());
        Assert.assertEquals(ProductResourceType.EQUIPMENT, resource0.getResourceType());

        WorkStationId stationId = WorkStationId.randomId(WorkStationId.class);
        ProductResource<EquipmentId> resource1 =  ProductResource.create(
                stationId,
                ProductResourceType.WORK_STATION, ChangeableInfo.create("工位生产资源","这是一类工位生产资源")
        );
        Assert.assertEquals(stationId, resource1.getId());
        Assert.assertEquals(ProductResourceType.WORK_STATION, resource1.getResourceType());
    }
}
