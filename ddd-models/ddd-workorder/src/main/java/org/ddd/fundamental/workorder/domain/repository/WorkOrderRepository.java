package org.ddd.fundamental.workorder.domain.repository;

import org.ddd.fundamental.core.repository.BaseRepository;
import org.ddd.fundamental.workorder.domain.model.WorkOrder;
import org.ddd.fundamental.workorder.value.WorkOrderId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface WorkOrderRepository extends BaseRepository<WorkOrder, WorkOrderId> {

    @Modifying
    @Query("delete from WorkOrder")
    void deleteAllWorkOrders();
}
