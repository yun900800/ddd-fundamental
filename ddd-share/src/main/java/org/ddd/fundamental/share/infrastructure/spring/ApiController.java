package org.ddd.fundamental.share.infrastructure.spring;

import org.ddd.fundamental.share.domain.DomainError;
import org.ddd.fundamental.share.domain.bus.command.Command;
import org.ddd.fundamental.share.domain.bus.command.CommandBus;
import org.ddd.fundamental.share.domain.bus.command.CommandHandlerExecutionError;
import org.ddd.fundamental.share.domain.bus.query.Query;
import org.ddd.fundamental.share.domain.bus.query.QueryBus;
import org.ddd.fundamental.share.domain.bus.query.QueryHandlerExecutionError;
import org.springframework.http.HttpStatus;

import java.util.HashMap;

public abstract class ApiController {
    private final QueryBus queryBus;
    private final CommandBus commandBus;

    public ApiController(QueryBus queryBus, CommandBus commandBus) {
        this.queryBus   = queryBus;
        this.commandBus = commandBus;
    }

    protected void dispatch(Command command) throws CommandHandlerExecutionError {
        commandBus.dispatch(command);
    }

    protected <R> R ask(Query query) throws QueryHandlerExecutionError {
        return queryBus.ask(query);
    }

    abstract public HashMap<Class<? extends DomainError>, HttpStatus> errorMapping();
}
