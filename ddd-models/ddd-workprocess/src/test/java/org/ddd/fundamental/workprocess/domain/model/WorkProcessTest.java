package org.ddd.fundamental.workprocess.domain.model;

import org.ddd.fundamental.workprocess.value.WorkProcessId;
import org.junit.Assert;
import org.junit.Test;

public class WorkProcessTest {

    private WorkProcess createWorkPrecess(){
        WorkProcess workProcess = new WorkProcess("模切工序");
        workProcess.addPre("模切前置工序1")
                .addPre("模切前置工序2")
                .addNext("模切后置工序1")
                .addNext("模切后置工序2");
        return workProcess;
    }

    @Test
    public void testCreateWorkProcess(){
        WorkProcess workProcess = createWorkPrecess();
        Assert.assertEquals(workProcess.getPreProcesses().size(),2,0);
        Assert.assertEquals(workProcess.getNextProcesses().size(),2,0);
    }

    @Test
    public void testRemovePreAndNext(){
        WorkProcess workProcess = createWorkPrecess();
        WorkProcessId preId = null;
        for (WorkProcess process: workProcess.getPreProcesses()){
            if (process.getProcessName().equals("模切前置工序1")){
                preId = process.id();
            }
        }
        WorkProcessId nextId = null;
        for (WorkProcess process: workProcess.getNextProcesses()){
            if (process.getProcessName().equals("模切后置工序2")) {
                nextId = process.id();
            }
        }
        workProcess.removePre(preId);
        Assert.assertEquals(workProcess.getPreProcesses().size(),1,0);
        workProcess.removeNext(nextId);
        Assert.assertEquals(workProcess.getNextProcesses().size(),1,0);
    }

}
