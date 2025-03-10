package org.ddd.fundamental.design.chain;

import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public abstract class AbstractChainStep<C> implements ChainStep<C> {

    private ChainStep<C> next;

    @Override
    public void setNext(ChainStep<C> element) {
        this.next = element;
    }

    @Override
    public ChainStep<C> getNext(){
        return this.next;
    }

    @Override
    public C handle(C context) {
        try {
            return enrichAndApplyNext(context)
                    .map(ctx -> next.handle(ctx))
                    .orElseGet(() -> next.handle(context));
        }
        catch (Exception e) {
            log.error("Unexpected error during handling for msg {}", context, e);
            return next.handle(context);
        }
    }

    protected abstract Optional<C> enrichAndApplyNext(C context);
}
