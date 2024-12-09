package org.ddd.fundamental.workprocess.consumer;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.event.workprocess.WorkProcessRecordCreated;
import org.ddd.fundamental.workprocess.producer.WorkProcessRecordProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import static org.springframework.transaction.event.TransactionPhase.BEFORE_COMMIT;

@Component
@Slf4j
public class WorkProcessRecordConsumer {

    @Autowired
    private WorkProcessRecordProducer processRecordProducer;

    @TransactionalEventListener(phase = BEFORE_COMMIT)
    public void OnWorkProcessRecordCreated(WorkProcessRecordCreated workProcessRecordCreated){
        log.info("workProcessRecordCreated is {}",workProcessRecordCreated);
        processRecordProducer.sendWorkOrderProcessCreated(workProcessRecordCreated);
    }
}
