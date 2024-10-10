package org.ddd.fundamental.design.chains;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.design.chains.annotation.GenericMessageAnnotation;
import org.ddd.fundamental.design.chains.annotation.MessageAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ChainStepFacade<C> {
    private final ChainStep<C> chainHead;

    @Autowired
    @GenericMessageAnnotation
    private List<ChainStep> genericSteps;

    @Autowired
    @MessageAnnotation
    private List<ChainStep<C>> defaults;

    public ChainStepFacade(@MessageAnnotation List<ChainStep<C>> steps) {
        log.info("size:{}",steps.size());
        this.chainHead = ChainElement.buildChain(steps, new NoOpChainStep());
    }

    public ChainStepFacade changeToGeneric(){
        log.info("genericSteps:{}",genericSteps);
        log.info("defaults:{}",defaults);
        return new ChainStepFacade(genericSteps);
    }

    public ChainStepFacade<C> changeToDefault(){
        log.info("genericSteps:{}",genericSteps);
        log.info("defaults:{}",defaults);
        return new ChainStepFacade(defaults);
    }

    public C handle(C context) {
        return chainHead.handle(context);
    }
}
