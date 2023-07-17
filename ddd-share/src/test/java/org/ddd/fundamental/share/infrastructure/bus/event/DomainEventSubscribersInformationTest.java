package org.ddd.fundamental.share.infrastructure.bus.event;

import org.apache.commons.collections4.CollectionUtils;
import org.ddd.fundamental.share.domain.bus.event.DomainEvent;
import org.ddd.fundamental.share.domain.bus.event.EmptyDomainEvent;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RunWith(SpringRunner.class)
public class DomainEventSubscribersInformationTest {

    @TestConfiguration
    static class DomainEventSubscribersInformationImplTestContextConfiguration {
        @Bean
        public DomainEventSubscribersInformation domainEventSubscribersInformation() {
            return new DomainEventSubscribersInformation();
        }
    }
    @Autowired
    private DomainEventSubscribersInformation domainEventSubscribersInformation;

    @Test
    public void testDomainEventSubscribersInformationNotNull() {
        Assert.assertNotNull(domainEventSubscribersInformation);
    }

    @Test
    public void testAll() {
        List<Class<? extends DomainEvent>> domainEventClassLists = new ArrayList<>();
        domainEventClassLists.add(EmptyDomainEvent.class);
        DomainEventSubscriberInformation information = new DomainEventSubscriberInformation(EmptyDomainEventSubscriber.class,
                domainEventClassLists );
        Collection<DomainEventSubscriberInformation> informations = new ArrayList<>();
        informations.add(information);
        boolean res = CollectionUtils.isEqualCollection(informations,domainEventSubscribersInformation.all());
        Assert.assertTrue(res);

    }

    @Test
    public void testRabbitMqFormattedNames() {
        Assert.assertArrayEquals(new String[]{"ddd.fundamental.fundamental.share.empty_domain_event_subscriber"},domainEventSubscribersInformation.rabbitMqFormattedNames());
    }
}
