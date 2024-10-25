package org.ddd.fundamental.workprocess.domain.repository;

import org.ddd.fundamental.workprocess.WorkProcessAppTest;
import org.ddd.fundamental.workprocess.domain.model.WorkProcess;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class WorkProcessRepositoryTest extends WorkProcessAppTest {

    @Autowired
    private WorkProcessRepository repository;

    @Test
    public void testCreateWorkProcess(){
        repository.save(new WorkProcess("模切工序").addPre("模切前置工序1")
                .addPre("模切前置工序2")
                .addNext("模切后置工序1")
                .addNext("模切后置工序2"));

    }
}
