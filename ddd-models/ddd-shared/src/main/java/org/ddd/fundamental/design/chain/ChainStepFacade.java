package org.ddd.fundamental.design.chain;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class ChainStepFacade<C> {
    private final ChainStep<C> chainHead;
    public ChainStepFacade(List<ChainStep<C>> steps) {
        log.info("size:{}",steps.size());
        this.chainHead = ChainElement.buildChain(steps, new NoOpChainStep());
    }
    public C handle(C context) {
        return chainHead.handle(context);
    }
}
