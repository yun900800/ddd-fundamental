package org.ddd.fundamental.design.chains.impl;

import org.ddd.fundamental.design.chains.AbstractChainStep;
import org.ddd.fundamental.design.chains.dto.Message;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Order(2)
class GeoLocationChainStep extends AbstractChainStep<Message> {


    @Override
    protected Optional<Message> enrichAndApplyNext(Message context) {
        System.out.println("GeoLocationEnrichmentStep enrich");
        context = context.with("geo","192.168.1.1");
        return Optional.ofNullable(context);
    }
}
