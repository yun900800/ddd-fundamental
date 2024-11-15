package org.ddd.fundamental.workorder.application;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.workorder.domain.repository.WorkOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WorkOrderApplication {

    @Autowired
    private WorkOrderRepository workOrderRepository;
}
