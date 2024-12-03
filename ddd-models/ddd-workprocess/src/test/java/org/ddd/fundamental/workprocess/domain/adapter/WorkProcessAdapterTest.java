package org.ddd.fundamental.workprocess.domain.adapter;

import org.ddd.fundamental.design.chain.ChainStepFacade;
import org.ddd.fundamental.tuple.Tuple;
import org.ddd.fundamental.tuple.TwoTuple;
import org.ddd.fundamental.workprocess.domain.model.WorkProcess;
import org.ddd.fundamental.workprocess.value.WorkProcessId;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class WorkProcessAdapterTest {

    private TwoTuple<ChainStepFacade<Context>,WorkProcessAdapter> create(){
        WorkProcess workProcess1 = new WorkProcess("工序1");
        WorkProcess workProcess2 = new WorkProcess("工序2");
        workProcess1.addNext(workProcess2);
        workProcess2.addPre(workProcess1);
        WorkProcessAdapter workProcessAdapter1 = new WorkProcessAdapter(workProcess1);
        WorkProcessAdapter workProcessAdapter2 = new WorkProcessAdapter(workProcess2);
        WorkProcess workProcess3 = new WorkProcess("工序3");
        workProcess3.addPre(workProcess2);
        workProcess2.addNext(workProcess3);
        WorkProcessAdapter workProcessAdapter3 = new WorkProcessAdapter(workProcess3);

        ChainStepFacade<Context> chainStepFacade = new ChainStepFacade<>(Arrays.asList(
                workProcessAdapter1,workProcessAdapter2,workProcessAdapter3
        ));
        return Tuple.tuple(chainStepFacade,workProcessAdapter2);
    }

    @Test
    public void createWorkProcessAdapter(){
        TwoTuple<ChainStepFacade<Context>,WorkProcessAdapter> twoTuple = create();
        ChainStepFacade<Context> chainStepFacade = twoTuple.first;
        chainStepFacade.handle(new Context());
        WorkProcessId secondId = twoTuple.second.id();
        WorkProcessAdapter workProcessAdapter = (WorkProcessAdapter)chainStepFacade.findById(secondId);
        Assert.assertEquals(workProcessAdapter,twoTuple.second);
    }

    @Test
    public void testChainStepHandle() {
        TwoTuple<ChainStepFacade<Context>,WorkProcessAdapter> twoTuple = create();
        ChainStepFacade<Context> chainStepFacade = twoTuple.first;
        chainStepFacade.handle(new Product("电路板"));
    }
}
