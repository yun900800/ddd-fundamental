package org.ddd.fundamental.design.chain;

public class NoOpChainStep<C> implements ChainStep<C>{
    @Override
    public void setNext(ChainStep<C> context) {

    }

    @Override
    public boolean acceptNext(ChainStep<C> context) {
        return true;
    }

    @Override
    public boolean acceptPre(ChainStep<C> context) {
        return true;
    }

    @Override
    public C handle(C context) {
        return context;
    }
}
