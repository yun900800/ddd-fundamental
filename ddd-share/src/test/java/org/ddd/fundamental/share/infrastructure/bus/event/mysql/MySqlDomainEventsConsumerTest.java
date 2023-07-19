package org.ddd.fundamental.share.infrastructure.bus.event.mysql;

import org.ddd.fundamental.share.domain.bus.event.DomainEvent;
import org.ddd.fundamental.share.domain.bus.event.EmptyDomainEvent;
import org.ddd.fundamental.share.infrastructure.bus.event.DomainEventsInformation;
import org.ddd.fundamental.share.infrastructure.bus.event.rabbitmq.RabbitMqDomainEventsConsumer;
import org.ddd.fundamental.share.infrastructure.bus.event.rabbitmq.RabbitMqPublisher;
import org.ddd.fundamental.share.infrastructure.bus.event.spring.SpringApplicationEventBus;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@DataJpaTest
@RunWith(SpringRunner.class)
public class MySqlDomainEventsConsumerTest {
    @MockBean
    private RabbitMqDomainEventsConsumer rabbitMqDomainEventsConsumer;

    @MockBean
    RabbitMqPublisher rabbitMqPublisher;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private DomainEventsInformation domainEventsInformation;

    @Mock
    private SpringApplicationEventBus springApplicationEventBus;


    private MySqlEventBus eventBus;

    private MySqlDomainEventsConsumer mySqlDomainEventsConsumer;

    @Before
    public void setUp() {
        this.eventBus = new MySqlEventBus(sessionFactory);
        this.mySqlDomainEventsConsumer = new MySqlDomainEventsConsumer(sessionFactory,domainEventsInformation,springApplicationEventBus);
        sessionFactory.getCurrentSession().createSQLQuery("" +
                " CREATE TABLE IF NOT EXISTS domain_events (\n" +
                "    id CHAR(36) NOT NULL,\n" +
                "    aggregate_id VARCHAR(36) NOT NULL,\n" +
                "    name VARCHAR(255) NOT NULL,\n" +
                "    body VARCHAR(1000) NOT NULL,\n" +
                "    occurred_on VARCHAR(255) NOT NULL,\n" +
                "    PRIMARY KEY (id)\n" +
                ");").executeUpdate();
        List<DomainEvent> eventList = new ArrayList<>();
        for (int i = 0 ; i< 50; i++) {
            EmptyDomainEvent event = new EmptyDomainEvent("empty:" + i,"1000"+i);
            eventList.add(event);
        }
        this.eventBus.publish(eventList);
    }

    @Test
    public void testConsume() {
        new Thread(()->{
            try {
                TimeUnit.MILLISECONDS.sleep(50);
                mySqlDomainEventsConsumer.stop();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        mySqlDomainEventsConsumer.consume();


    }

    @After
    public void tearDown() {
        sessionFactory.getCurrentSession().createSQLQuery("" +
                " DROP TABLE domain_events ;").executeUpdate();
    }
}
