package org.ddd.fundamental.workprocess.domain.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class ComposeWorkProcessTest {

    @Test
    public void testCreateComposeWorkProcess(){
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

        ComposeWorkProcess composeWorkProcess = new ComposeWorkProcess("组合工序", Arrays.asList(pre,current,next));
        for (WorkProcess workProcess:composeWorkProcess.getNextProcesses()){
            Assert.assertEquals(workProcess,nextNext);
        }
        for (WorkProcess workProcess:composeWorkProcess.getPreProcesses()){
            Assert.assertEquals(workProcess,prePre);
        }
    }
}
