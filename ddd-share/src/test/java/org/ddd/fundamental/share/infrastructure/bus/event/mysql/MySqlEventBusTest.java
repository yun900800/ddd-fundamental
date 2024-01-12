package org.ddd.fundamental.share.infrastructure.bus.event.mysql;


import org.ddd.fundamental.share.domain.bus.event.DomainEvent;
import org.ddd.fundamental.share.domain.bus.event.EmptyDomainEvent;
import org.ddd.fundamental.share.infrastructure.bus.event.rabbitmq.RabbitMqDomainEventsConsumer;
import org.ddd.fundamental.share.infrastructure.bus.event.rabbitmq.RabbitMqPublisher;
import org.ddd.fundamental.share.infrastructure.config.ParameterNotExist;
import org.ddd.fundamental.share.infrastructure.hibernate.HibernateConfigurationFactory;
import org.ddd.fundamental.share.infrastructure.hibernate.persistent.MySqlEventBusTestConfiguration;
import org.ddd.fundamental.share.infrastructure.hibernate.persistent.TestHibernateConfiguration;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@Import(MySqlEventBusTestConfiguration.class)
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
