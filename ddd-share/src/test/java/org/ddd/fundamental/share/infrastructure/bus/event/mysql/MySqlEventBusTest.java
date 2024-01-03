package org.ddd.fundamental.share.infrastructure.bus.event.mysql;


import org.ddd.fundamental.share.domain.bus.event.DomainEvent;
import org.ddd.fundamental.share.domain.bus.event.EmptyDomainEvent;
import org.ddd.fundamental.share.infrastructure.bus.event.rabbitmq.RabbitMqDomainEventsConsumer;
import org.ddd.fundamental.share.infrastructure.bus.event.rabbitmq.RabbitMqPublisher;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@RunWith(SpringRunner.class)
public class MySqlEventBusTest {

    @MockBean
    private RabbitMqDomainEventsConsumer rabbitMqDomainEventsConsumer;

    @MockBean
    RabbitMqPublisher rabbitMqPublisher;

    @Autowired
    private SessionFactory sessionFactory;

    private MySqlEventBus eventBus;

    @Before
    public void setUp() {
        this.eventBus = new MySqlEventBus(sessionFactory);
        sessionFactory.getCurrentSession().createSQLQuery("" +
                " CREATE TABLE IF NOT EXISTS domain_events (\n" +
                "    id CHAR(36) NOT NULL,\n" +
                "    aggregate_id VARCHAR(36) NOT NULL,\n" +
                "    name VARCHAR(255) NOT NULL,\n" +
                "    body VARCHAR(1000) NOT NULL,\n" +
                "    occurred_on VARCHAR(255) NOT NULL,\n" +
                "    PRIMARY KEY (id)\n" +
                ");").executeUpdate();
    }

    @Test
    public void testMySqlEventBusNotNull() {
        Assert.assertNotNull(eventBus);
    }

    @Test
    public void testPublish() {
        EmptyDomainEvent event = new EmptyDomainEvent("empty","1000");
        List<DomainEvent> eventList = new ArrayList<>();
        eventList.add(event);
        this.eventBus.publish(eventList);
    }

    @After
    public void tearDown() {
        sessionFactory.getCurrentSession().createSQLQuery("" +
                " DROP TABLE domain_events ;").executeUpdate();
    }
}
