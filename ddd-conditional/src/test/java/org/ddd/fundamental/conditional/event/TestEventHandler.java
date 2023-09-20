package org.ddd.fundamental.conditional.event;

import org.springframework.transaction.event.TransactionalEventListener;

public interface TestEventHandler {
    @TransactionalEventListener
    void handleEvent(DomainEvent event);

}
