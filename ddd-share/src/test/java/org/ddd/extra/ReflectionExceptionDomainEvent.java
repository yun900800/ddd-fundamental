package org.ddd.extra;


import org.ddd.fundamental.share.domain.bus.event.DomainEvent;

import java.io.Serializable;
import java.util.HashMap;

public class ReflectionExceptionDomainEvent extends DomainEvent {

    private String reflectionField;

    public ReflectionExceptionDomainEvent(String reflectionField) {
        this.reflectionField = reflectionField;
    }

    @Override
    public String eventName() {
        return null;
    }

    @Override
    public HashMap<String, Serializable> toPrimitives() {
        return null;
    }

    @Override
    public DomainEvent fromPrimitives(String aggregateId, HashMap<String, Serializable> body, String eventId, String occurredOn) {
        return null;
    }
}
