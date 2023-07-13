package org.ddd.fundamental.share.domain.bus.command;

public interface CommandBus {
    void dispatch(Command command)
            throws CommandHandlerExecutionError;
}
