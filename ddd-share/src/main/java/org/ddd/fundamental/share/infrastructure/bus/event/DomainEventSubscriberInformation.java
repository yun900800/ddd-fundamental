package org.ddd.fundamental.share.infrastructure.bus.event;

import org.ddd.fundamental.share.domain.Service;
import org.ddd.fundamental.share.domain.Utils;
import org.ddd.fundamental.share.domain.bus.event.DomainEvent;

import java.util.List;
import java.util.Objects;

//@Service
public final class DomainEventSubscriberInformation {
    private final Class<?>                           subscriberClass;
    private final List<Class<? extends DomainEvent>> subscribedEvents;

    public DomainEventSubscriberInformation(
            Class<?> subscriberClass,
            List<Class<? extends DomainEvent>> subscribedEvents
    ) {
        this.subscriberClass  = subscriberClass;
        this.subscribedEvents = subscribedEvents;
    }

    public Class<?> subscriberClass() {
        return subscriberClass;
    }

    public String contextName() {
        String[] nameParts = subscriberClass.getName().split("\\.");

        return nameParts[2];
    }

    public String moduleName() {
        String[] nameParts = subscriberClass.getName().split("\\.");

        return nameParts[3];
    }

    public String className() {
        String[] nameParts = subscriberClass.getName().split("\\.");

        return nameParts[nameParts.length - 1];
    }

    public List<Class<? extends DomainEvent>> subscribedEvents() {
        return subscribedEvents;
    }

    public String formatRabbitMqQueueName() {
        return String.format("ddd.fundamental.%s.%s.%s", contextName(), moduleName(), Utils.toSnake(className()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DomainEventSubscriberInformation that = (DomainEventSubscriberInformation) o;
        return subscriberClass.equals(that.subscriberClass) && subscribedEvents.equals(that.subscribedEvents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subscriberClass, subscribedEvents);
    }
}
