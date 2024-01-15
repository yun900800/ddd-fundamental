package org.ddd.fundamental.share.domain.bus.command;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConcreteCommandBusInterfaceTest extends CommandBusInterfaceTest{

    private static final Logger logger = LoggerFactory.getLogger(ConcreteCommandBusInterfaceTest.class);
    @Override
    public CommandBus createCommandBus() {
        return command -> logger.info("command:{}",command.hashCode());
    }
}
