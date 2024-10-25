package org.ddd.fundamental.workprocess.domain.adapter;

import org.ddd.fundamental.design.chain.ChainStepFacade;
import org.ddd.fundamental.design.chain.NoOpChainStep;
import org.ddd.fundamental.workprocess.domain.model.WorkProcess;
import org.junit.Test;

import java.util.Arrays;

public class WorkProcessAdapterTest {

    @Test
    public void createWorkProcessAdapter(){
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

        //NoOpChainStep<Context> opChainStep = new NoOpChainStep();
        ChainStepFacade<Context> chainStepFacade = new ChainStepFacade<>(Arrays.asList(
                workProcessAdapter1,workProcessAdapter2,workProcessAdapter3
        ));
        chainStepFacade.handle(new Context());
    }
}
