package org.ddd.fundamental.design.chains.impl;

import org.ddd.fundamental.design.chains.AbstractChainStep;
import org.ddd.fundamental.design.chains.annotation.GenericMessageAnnotation;
import org.ddd.fundamental.design.chains.dto.GenericMessage;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Order(3)
@GenericMessageAnnotation
public class PhoneNumberChainStepGeneric extends AbstractChainStep<GenericMessage> {
    @Override
    protected Optional<GenericMessage> enrichAndApplyNext(GenericMessage context) {
        System.out.println("PhoneNumberChainStepGeneric enrich");
        context = context.with("phoneNumber", "13570863934");
        return Optional.ofNullable(context);
    }
}
