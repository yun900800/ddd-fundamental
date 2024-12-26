package org.ddd.fundamental.material.producer;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.event.material.ProductEventCreated;
import org.ddd.fundamental.event.workprocess.WorkProcessRecordCreated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MaterialProducer {

    private final KafkaTemplate<String,Object> multiTypeKafkaTemplate;

    @Autowired(required = false)
    public MaterialProducer(KafkaTemplate<String,Object> multiTypeKafkaTemplate){
        this.multiTypeKafkaTemplate = multiTypeKafkaTemplate;
    }

    public void sendProductEventCreated(ProductEventCreated event){
        log.info("发送产品创建事件:{}",event);
        multiTypeKafkaTemplate.send("material_topic",event);
    }
}
