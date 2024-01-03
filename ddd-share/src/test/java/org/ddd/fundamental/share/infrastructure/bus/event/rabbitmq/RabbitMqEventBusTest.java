package org.ddd.fundamental.share.infrastructure.bus.event.rabbitmq;

import org.ddd.fundamental.share.domain.bus.event.DomainEvent;
import org.ddd.fundamental.share.domain.bus.event.EmptyDomainEvent;
import org.ddd.fundamental.share.infrastructure.bus.event.mysql.MySqlEventBus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.amqp.AmqpException;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class RabbitMqEventBusTest {

    @MockBean
    private RabbitMqPublisher rabbitMqPublisher;

    @MockBean
    private MySqlEventBus eventBus;

    private RabbitMqEventBus rabbitMqEventBus;

    @Before
    public void setUp() {
        this.rabbitMqEventBus = new RabbitMqEventBus(rabbitMqPublisher,
                eventBus);
    }

    @Test
    public void testPublish() {
        List<DomainEvent> domainEventList = new ArrayList<>();
        EmptyDomainEvent event = new EmptyDomainEvent("empty","1000");
        domainEventList.add(event);
        rabbitMqEventBus.publish(domainEventList);
        verify(rabbitMqPublisher).publish(any(),any());
    }

    @Test
    public void testPublishException() {
        doThrow(AmqpException.class).when(rabbitMqPublisher).publish(any(),any());
        ArgumentCaptor<List<DomainEvent>> argumentCaptor = ArgumentCaptor.forClass(List.class);
        List<DomainEvent> domainEventList = new ArrayList<>();
        EmptyDomainEvent event = new EmptyDomainEvent("empty","1000");
        domainEventList.add(event);
        rabbitMqEventBus.publish(domainEventList);
        verify(eventBus).publish(argumentCaptor.capture());
        Assert.assertEquals(domainEventList, argumentCaptor.getValue());
        Assert.assertEquals(argumentCaptor.getValue().size(),1);
    }
}
