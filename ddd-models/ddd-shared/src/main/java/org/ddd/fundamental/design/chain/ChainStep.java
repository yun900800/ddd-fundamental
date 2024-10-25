package org.ddd.fundamental.design.chain;


import org.ddd.fundamental.core.DomainObjectId;

public interface ChainStep<C>  extends ChainElement<ChainStep<C>>{

    <ID extends DomainObjectId> ID id();
    C handle(C context);

}
