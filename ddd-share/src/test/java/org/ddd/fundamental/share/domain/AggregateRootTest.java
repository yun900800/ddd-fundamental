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
        Assert.assertEquals(1,events.size());
        Assert.assertEquals(event,events.get(0));
    }

}
