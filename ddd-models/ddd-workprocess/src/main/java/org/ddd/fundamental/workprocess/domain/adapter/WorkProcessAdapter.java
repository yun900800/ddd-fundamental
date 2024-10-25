package org.ddd.fundamental.workprocess.domain.adapter;

import org.ddd.fundamental.design.chain.AbstractChainStep;
import org.ddd.fundamental.design.chain.ChainStep;
import org.ddd.fundamental.workprocess.domain.model.WorkProcess;

import java.util.Optional;

public class WorkProcessAdapter extends AbstractChainStep<Context> {

    private final WorkProcess workProcess;

    public WorkProcessAdapter(WorkProcess workProcess){
        this.workProcess = workProcess;
    }

    @Override
    protected Optional<Context> enrichAndApplyNext(Context context) {
        Context.increment();
        System.out.println("workProcessId:"+workProcess.id()+" handle something, Context.count="+Context.count);
        return Optional.ofNullable(context);
    }

    @Override
    public boolean acceptNext(ChainStep<Context> element) {
        if (element instanceof WorkProcessAdapter) {
            WorkProcess next = ((WorkProcessAdapter)element).workProcess;
            return workProcess.acceptNext(next);
        }
        return true;
    }

    @Override
    public boolean acceptPre(ChainStep<Context> element) {
        if (element instanceof WorkProcessAdapter) {
            WorkProcess next = ((WorkProcessAdapter)element).workProcess;
            return workProcess.acceptPre(next);
        }
        return true;
    }
}
