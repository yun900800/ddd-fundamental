package org.ddd.fundamental.share.domain;

import org.ddd.fundamental.share.domain.bus.event.DomainEvent;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

public class AggregateRootTest {

    @Test
    public void testRecordAndPullDomainEvents() {
        AggregateRoot absCls = Mockito.spy(
                AggregateRoot.class);
        DomainEvent event = Mockito.mock(
                DomainEvent.class,
                Mockito.CALLS_REAL_METHODS);
        absCls.record(event);
        // 注意这里测试的时候pullDomainEvents只能调用一次
        List<DomainEvent> events = absCls.pullDomainEvents();
        Assert.assertEquals(events.size(),1);
        Assert.assertEquals(event,events.get(0));
        events = absCls.pullDomainEvents();
        Assert.assertEquals(events.size(),0);
    }

    @Test
    public void testMultipleRecordAndPullDomainEvents(){
        AggregateRoot absCls = Mockito.spy(
                AggregateRoot.class);
        DomainEvent event = Mockito.mock(
                DomainEvent.class,
                Mockito.CALLS_REAL_METHODS);
        DomainEvent event1 = Mockito.mock(
                DomainEvent.class,
                Mockito.CALLS_REAL_METHODS);
        absCls.record(event);
        absCls.record(event1);
        absCls.record(event1);
        List<DomainEvent> events = absCls.pullDomainEvents();
        Assert.assertEquals(events.size(),3);
        Assert.assertEquals(event1,events.get(1));
        events = absCls.pullDomainEvents();
        Assert.assertEquals(events.size(),0);
    }


}
