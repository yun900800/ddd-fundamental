package org.ddd.fundamental.share.infrastructure.bus.command;

import org.ddd.fundamental.share.domain.bus.command.CommandHandler;

public class EmptyCommandHandler implements CommandHandler<EmptyCommand> {
    @Override
    public void handle(EmptyCommand command) {

    }
}
