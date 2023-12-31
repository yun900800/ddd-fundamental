package org.ddd.fundamental.share.infrastructure.bus.event.rabbitmq;

import org.ddd.fundamental.share.domain.bus.event.DomainEvent;
import org.ddd.fundamental.share.domain.bus.event.EventBus;
import org.ddd.fundamental.share.infrastructure.bus.event.mysql.MySqlEventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;

import java.util.Collections;
import java.util.List;

public class RabbitMqEventBus implements EventBus {
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMqEventBus.class);
    private final RabbitMqPublisher publisher;
    private final MySqlEventBus failoverPublisher;
    private final String            exchangeName;

    public RabbitMqEventBus(RabbitMqPublisher publisher, MySqlEventBus failoverPublisher) {
        this.publisher         = publisher;
        this.failoverPublisher = failoverPublisher;
        this.exchangeName      = "domain_events";
    }

    @Override
    public void publish(List<DomainEvent> events) {
        events.forEach(this::publish);
    }

    private void publish(DomainEvent domainEvent) {
        try {
            this.publisher.publish(domainEvent, exchangeName);
        } catch (AmqpException error) {
            LOGGER.error("rabbitMQ send message error:{}",error.getMessage());
            failoverPublisher.publish(Collections.singletonList(domainEvent));
        }
    }
}
