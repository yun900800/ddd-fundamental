package org.ddd.fundamental.bom.consumer;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.bom.application.command.BomCommandService;
import org.ddd.fundamental.bom.helper.BomHelper;
import org.ddd.fundamental.event.material.ProductEventCreated;

import org.ddd.fundamental.material.value.MaterialType;
import org.ddd.fundamental.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@KafkaListener(id = "multiGroup" ,topics = "material_topic", groupId = "bom")
public class MaterialConsumer {

    private final BomCommandService commandService;

    @Autowired(required = false)
    public MaterialConsumer(BomCommandService commandService){
        this.commandService = commandService;
    }

    @KafkaHandler
    public void handleWorkProcess(ProductEventCreated event) {
        log.info("ProductEventCreated received: {} in bom",event);
        if (event.getMaterialType().equals(MaterialType.PRODUCTION)) {
            log.info("只处理物料类型为产品的数据");
            commandService.createProductBom(event.getProductId(),
                    CollectionUtils.random(BomHelper.spareCount()),
                    CollectionUtils.random(BomHelper.rawCount()));
        }
    }
}
