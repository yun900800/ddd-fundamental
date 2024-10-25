package org.ddd.fundamental.design.chain;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.core.DomainObjectId;

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

    public <ID extends DomainObjectId> ChainStep<C> findById(ID id){
        ChainStep<C> current = chainHead;
        while (!current.id().equals(id)){
            current = current.getNext();
        }
        return current;
    }
}
