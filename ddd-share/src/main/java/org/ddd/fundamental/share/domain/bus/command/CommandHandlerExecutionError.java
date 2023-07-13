package org.ddd.fundamental.share.domain.bus.command;

public class CommandHandlerExecutionError extends RuntimeException{

    public CommandHandlerExecutionError(Throwable e) {
        super(e);
    }
}
