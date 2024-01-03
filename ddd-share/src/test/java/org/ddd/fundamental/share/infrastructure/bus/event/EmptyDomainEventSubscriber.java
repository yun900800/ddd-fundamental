package org.ddd.fundamental.share.infrastructure.bus.event;

import org.ddd.fundamental.share.domain.bus.event.DomainEventSubscriber;
import org.ddd.fundamental.share.domain.bus.event.EmptyDomainEvent;

@DomainEventSubscriber({EmptyDomainEvent.class})
public class EmptyDomainEventSubscriber {
}
