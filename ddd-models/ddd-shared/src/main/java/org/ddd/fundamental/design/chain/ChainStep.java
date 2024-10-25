package org.ddd.fundamental.design.chain;


public interface ChainStep<C>  extends ChainElement<ChainStep<C>>{

    C handle(C context);

}
