package org.ddd.fundamental.equipment.value;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.day.range.DateRangeValue;
import org.ddd.fundamental.factory.EquipmentId;
import org.ddd.fundamental.workprocess.enums.ProductResourceType;
import org.junit.Assert;
import org.junit.Test;

import java.time.Instant;

public class EquipmentResourceTest {

    @Test
    public void testCreateEquipmentResource(){
        EquipmentResource resource = EquipmentResource.create(
                EquipmentId.randomId(EquipmentId.class),
                ProductResourceType.EQUIPMENT,
                ChangeableInfo.create("设备资源","这是一个设备资源"),
                null
        );
        System.out.println(resource);
    }

    @Test
    public void testUsed(){
        EquipmentResource resource = EquipmentResource.create(
                EquipmentId.randomId(EquipmentId.class),
                ProductResourceType.EQUIPMENT,
                ChangeableInfo.create("设备资源","这是一个设备资源"),
                null
        );
        Assert.assertFalse(resource.isUsed());
        resource.recordUseRange(DateRangeValue.create(
                Instant.parse("2024-11-07T12:55:58.00Z"),
                Instant.parse("2024-11-08T14:55:58.00Z"),
                "这是一个使用parse创建的时间段"
        ));
        Assert.assertTrue(resource.isUsed());
        resource.finishUseRange();
        Assert.assertFalse(resource.isUsed());
    }

    @Test
    public void testAddRange() {
        EquipmentResource resource = EquipmentResource.create(
                EquipmentId.randomId(EquipmentId.class),
                ProductResourceType.EQUIPMENT,
                ChangeableInfo.create("设备资源","这是一个设备资源"),
                null
        );
        resource.addRange(DateRangeValue.create(
                Instant.parse("2024-11-07T12:55:58.00Z"),
                Instant.parse("2024-11-08T14:55:58.00Z"),
                "这是一个使用parse创建的时间段"
        ));
        resource.addRange(DateRangeValue.create(
                Instant.parse("2024-11-08T14:55:59.00Z"),
                Instant.parse("2024-11-09T12:55:58.00Z"),
                "这是一个使用parse创建的时间段"
        ));
    }

    @Test(expected = RuntimeException.class)
    public void testAddRangeThrowException(){
        EquipmentResource resource = EquipmentResource.create(
                EquipmentId.randomId(EquipmentId.class),
                ProductResourceType.EQUIPMENT,
                ChangeableInfo.create("设备资源","这是一个设备资源"),
                null
        );
        resource.addRange(DateRangeValue.create(
                Instant.parse("2024-11-07T12:55:58.00Z"),
                Instant.parse("2024-11-08T14:55:58.00Z"),
                "这是一个使用parse创建的时间段"
        ));
        resource.addRange(DateRangeValue.create(
                Instant.parse("2024-11-08T12:55:59.00Z"),
                Instant.parse("2024-11-09T12:55:58.00Z"),
                "这是一个使用parse创建的时间段"
        ));
    }
}
