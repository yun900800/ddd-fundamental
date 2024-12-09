package org.ddd.fundamental.material.consumer;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.event.workorder.WorkOrderEvent;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@KafkaListener(id = "multiGroup" ,topics = "work_order_topic", groupId = "material")
public class WorkOrderConsumerByType {

    @KafkaHandler
    public void handleWorkOrder(WorkOrderEvent event) {
        log.info("WorkProcess received: {} in material",event);
    }

    @KafkaHandler
    public void handleWorkOrders(List<WorkOrderEvent> eventList) {
        log.info("WorkProcess received eventList is: {} in material",eventList);
    }
}
