package org.ddd.fundamental.workorder.application.query;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.workorder.domain.repository.ProductOrderRepository;
import org.ddd.fundamental.workorder.domain.repository.WorkOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional(readOnly = true)
public class WorkOrderQueryService {

    private final ProductOrderRepository productOrderRepository;

    private final WorkOrderRepository workOrderRepository;

    @Autowired(required = false)
    public WorkOrderQueryService(ProductOrderRepository productOrderRepository,
                                 WorkOrderRepository workOrderRepository){
        this.productOrderRepository = productOrderRepository;
        this.workOrderRepository = workOrderRepository;
    }
}
