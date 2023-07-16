package org.ddd.fundamental.share.infrastructure.bus.query;

import org.ddd.fundamental.share.domain.bus.query.QueryHandler;
import org.ddd.fundamental.share.domain.bus.query.Response;

public class EmptyQueryHandler implements QueryHandler<EmptyQuery, Response> {
    @Override
    public Response handle(EmptyQuery query) {
        return null;
    }
}
