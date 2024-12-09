package org.ddd.fundamental.workprocess.producer;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.event.workprocess.WorkProcessRecordCreated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WorkProcessRecordProducer {

    @Autowired
    private KafkaTemplate<String,Object> multiTypeKafkaTemplate;

    public void sendWorkOrderProcessCreated(WorkProcessRecordCreated event){
        log.info("发送工单工序事件:{}",event);
        multiTypeKafkaTemplate.send("work_process_topic",event);
    }
}
