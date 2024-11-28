package org.ddd.fundamental.workprocess.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WorkOrderConsumer {

    @KafkaListener(topics = "work_order_topic", groupId = "workProcess", containerFactory = "workProcessKafkaListenerContainerFactory")
    public void handleWorkOrder(String message) {
        log.info("handle workOrder is {} in workProcess",message);
    }
}
