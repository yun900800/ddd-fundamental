package org.ddd.fundamental.design.chains.impl;

import org.ddd.fundamental.design.chains.AbstractChainStep;
import org.ddd.fundamental.design.chains.annotation.GenericMessageAnnotation;
import org.ddd.fundamental.design.chains.dto.GenericMessage;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Order(4)
@GenericMessageAnnotation
public class GeoLocationChainStepOfGeneric extends AbstractChainStep<GenericMessage> {
    @Override
    protected Optional<GenericMessage> enrichAndApplyNext(GenericMessage context) {
        System.out.println("GeoLocationChainStepOfGeneric enrich");
        context = context.with("geo","192.168.1.2");
        return Optional.ofNullable(context);
    }
}
