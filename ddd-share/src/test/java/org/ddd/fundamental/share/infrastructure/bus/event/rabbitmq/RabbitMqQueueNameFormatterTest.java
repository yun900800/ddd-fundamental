package org.ddd.fundamental.share.infrastructure.bus.event.rabbitmq;

import org.ddd.fundamental.share.domain.bus.event.DomainEvent;
import org.ddd.fundamental.share.domain.bus.event.EmptyDomainEvent;
import org.ddd.fundamental.share.infrastructure.bus.event.DomainEventSubscriberInformation;
import org.ddd.fundamental.share.infrastructure.bus.event.EmptyDomainEventSubscriber;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class RabbitMqQueueNameFormatterTest {

    private DomainEventSubscriberInformation information;

    @Before
    public void setUp() {
        List<Class<? extends DomainEvent>> domainEventClassLists = new ArrayList<>();
        domainEventClassLists.add(EmptyDomainEvent.class);
        information = new DomainEventSubscriberInformation(EmptyDomainEventSubscriber.class,
                domainEventClassLists );
    }

    @Test
    public void testFormat() {
        String res = RabbitMqQueueNameFormatter.format(information);
        Assert.assertEquals("org.ddd.fundamental.share.infrastructure.empty_domain_event_subscriber",res);
    }

    @Test
    public void testFormatRetry() {
        String res = RabbitMqQueueNameFormatter.formatRetry(information);
        Assert.assertEquals("retry.org.ddd.fundamental.share.infrastructure.empty_domain_event_subscriber",res);
    }

    @Test
    public void testFormatDeadLetter() {
        String res = RabbitMqQueueNameFormatter.formatDeadLetter(information);
        Assert.assertEquals("dead_letter.org.ddd.fundamental.share.infrastructure.empty_domain_event_subscriber",res);
    }
}
