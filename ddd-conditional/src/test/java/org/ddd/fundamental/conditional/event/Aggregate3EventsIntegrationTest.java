package org.ddd.fundamental.conditional.event;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EventApplication.class)
public class Aggregate3EventsIntegrationTest {

    @MockBean
    private TestEventHandler eventHandler;
    @Autowired
    private Aggregate3Repository repository;

    // @formatter:off
    @DisplayName("given aggregate extending AbstractAggregateRoot,"
            + " when do domain operation and save twice,"
            + " then an event is published only for the first time")
    // @formatter:on
    @Test
    public void afterDomainEvents() {
        // given
        Aggregate3 aggregate = new Aggregate3();

        // when
        aggregate.domainOperation();
        repository.save(aggregate);
        repository.save(aggregate);

        // then
        verify(eventHandler, times(1)).handleEvent(any(DomainEvent.class));
    }

    // @formatter:off
    @DisplayName("given aggregate extending AbstractAggregateRoot,"
            + " when do domain operation and save,"
            + " then an event is published")
    // @formatter:on
    @Test
    public void domainEvents() {
        // given
        Aggregate3 aggregate = new Aggregate3();

        // when
        aggregate.domainOperation();
        repository.save(aggregate);

        // then
        verify(eventHandler, times(1)).handleEvent(any(DomainEvent.class));
    }
}
