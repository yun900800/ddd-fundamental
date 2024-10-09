package org.ddd.fundamental.design.chains;

public interface ChainStep<C> extends ChainElement<ChainStep<C>> {
    C handle(C context);
}
