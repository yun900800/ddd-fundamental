package org.ddd.fundamental.share.infrastructure.bus.command;


import org.ddd.fundamental.share.domain.bus.command.Command;
import org.ddd.fundamental.share.domain.bus.command.CommandHandler;
import org.ddd.fundamental.share.domain.bus.command.CommandHandlerExecutionError;
import org.ddd.fundamental.share.domain.bus.command.CommandNotRegisteredError;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.internal.verification.Times;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class InMemoryCommandBusTest {

    @MockBean
    private CommandHandlersInformation commandHandlersInformation;

    @MockBean
    private ApplicationContext applicationContext;

    @TestConfiguration
    static class InMemoryCommandBusImplTestContextConfiguration {
        @Bean
        public InMemoryCommandBus inMemoryCommandBus(CommandHandlersInformation commandHandlersInformation,
                                                     ApplicationContext applicationContext) {
            return new InMemoryCommandBus(commandHandlersInformation,applicationContext);
        }
    }

    @Autowired
    private InMemoryCommandBus inMemoryCommandBus;

    @MockBean
    private EmptyCommandHandler emptyCommandHandler;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Before
    public void setUp() throws CommandNotRegisteredError {
        Mockito.when(applicationContext.getBean(EmptyCommandHandler.class)).thenReturn(emptyCommandHandler);
        Class commandHandlerClass = EmptyCommandHandler.class;
        Mockito.when(commandHandlersInformation.search(EmptyCommand.class))
                .thenReturn(commandHandlerClass);

    }

    @Test
    public void testDispatch() {
        ArgumentCaptor<EmptyCommand> argumentCaptor = ArgumentCaptor.forClass(EmptyCommand.class);
        Command emptyCommand = new EmptyCommand();
        inMemoryCommandBus.dispatch(emptyCommand);
        verify(emptyCommandHandler,times(1)).handle(argumentCaptor.capture());
        Assert.assertEquals(emptyCommand,argumentCaptor.getValue());
    }

    @Test
    public void testDispatchThrowException() throws CommandNotRegisteredError {
        expectedEx.expect(CommandHandlerExecutionError.class);
        expectedEx.expectMessage("找不到合适的CommandHandler类");
        Mockito.when(commandHandlersInformation.search(EmptyCommand.class))
                .thenThrow(new RuntimeException("找不到合适的CommandHandler类"));
        Command emptyCommand = new EmptyCommand();
        inMemoryCommandBus.dispatch(emptyCommand);
    }
}
