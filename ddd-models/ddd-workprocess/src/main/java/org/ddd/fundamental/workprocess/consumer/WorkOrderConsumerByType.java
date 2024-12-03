package org.ddd.fundamental.workprocess.consumer;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.event.workorder.WorkOrderEvent;
import org.ddd.fundamental.workprocess.application.WorkProcessRecordApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@KafkaListener(id = "multiGroup" ,topics = "work_order_topic", groupId = "workProcess")
public class WorkOrderConsumerByType {

    @Autowired
    private WorkProcessRecordApplication application;

    @KafkaHandler
    public void handleWorkOrder(WorkOrderEvent event) {
        log.info("WorkProcess received: {} in workProcess",event);
        application.createWorkProcessRecords(event.getData().getProductId(),
                event.getData().getCraftsmanShipId());
    }
}
