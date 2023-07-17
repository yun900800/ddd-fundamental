package org.ddd.fundamental.share.infrastructure.bus.event;

import org.ddd.fundamental.share.domain.bus.event.EmptyDomainEvent;
import org.junit.Assert;
import org.junit.Test;

public class DomainEventJsonSerializerTest {

    @Test
    public void testSerialize() {
        EmptyDomainEvent event = new EmptyDomainEvent("empty","1000","eventId","2023-07-17");
        String res = DomainEventJsonSerializer.serialize(event);
        Assert.assertEquals("{\"data\":{\"occurred_on\":\"2023-07-17\",\"attributes\":{\"eventId\":\"eventId\",\"aggregateId\":\"1000\",\"id\":\"1000\",\"occurredOn\":\"2023-07-17\",\"empty\":\"empty\"},\"id\":\"eventId\",\"type\":\"EmptyDomainEvent\"},\"meta\":{}}",res);
    }
}
