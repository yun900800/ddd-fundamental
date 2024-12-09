package org.ddd.fundamental.workorder.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.event.workorder.WorkOrderEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
@Slf4j
public class WorkOrderProducer {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @Autowired
    private KafkaTemplate<String,Object> multiTypeKafkaTemplate;

    @Autowired
    private ObjectMapper mapper;

    public void sendWorkOrderByType(WorkOrderEvent event){
        multiTypeKafkaTemplate.send("work_order_topic",event);
    }

    public void sendWorkOrdersByType(List<WorkOrderEvent> eventList){
        multiTypeKafkaTemplate.send("work_order_topic",eventList);
    }

    public void sendWorkOrder(WorkOrderEvent event) {
        String result = "";
        try {
            result = mapper.writeValueAsString(event);
        } catch (JsonProcessingException e){
            log.error("解析json出错：{}", e);
        }
        if (!StringUtils.hasLength(result)){
            return;
        }
        kafkaTemplate.send("work_order_topic",result);
    }

    public void sendWorkOrders(List<WorkOrderEvent> eventList){
        String result = "";
        try {
            result = mapper.writeValueAsString(eventList);
        } catch (JsonProcessingException e){
            log.error("解析json出错：{}", e);
        }
        if (!StringUtils.hasLength(result)){
            return;
        }
        kafkaTemplate.send("work_order_topic",result);
    }
}
