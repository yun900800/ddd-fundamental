package org.ddd.fundamental.design.chains.impl;

import org.ddd.fundamental.design.chains.AbstractChainStep;
import org.ddd.fundamental.design.chains.annotation.MessageAnnotation;
import org.ddd.fundamental.design.chains.dto.Message;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Order(1)
@MessageAnnotation
public class PhoneNumberChainStep extends AbstractChainStep<Message> {
    @Override
    protected Optional<Message> enrichAndApplyNext(Message context) {
        System.out.println("PhoneNumberEnrichmentStep enrich");
        context = context.with("phoneNumber", "13570863933");
        return Optional.ofNullable(context);
    }
}
