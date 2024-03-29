package org.ddd.fundamental.share.infrastructure.bus.event;

import org.apache.commons.collections4.CollectionUtils;
import org.ddd.fundamental.share.domain.bus.event.DomainEvent;
import org.ddd.fundamental.share.domain.bus.event.EmptyDomainEvent;
import org.ddd.fundamental.share.domain.bus.event.UserDomainEvent;
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
                domainEventClassLists);
        //第二个人数据
        List<Class<? extends DomainEvent>> domainEventClassLists1 = new ArrayList<>();
        domainEventClassLists1.add(UserDomainEvent.class);
        DomainEventSubscriberInformation information1 = new DomainEventSubscriberInformation(UserDomainEventSubscriber.class,
                domainEventClassLists1);
        Collection<DomainEventSubscriberInformation> informations = new ArrayList<>();
        informations.add(information);
        informations.add(information1);
        boolean res = CollectionUtils.isEqualCollection(informations,domainEventSubscribersInformation.all());
        Assert.assertTrue(res);

    }

    /**
     * 测试数组比较的时候一定要注意顺序
     */
    @Test
    public void testRabbitMqFormattedNames() {
        Assert.assertArrayEquals(new String[]{"org.ddd.fundamental.share.infrastructure.empty_domain_event_subscriber",
        "org.ddd.fundamental.share.infrastructure.user_domain_event_subscriber"},domainEventSubscribersInformation.rabbitMqFormattedNames());
    }
}
