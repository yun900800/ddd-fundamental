package org.ddd.fundamental.share.infrastructure.bus.query;

import org.ddd.fundamental.share.domain.bus.command.CommandNotRegisteredError;
import org.ddd.fundamental.share.domain.bus.query.QueryNotRegisteredError;
import org.ddd.fundamental.share.infrastructure.bus.command.EmptyCommand;
import org.ddd.fundamental.share.infrastructure.bus.command.EmptyCommandHandler;
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
public class QueryHandlersInformationTest {

    @TestConfiguration
    static class QueryHandlersInformationImplTestContextConfiguration {
        @Bean
        public QueryHandlersInformation queryHandlersInformation() {
            return new QueryHandlersInformation();
        }
    }

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Autowired
    private QueryHandlersInformation queryHandlersInformation;

    @Test
    public void testQueryHandlersInformationNotNull() {
        Assert.assertNotNull(queryHandlersInformation);
    }

    @Test
    public void testSearchMethodThrowException() throws QueryNotRegisteredError {
        expectedEx.expect(QueryNotRegisteredError.class);
        expectedEx.expectMessage("The query <class org.ddd.fundamental.share.infrastructure.bus.query.ExceptionQuery> hasn't a query handler associated");
        Assert.assertEquals(EmptyCommandHandler.class,
                queryHandlersInformation.search(ExceptionQuery.class));
    }

    @Test
    public void testSearch() throws QueryNotRegisteredError {
        Assert.assertEquals(EmptyQueryHandler.class,
                queryHandlersInformation.search(EmptyQuery.class));
    }

    @Test
    public void testQueryHandlerSize() {
        Assert.assertEquals(Optional.of(1),
                Optional.of(queryHandlersInformation.queryHandlerSize()));
    }
}
