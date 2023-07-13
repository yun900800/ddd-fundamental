package org.ddd.fundamental.share.domain.bus.event;

import org.junit.Assert;
import org.junit.Test;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class DomainEventTest {

    @Test
    public void testCreateDomainEvent() {
        DomainEvent event = new EmptyDomainEvent("", "12590888");
        HashMap<String,Serializable> map = event.toPrimitives();
        Assert.assertEquals(map.keySet().size(),4);

        DomainEvent newEvent = event.fromPrimitives(event.aggregateId(),map,event.eventId(),event.occurredOn());
        Assert.assertNotEquals(event,newEvent);

        Assert.assertEquals(event.eventName(),"EmptyDomainEvent");
    }
}

class EmptyDomainEvent extends DomainEvent {

    private String empty;

    public EmptyDomainEvent(String empty,String aggregateId,
                            String eventId, String createOn) {
        super(aggregateId,eventId,createOn);
        this.empty = empty;
    }

    public EmptyDomainEvent(String empty,String aggregateId) {
        super(aggregateId);
        this.empty = empty;
    }

    @Override
    public String eventName() {
        return "EmptyDomainEvent";
    }

    @Override
    public HashMap<String, Serializable> toPrimitives() {
        HashMap<String, Serializable> map = new HashMap<>();
        map.put("empty", this.empty);
        map.put("aggregateId", aggregateId());
        map.put("eventId", eventId());
        map.put("occurredOn", occurredOn());
        return map;
    }

    @Override
    public DomainEvent fromPrimitives(String aggregateId, HashMap<String, Serializable> body, String eventId, String occurredOn) {
        String empty = (String)body.get("empty");
        EmptyDomainEvent emptyDomainEvent = new EmptyDomainEvent(empty,
                aggregateId,eventId,occurredOn);
        return emptyDomainEvent;
    }
}
