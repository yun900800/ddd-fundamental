package org.ddd.fundamental.design.chain;

import org.ddd.fundamental.core.DomainObjectId;

public class NoOpChainStep<C> implements ChainStep<C>{
    @Override
    public void setNext(ChainStep<C> context) {

    }

    @Override
    public ChainStep<C> getNext() {
        return null;
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
    public DomainObjectId id() {
        return null;
    }

    @Override
    public C handle(C context) {
        return context;
    }
}
