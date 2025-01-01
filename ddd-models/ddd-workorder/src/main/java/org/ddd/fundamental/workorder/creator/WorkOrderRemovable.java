package org.ddd.fundamental.workorder.creator;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.creator.DataRemovable;
import org.ddd.fundamental.workorder.domain.repository.WorkOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class WorkOrderRemovable implements DataRemovable {


    private final WorkOrderRepository workOrderRepository;

    @Autowired(required = false)
    public WorkOrderRemovable(WorkOrderRepository workOrderRepository){
        this.workOrderRepository = workOrderRepository;
    }

    @Transactional
    @Override
    public void execute() {
        log.info("start delete workOrders");
        workOrderRepository.deleteAllWorkOrders();
        log.info("finish delete workOrders");
    }
}
