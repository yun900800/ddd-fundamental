package org.ddd.fundamental.workprocess.domain.model;

import org.ddd.fundamental.design.chain.ChainStep;
import org.ddd.fundamental.design.chain.ChainStepFacade;
import org.ddd.fundamental.tuple.ThreeTuple;
import org.ddd.fundamental.tuple.Tuple;
import org.ddd.fundamental.workprocess.domain.adapter.Context;
import org.ddd.fundamental.workprocess.domain.adapter.WorkProcessAdapter;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ComposeWorkProcessTest {

    private ThreeTuple<ComposeWorkProcess,WorkProcess,WorkProcess> createComposeWorkProcess(){
        WorkProcess current = new WorkProcess("模切工序");
        WorkProcess pre = new WorkProcess("模切前工序1");
        WorkProcess next = new WorkProcess("模切后工序1");
        current.addPre(pre);
        current.addNext(next);
        pre.addNext(current);
        next.addPre(current);
        WorkProcess prePre = new WorkProcess("模切后工序1-1");
        pre.addPre(prePre);
        WorkProcess nextNext = new WorkProcess("模切后工序1-1");
        next.addNext(nextNext);

        WorkProcess next1 = new WorkProcess("模切后工序2");
        next.addNext(next1);
        next1.addPre(next);
        WorkProcess nextNext1 = new WorkProcess("模切后工序2-1");
        next1.addNext(nextNext1);
        return Tuple.tuple(new ComposeWorkProcess("组合工序", Arrays.asList(pre,current,next,next1)),prePre,nextNext1);
    }

    @Test
    public void testCreateComposeWorkProcess(){

        ThreeTuple<ComposeWorkProcess,WorkProcess,WorkProcess> threeTuple = createComposeWorkProcess();
        for (WorkProcess workProcess:threeTuple.first.getNextProcesses()){
            Assert.assertEquals(workProcess,threeTuple.third);
        }
        for (WorkProcess workProcess:threeTuple.first.getPreProcesses()){
            Assert.assertEquals(workProcess,threeTuple.second);
        }
    }

    @Test
    public void testCreateChain() {
        ThreeTuple<ComposeWorkProcess,WorkProcess,WorkProcess> threeTuple = createComposeWorkProcess();
        List<ChainStep<Context>> adapterList =
                threeTuple.first.getProcessList().stream().map(v->new WorkProcessAdapter(v)).collect(Collectors.toList());
        ChainStepFacade<Context> chainStepFacade = new ChainStepFacade<>(adapterList);
        chainStepFacade.handle(new Context());
    }
}
