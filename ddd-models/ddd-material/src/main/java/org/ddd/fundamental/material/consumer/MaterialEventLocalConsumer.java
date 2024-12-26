package org.ddd.fundamental.material.consumer;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.event.material.ProductEventCreated;
import org.ddd.fundamental.material.producer.MaterialProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import static org.springframework.transaction.event.TransactionPhase.BEFORE_COMMIT;

@Component
@Slf4j
public class MaterialEventLocalConsumer {

    private final MaterialProducer materialProducer;

    @Autowired(required = false)
    public MaterialEventLocalConsumer(MaterialProducer materialProducer){
        this.materialProducer = materialProducer;
    }

    @TransactionalEventListener(phase = BEFORE_COMMIT)
    public void onProductEventCreated(ProductEventCreated productEventCreated){
        log.info("productEventCreated is {}",productEventCreated);
        materialProducer.sendProductEventCreated(productEventCreated);
    }

}
