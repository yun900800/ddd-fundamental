package org.ddd.fundamental.share.domain.bus.event;

import org.junit.Assert;
import org.junit.Test;

import java.io.Serializable;
import java.util.HashMap;

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


