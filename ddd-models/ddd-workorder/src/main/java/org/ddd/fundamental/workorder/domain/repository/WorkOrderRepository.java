package org.ddd.fundamental.workorder.domain.repository;

import org.ddd.fundamental.core.repository.BaseRepository;
import org.ddd.fundamental.workorder.domain.model.WorkOrder;
import org.ddd.fundamental.workorder.value.WorkOrderId;

public interface WorkOrderRepository extends BaseRepository<WorkOrder, WorkOrderId> {
}
