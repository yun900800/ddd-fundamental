package org.ddd.fundamental.equipment.consumer;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.equipment.application.command.EquipmentCommandService;
import org.ddd.fundamental.event.workprocess.WorkProcessRecordCreated;
import org.ddd.fundamental.factory.EquipmentId;
import org.ddd.fundamental.workprocess.enums.ProductResourceType;
import org.ddd.fundamental.workprocess.value.resources.ProductResource;
import org.ddd.fundamental.workprocess.value.resources.ProductResources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@KafkaListener(id = "multiGroup" ,topics = "work_process_topic", groupId = "equipment")
public class WorkProcessRecordConsumer {

    @Autowired
    private EquipmentCommandService equipmentService;

    @KafkaHandler
    public void handleWorkProcess(WorkProcessRecordCreated event) {
        log.info("WorkProcessRecordCreated received: {} in equipment",event);
        ProductResources productResources = event.getProductResources();
        for (ProductResource resource: productResources.getResources()) {
            if (ProductResourceType.EQUIPMENT.equals(resource.getResourceType())) {
                EquipmentId id = (EquipmentId)resource.getId();
                equipmentService.addWorkOrderPlanToEquipment(id,
                        event.getWorkOrderId(),
                        event.getWorkProcessId(),
                        event.getWorkOrderValue().getStartTime(),
                        event.getWorkOrderValue().getEndTime());
            }
        }
    }
}
