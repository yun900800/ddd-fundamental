package org.ddd.fundamental.material.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

//@Component
@Slf4j
public class WorkOrderConsumer {
    @KafkaListener(topics = "work_order_topic", groupId = "material", containerFactory = "materialKafkaListenerContainerFactory")
    public void handleWorkOrder(String message) {
        log.info("handle workOrder is {} in material",message);
    }
}
