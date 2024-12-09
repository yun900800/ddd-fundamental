package org.ddd.fundamental.workprocess.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.event.workorder.WorkOrderEvent;
import org.ddd.fundamental.workprocess.application.WorkProcessRecordApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@KafkaListener(id = "multiGroup" ,topics = "work_order_topic", groupId = "workProcess")
public class WorkOrderConsumerByType {

    @Autowired
    private WorkProcessRecordApplication application;

    @Autowired
    private ObjectMapper mapper;

    @KafkaHandler
    public void handleWorkOrder(WorkOrderEvent event) {
        log.info("WorkProcess received: {} in workProcess",event);
        application.createWorkProcessRecords(event.getData().getProductId(),
                event.getData().getCraftsmanShipId(),
                event.getWorkOrderId(),
                event.getData()
        );
    }

    @KafkaHandler
    public void handleWorkOrders(List<WorkOrderEvent> eventList) throws JsonProcessingException {
        log.info("WorkProcess received eventList is : {} in workProcess",eventList);
        CollectionType javaType = mapper.getTypeFactory().constructCollectionType(List.class,WorkOrderEvent.class);
        List<WorkOrderEvent> dataList = mapper.readValue(mapper.writeValueAsString(eventList),javaType);
        for(WorkOrderEvent event: dataList){
            application.createWorkProcessRecords(event.getData().getProductId(),
                    event.getData().getCraftsmanShipId(),
                    event.getWorkOrderId(),
                    event.getData()
            );
        }
    }
}
