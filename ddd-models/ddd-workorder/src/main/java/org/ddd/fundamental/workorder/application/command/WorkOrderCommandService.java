package org.ddd.fundamental.workorder.application.command;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.workorder.domain.repository.WorkOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WorkOrderCommandService {

    @Autowired
    private WorkOrderRepository workOrderRepository;
}
