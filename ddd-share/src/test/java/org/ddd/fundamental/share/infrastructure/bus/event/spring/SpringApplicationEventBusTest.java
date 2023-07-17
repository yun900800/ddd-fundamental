package org.ddd.fundamental.share.infrastructure.bus.event.spring;

import org.ddd.fundamental.share.domain.bus.event.DomainEvent;
import org.ddd.fundamental.share.domain.bus.event.EmptyDomainEvent;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class SpringApplicationEventBusTest {

    @MockBean
    private ApplicationEventPublisher applicationEventPublisher;

    @TestConfiguration
    static class SpringApplicationEventBusImplTestContextConfiguration {
        @Bean
        @Primary
        public SpringApplicationEventBus springApplicationEventBus(ApplicationEventPublisher applicationEventPublisher) {
            return new SpringApplicationEventBus(applicationEventPublisher);
        }
    }



    @Autowired
    private SpringApplicationEventBus eventBus;

    @Before
    public void setUp() {

    }

    @Test
    public void testSpringApplicationEventBusNotNull() {
        Assert.assertNotNull(eventBus);
    }

    @Test
    public void testPublish() {
        ApplicationEventPublisher applicationEventPublisher1 = Mockito.mock(ApplicationEventPublisher.class);
        SpringApplicationEventBus eventBus1 = new SpringApplicationEventBus(applicationEventPublisher1);
        ArgumentCaptor<EmptyDomainEvent> eventArgumentCaptor = ArgumentCaptor.forClass(EmptyDomainEvent.class);
        EmptyDomainEvent event = new EmptyDomainEvent("empty","1000");
        List<DomainEvent> eventList = new ArrayList<>();
        eventList.add(event);
        eventBus1.publish(eventList);
        verify(applicationEventPublisher1,times(1))
                .publishEvent(eventArgumentCaptor.capture());
        Assert.assertEquals(event,eventArgumentCaptor.getValue());
    }
}
