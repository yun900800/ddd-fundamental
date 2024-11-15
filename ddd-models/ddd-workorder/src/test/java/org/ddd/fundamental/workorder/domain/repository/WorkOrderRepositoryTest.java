package org.ddd.fundamental.workorder.domain.repository;

import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.workorder.WorkOrderAppTest;
import org.ddd.fundamental.workorder.domain.model.WorkOrder;
import org.ddd.fundamental.workorder.enums.WorkOrderType;
import org.ddd.fundamental.workorder.value.WorkOrderValue;
import org.ddd.fundamental.workprocess.value.CraftsmanShipId;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;

public class WorkOrderRepositoryTest extends WorkOrderAppTest {

    @Autowired
    private WorkOrderRepository workOrderRepository;

    @Test
    public void testCreateWorkOrder(){
        WorkOrder workOrder = new WorkOrder(WorkOrderValue.create(
                WorkOrderType.PRODUCT_WORK_ORDER, MaterialId.randomId(MaterialId.class),
                Instant.now(), Instant.now(),10000.0, "深圳市卓越科技有限公司",
                CraftsmanShipId.randomId(CraftsmanShipId.class)

        ));
        workOrderRepository.save(workOrder);
    }
}
