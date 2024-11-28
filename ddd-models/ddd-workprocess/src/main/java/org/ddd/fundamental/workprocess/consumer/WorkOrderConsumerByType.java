package org.ddd.fundamental.workprocess.consumer;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.event.workorder.WorkOrderEvent;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@KafkaListener(id = "multiGroup" ,topics = "work_order_topic", groupId = "workProcess")
public class WorkOrderConsumerByType {

    @KafkaHandler
    public void handleWorkOrder(WorkOrderEvent event) {
        log.info("WorkProcess received: {} in workProcess",event);
    }
}
