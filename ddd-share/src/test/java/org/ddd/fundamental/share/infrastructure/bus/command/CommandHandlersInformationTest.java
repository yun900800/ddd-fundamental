package org.ddd.fundamental.share.infrastructure.bus.command;

import org.ddd.fundamental.share.domain.bus.command.CommandNotRegisteredError;
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
public class CommandHandlersInformationTest {

    @TestConfiguration
    static class CommandHandlersInformationImplTestContextConfiguration {
        @Bean
        public CommandHandlersInformation commandHandlersInformation() {
            return new CommandHandlersInformation();
        }
    }

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();
    @Autowired
    private CommandHandlersInformation commandHandlersInformation;

    @Test
    public void testCommandHandlersInformationNotNull() {
        Assert.assertNotNull(commandHandlersInformation);
    }

    @Test
    public void testSearchMethod() throws CommandNotRegisteredError {
        Assert.assertEquals(EmptyCommandHandler.class,
                commandHandlersInformation.search(EmptyCommand.class));
    }

    @Test
    public void testCommandHandlerSize(){
        Assert.assertEquals(Optional.of(1),
                Optional.of(commandHandlersInformation.commandHandlerSize()));
    }

    @Test
    public void testSearchMethodThrowException() throws CommandNotRegisteredError {
        expectedEx.expect(CommandNotRegisteredError.class);
        expectedEx.expectMessage("The command <class org.ddd.fundamental.share.infrastructure.bus.command.ExceptionCommand> hasn't a command handler associated");
        commandHandlersInformation.search(ExceptionCommand.class);
    }
}
