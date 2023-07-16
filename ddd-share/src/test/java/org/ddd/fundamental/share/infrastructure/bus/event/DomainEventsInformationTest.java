package org.ddd.fundamental.share.infrastructure.bus.event;

import org.ddd.extra.ExtraDomainEvent;
import org.ddd.fundamental.share.domain.bus.event.EmptyDomainEvent;
import org.ddd.fundamental.share.domain.course.CourseCreatedDomainEvent;
import org.ddd.fundamental.share.infrastructure.bus.event.DomainEventsInformation;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
public class DomainEventsInformationTest {

    @TestConfiguration
    static class DomainEventsInformationImplTestContextConfiguration {
        @Bean
        public DomainEventsInformation domainEventsInformation() {
            return new DomainEventsInformation();
        }
    }

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Autowired
    private DomainEventsInformation domainEventsInformation;

    @Test
    public void testDomainEventsInformationCreate() {
        Assert.assertNotNull(domainEventsInformation);
    }

    @Test
    public void testForName() {
        Assert.assertEquals(domainEventsInformation.forName("EmptyDomainEvent"), EmptyDomainEvent.class);
        Assert.assertEquals(domainEventsInformation.forName("course.created"), CourseCreatedDomainEvent.class);
    }

    @Test
    public void testForClass() {
        Assert.assertEquals(domainEventsInformation.forClass(EmptyDomainEvent.class),"EmptyDomainEvent");
        Assert.assertEquals(domainEventsInformation.forClass(CourseCreatedDomainEvent.class),"course.created");
    }

    @Test
    public void testEventSize() {
        Assert.assertEquals(Optional.of(domainEventsInformation.eventSize()),Optional.of(2));
    }

    @Test
    public void testAppendScanDomainEventPah() {
        domainEventsInformation.appendScanDomainEventPah("org.ddd.extra");
        Assert.assertEquals(Optional.of(domainEventsInformation.eventSize()),Optional.of(3));
        Assert.assertEquals(domainEventsInformation.forName("ExtraDomainEvent"), ExtraDomainEvent.class);
    }
}
