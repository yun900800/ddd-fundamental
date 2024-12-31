package org.ddd.fundamental.workorder.application.command;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.workorder.application.query.WorkOrderQueryService;
import org.ddd.fundamental.workorder.domain.repository.ProductOrderRepository;
import org.ddd.fundamental.workorder.domain.repository.WorkOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class WorkOrderCommandService {


    private final ProductOrderRepository productOrderRepository;

    private final WorkOrderRepository workOrderRepository;

    private final WorkOrderQueryService queryService;

    @Autowired(required = false)
    public WorkOrderCommandService(ProductOrderRepository productOrderRepository,
                                 WorkOrderRepository workOrderRepository,
                                   WorkOrderQueryService queryService){
        this.productOrderRepository = productOrderRepository;
        this.workOrderRepository = workOrderRepository;
        this.queryService = queryService;
    }
}
