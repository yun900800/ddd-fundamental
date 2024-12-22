package org.ddd.fundamental.equipment.consumer;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.day.range.DateRangeValue;
import org.ddd.fundamental.equipment.application.command.EquipmentCommandService;
import org.ddd.fundamental.equipment.value.BusinessRange;
import org.ddd.fundamental.equipment.value.business.WorkOrderComposable;
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
                EquipmentId id = (EquipmentId) resource.getId();
                BusinessRange<WorkOrderComposable> businessRange = BusinessRange.create(
                        WorkOrderComposable.create(
                                event.getWorkOrderId(),
                                event.getWorkProcessId()
                        ),
                        DateRangeValue.create(
                                event.getWorkOrderValue().getStartTime(),
                                event.getWorkOrderValue().getEndTime(),
                                "工单占用设备的时间"
                        )
                );

                equipmentService.addBusinessPlanRangeToEquipment(
                        id, businessRange
                );
            }
        }
    }
}
