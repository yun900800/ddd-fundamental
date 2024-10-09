package org.ddd.fundamental.design.chains;

public class NoOpChainStep<C> implements ChainStep<C> {

    @Override
    public C handle(C context) {
        return context;
    }

    @Override
    public void setNext(ChainStep<C> step) {
        // no op
    }
}
