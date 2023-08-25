package org.ddd.fundamental.share.infrastructure.bus.event;

import org.ddd.fundamental.share.domain.bus.event.DomainEvent;
import org.ddd.fundamental.share.domain.bus.event.EmptyDomainEvent;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DomainEventSubscriberInformationTest {

    @Test
    public void testCreateDomainEventSubscriberInformation() {
        List<Class<? extends DomainEvent>> domainEventClassLists = new ArrayList<>();
        domainEventClassLists.add(EmptyDomainEvent.class);
        DomainEventSubscriberInformation information = new DomainEventSubscriberInformation(EmptyDomainEventSubscriber.class,
                domainEventClassLists );
        Assert.assertEquals(EmptyDomainEventSubscriber.class,information.subscriberClass());
        Assert.assertEquals("EmptyDomainEventSubscriber",information.className());
        Assert.assertEquals("infrastructure",information.moduleName());
        Assert.assertEquals("share",information.contextName());
        Assert.assertEquals(domainEventClassLists,information.subscribedEvents());
        Assert.assertEquals("org.ddd.fundamental.share.infrastructure.empty_domain_event_subscriber",information.formatRabbitMqQueueName());
    }
}
