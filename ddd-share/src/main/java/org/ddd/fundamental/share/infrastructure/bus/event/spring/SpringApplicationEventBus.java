package org.ddd.fundamental.share.infrastructure.bus.event.spring;

import org.ddd.fundamental.share.domain.Service;
import org.ddd.fundamental.share.domain.bus.event.DomainEvent;
import org.ddd.fundamental.share.domain.bus.event.EventBus;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Primary
@Service
public class SpringApplicationEventBus implements EventBus {
    private final ApplicationEventPublisher publisher;

    public SpringApplicationEventBus(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void publish(@NotNull final List<DomainEvent> events) {
        events.forEach(this::publish);
    }

    public void publish(final DomainEvent event) {
        this.publisher.publishEvent(event);
    }
}
