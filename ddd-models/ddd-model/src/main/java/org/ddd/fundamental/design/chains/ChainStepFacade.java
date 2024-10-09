package org.ddd.fundamental.design.chains;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChainStepFacade<C> {
    private final ChainStep<C> chainHead;

    public ChainStepFacade(List<ChainStep<C>> steps) {
        if (steps.isEmpty()) {
            chainHead = (ChainStep<C>)new NoOpChainStep<C>();
        } else {
            for (int i = 0; i < steps.size(); i++) {
                var current = steps.get(i);
                var next = i < steps.size() - 1 ? steps.get(i + 1) : ((ChainStep<C>)new NoOpChainStep<C>());
                current.setNext(next);
            }
            chainHead = steps.get(0);
        }
    }

    public C handle(C context) {
        return chainHead.handle(context);
    }
}
