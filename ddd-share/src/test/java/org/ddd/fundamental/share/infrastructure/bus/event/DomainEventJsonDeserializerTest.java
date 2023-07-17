package org.ddd.fundamental.share.infrastructure.bus.event;

import org.ddd.fundamental.share.domain.bus.event.DomainEvent;
import org.ddd.fundamental.share.domain.bus.event.EmptyDomainEvent;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.InvocationTargetException;

import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
public class DomainEventJsonDeserializerTest {

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
        Class<? extends DomainEvent> event = (Class<? extends DomainEvent>)EmptyDomainEvent.class;
        //http://not4j.com/mocking-method-with-generic-return-type/ 理解doReturn和thenReturn的区别
        //when(domainEventsInformation.forName("EmptyDomainEvent")).thenReturn(event);
        //   以上编译会报错
        doReturn(event).when(domainEventsInformation).forName("EmptyDomainEvent");
    }

    @Test
    public void testDomainEventJsonDeserializerNotNull() {
        Assert.assertNotNull(domainEventJsonDeserializer);
    }



    @Test
    public void testDeserializer() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        String source = "{\"data\":{\"occurred_on\":\"2023-07-17\",\"attributes\":{\"eventId\":\"eventId\",\"aggregateId\":\"1000\",\"id\":\"1000\",\"occurredOn\":\"2023-07-17\",\"empty\":\"empty\"},\"id\":\"eventId\",\"type\":\"EmptyDomainEvent\"},\"meta\":{}}";
        DomainEvent event = domainEventJsonDeserializer.deserialize(source);
        Assert.assertEquals("EmptyDomainEvent",event.eventName());
        Assert.assertEquals("eventId",event.eventId());
        Assert.assertEquals("2023-07-17",event.occurredOn());
        Assert.assertEquals("1000",event.aggregateId());
    }


}
