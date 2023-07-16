package org.ddd.fundamental.share.infrastructure.bus.query;


import org.ddd.fundamental.share.domain.bus.command.Command;
import org.ddd.fundamental.share.domain.bus.command.CommandHandlerExecutionError;
import org.ddd.fundamental.share.domain.bus.command.CommandNotRegisteredError;
import org.ddd.fundamental.share.domain.bus.query.Query;
import org.ddd.fundamental.share.domain.bus.query.QueryHandlerExecutionError;
import org.ddd.fundamental.share.domain.bus.query.QueryNotRegisteredError;
import org.ddd.fundamental.share.domain.bus.query.Response;
import org.ddd.fundamental.share.infrastructure.bus.command.CommandHandlersInformation;
import org.ddd.fundamental.share.infrastructure.bus.command.EmptyCommand;
import org.ddd.fundamental.share.infrastructure.bus.command.EmptyCommandHandler;
import org.ddd.fundamental.share.infrastructure.bus.command.InMemoryCommandBus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class InMemoryQueryBusTest {

    @MockBean
    private QueryHandlersInformation queryHandlersInformation;

    @MockBean
    private ApplicationContext applicationContext;

    @TestConfiguration
    static class InMemoryQueryBusImplTestContextConfiguration {
        @Bean
        public InMemoryQueryBus inMemoryQueryBus(QueryHandlersInformation queryHandlersInformation,
                                                     ApplicationContext applicationContext) {
            return new InMemoryQueryBus(queryHandlersInformation,applicationContext);
        }
    }
    @Autowired
    private InMemoryQueryBus inMemoryQueryBus;

    @MockBean
    private EmptyQueryHandler emptyQueryHandler;

    @Mock
    private Response response;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Before
    public void setUp() throws QueryNotRegisteredError {
        Mockito.when(applicationContext.getBean(EmptyQueryHandler.class)).thenReturn(emptyQueryHandler);
        Class commandHandlerClass = EmptyQueryHandler.class;
        Mockito.when(queryHandlersInformation.search(EmptyQuery.class))
                .thenReturn(commandHandlerClass);

    }

    @Test
    public void testAsk() {
        ArgumentCaptor<EmptyQuery> argumentCaptor = ArgumentCaptor.forClass(EmptyQuery.class);
        Mockito.when(emptyQueryHandler.handle(any())).thenReturn(response);
        Query emptyQuery = new EmptyQuery();
        Response res = inMemoryQueryBus.ask(emptyQuery);
        verify(emptyQueryHandler,times(1)).handle(argumentCaptor.capture());
        Assert.assertEquals(emptyQuery,argumentCaptor.getValue());
        Assert.assertEquals(response,res);
    }

    @Test
    public void testAskThrowException() throws QueryNotRegisteredError {
        expectedEx.expect(QueryHandlerExecutionError.class);
        expectedEx.expectMessage("找不到合适的QueryHandler类");
        Mockito.when(queryHandlersInformation.search(EmptyQuery.class))
                .thenThrow(new RuntimeException("找不到合适的QueryHandler类"));
        Query emptyQuery = new EmptyQuery();
        inMemoryQueryBus.ask(emptyQuery);
    }
}
