package org.ddd.fundamental.share.domain.bus.command;

import org.ddd.fundamental.share.infrastructure.bus.command.EmptyCommandHandler;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.times;

public abstract class CommandBusInterfaceTest {

    private CommandBus commandBus;
    private Command command;

    public abstract CommandBus createCommandBus();

    @Test
    public void testCommandBusDispatch(){
        command = Mockito.mock(Command.class);
        commandBus = createCommandBus();
        commandBus.dispatch(command);

    }

    @Test(expected = CommandHandlerExecutionError.class)
    public void testCommandBusDispatchWithException(){
        command = Mockito.mock(Command.class);
        commandBus = Mockito.mock(CommandBus.class);
        CommandHandlerExecutionError executionError = new CommandHandlerExecutionError(new RuntimeException("执行出现异常"));
        Mockito.doThrow(executionError).when(commandBus).dispatch(command);
        commandBus.dispatch(command);
        Mockito.verify(commandBus,times(1)).dispatch(command);
    }

}
