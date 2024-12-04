package org.ddd.fundamental.equipment.value;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.day.range.DateRangeValue;
import org.ddd.fundamental.factory.EquipmentId;
import org.ddd.fundamental.workorder.value.WorkOrderId;
import org.ddd.fundamental.workprocess.enums.ProductResourceType;
import org.ddd.fundamental.workprocess.value.WorkProcessId;
import org.junit.Assert;
import org.junit.Test;

import java.time.Instant;

public class EquipmentResourceTest {

    @Test
    public void testCreateEquipmentResource(){
        EquipmentResourceValue resource = EquipmentResourceValue.create(
                EquipmentId.randomId(EquipmentId.class),
                ProductResourceType.EQUIPMENT,
                ChangeableInfo.create("设备资源","这是一个设备资源"),
                null
        );
        System.out.println(resource);
    }

    @Test
    public void testUsed(){
        EquipmentResourceValue resource = EquipmentResourceValue.create(
                EquipmentId.randomId(EquipmentId.class),
                ProductResourceType.EQUIPMENT,
                ChangeableInfo.create("设备资源","这是一个设备资源"),
                null
        );
        Assert.assertFalse(resource.isUsed());
        resource.recordUseRange(EquipmentPlanRange.create(
                DateRangeValue.create(
                        Instant.parse("2024-11-07T12:55:58.00Z"),
                        Instant.parse("2024-11-08T14:55:58.00Z"),
                        "这是一个使用parse创建的时间段"
                ),
                WorkOrderId.randomId(WorkOrderId.class),
                WorkProcessId.randomId(WorkProcessId.class)
        ));
        Assert.assertTrue(resource.isUsed());
        resource.finishUseRange();
        Assert.assertFalse(resource.isUsed());
    }

    @Test
    public void testAddRange() {
        EquipmentResourceValue resource = EquipmentResourceValue.create(
                EquipmentId.randomId(EquipmentId.class),
                ProductResourceType.EQUIPMENT,
                ChangeableInfo.create("设备资源","这是一个设备资源"),
                null
        );
        resource.addRange(
                EquipmentPlanRange.create(
                        DateRangeValue.create(
                                Instant.parse("2024-11-07T12:55:58.00Z"),
                                Instant.parse("2024-11-08T14:55:58.00Z"),
                                "这是一个使用parse创建的时间段"
                        ),
                        WorkOrderId.randomId(WorkOrderId.class),
                        WorkProcessId.randomId(WorkProcessId.class)
                ));
        resource.addRange(
                EquipmentPlanRange.create(
                        DateRangeValue.create(
                                Instant.parse("2024-11-08T14:55:59.00Z"),
                                Instant.parse("2024-11-09T12:55:58.00Z"),
                                "这是一个使用parse创建的时间段"
                        ),
                        WorkOrderId.randomId(WorkOrderId.class),
                        WorkProcessId.randomId(WorkProcessId.class)
                )
                );
    }

    @Test(expected = RuntimeException.class)
    public void testAddRangeThrowException(){
        EquipmentResourceValue resource = EquipmentResourceValue.create(
                EquipmentId.randomId(EquipmentId.class),
                ProductResourceType.EQUIPMENT,
                ChangeableInfo.create("设备资源","这是一个设备资源"),
                null
        );
        resource.addRange(
                EquipmentPlanRange.create(
                        DateRangeValue.create(
                                Instant.parse("2024-11-07T12:55:58.00Z"),
                                Instant.parse("2024-11-08T14:55:58.00Z"),
                                "这是一个使用parse创建的时间段"
                        ),
                        WorkOrderId.randomId(WorkOrderId.class),
                        WorkProcessId.randomId(WorkProcessId.class)
                )
                );
        resource.addRange(
                EquipmentPlanRange.create(
                        DateRangeValue.create(
                                Instant.parse("2024-11-08T12:55:59.00Z"),
                                Instant.parse("2024-11-09T12:55:58.00Z"),
                                "这是一个使用parse创建的时间段"
                        ),
                        WorkOrderId.randomId(WorkOrderId.class),
                        WorkProcessId.randomId(WorkProcessId.class)
                )
                );
    }
}
