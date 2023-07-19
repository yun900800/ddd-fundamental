package org.ddd.fundamental.share.infrastructure.bus.event.rabbitmq;

import org.ddd.fundamental.share.domain.bus.event.DomainEvent;
import org.ddd.fundamental.share.domain.bus.event.EmptyDomainEvent;
import org.ddd.fundamental.share.infrastructure.bus.event.DomainEventJsonDeserializer;
import org.ddd.fundamental.share.infrastructure.bus.event.DomainEventJsonSerializer;
import org.ddd.fundamental.share.infrastructure.bus.event.DomainEventsInformation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.InvocationTargetException;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class RabbitMqPublisherTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    private RabbitMqPublisher rabbitMqPublisher;

    @TestConfiguration
    static class DomainEventJsonDeserializerImplTestContextConfiguration {
        @Bean
        public DomainEventJsonDeserializer domainEventJsonDeserializer(DomainEventsInformation information) {
            return new DomainEventJsonDeserializer(information);
        }
    }
    @MockBean
    private DomainEventsInformation domainEventsInformation;

    @Autowired
    private DomainEventJsonDeserializer domainEventJsonDeserializer;

    @Before
    public void setUp() {
        this.rabbitMqPublisher = new RabbitMqPublisher(rabbitTemplate);
        Class<? extends DomainEvent> event = (Class<? extends DomainEvent>)EmptyDomainEvent.class;
        doReturn(event).when(domainEventsInformation).forName("EmptyDomainEvent");
    }

    @Test
    public void testPublish() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        ArgumentCaptor<Message> argumentCaptor = ArgumentCaptor.forClass(Message.class);
        ArgumentCaptor<String>  exchangeNameArg = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String>  rootingKeyNameArg = ArgumentCaptor.forClass(String.class);
        EmptyDomainEvent event = new EmptyDomainEvent("empty","1000");
        rabbitMqPublisher.publish(event,"fundamental");
        verify(rabbitTemplate).send(exchangeNameArg.capture(),rootingKeyNameArg.capture(),argumentCaptor.capture());
        Assert.assertEquals(exchangeNameArg.getValue(),"fundamental");
        Assert.assertEquals(rootingKeyNameArg.getValue(),"EmptyDomainEvent");
        String res = new String(argumentCaptor.getValue().getBody());
        EmptyDomainEvent event1 = (EmptyDomainEvent)domainEventJsonDeserializer.deserialize(res);
        Assert.assertEquals(event.eventName(), event1.eventName());
        Assert.assertEquals(event.aggregateId(), event1.aggregateId());
    }
}
