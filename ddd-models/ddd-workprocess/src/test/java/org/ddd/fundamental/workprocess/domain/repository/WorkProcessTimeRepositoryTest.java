package org.ddd.fundamental.workprocess.domain.repository;

import org.ddd.fundamental.tuple.Tuple;
import org.ddd.fundamental.tuple.TwoTuple;
import org.ddd.fundamental.workprocess.WorkProcessAppTest;
import org.ddd.fundamental.workprocess.domain.model.WorkProcessTimeEntity;
import org.ddd.fundamental.workprocess.enums.WorkProcessTimeState;
import org.ddd.fundamental.workprocess.value.WorkProcessTimeId;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;

public class WorkProcessTimeRepositoryTest extends WorkProcessAppTest {

    @Autowired
    private WorkProcessTimeRepository timeRepository;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    private TwoTuple<WorkProcessTimeId,WorkProcessTimeEntity> directStartProcess(){
        WorkProcessTimeEntity workProcessTime =
                WorkProcessTimeEntity.init(false).directStartProcess();
        timeRepository.persist(workProcessTime);
        return Tuple.tuple(workProcessTime.id(),workProcessTime);
    }

    private TwoTuple<WorkProcessTimeId,WorkProcessTimeEntity> directChangingLine(){
        WorkProcessTimeEntity workProcessTime =
                WorkProcessTimeEntity.init(true).directChangLine();
        timeRepository.persist(workProcessTime);
        return Tuple.tuple(workProcessTime.id(),workProcessTime);
    }

    @Test
    public void testDirectStartProcess(){
        WorkProcessTimeId id = directStartProcess().first;
        WorkProcessTimeEntity queryTime = timeRepository.findById(id).orElse(null);
        Assert.assertNotNull(queryTime.getKeyTime().getStartTime());
        Assert.assertNull(queryTime.getKeyTime().getChangeLineSetTime());
        Assert.assertEquals(queryTime.getKeyTime().getState(), WorkProcessTimeState.WORK_PROCESS_RUNNING);
    }

    @Test
    public void testDirectChangeLine() {
        WorkProcessTimeId id = directChangingLine().first;
        WorkProcessTimeEntity queryTime = timeRepository.findById(id).orElse(null);
        Assert.assertNull(queryTime.getKeyTime().getStartTime());
        Assert.assertNotNull(queryTime.getKeyTime().getChangeLineSetTime());
        Assert.assertEquals(queryTime.getKeyTime().getState(), WorkProcessTimeState.LINE_CHANGED);
    }

    @Test
    public void testStartProcessAfterChangingLine(){
        WorkProcessTimeId id = directChangingLine().first;
        WorkProcessTimeEntity queryTime = timeRepository.findById(id).orElse(null);
        queryTime.startProcessAfterChangingLine();
        Assert.assertNotNull(queryTime.getKeyTime().getStartTime());
        Assert.assertNotNull(queryTime.getKeyTime().getChangeLineSetTime());
        Assert.assertEquals(queryTime.getKeyTime().getState(), WorkProcessTimeState.WORK_PROCESS_RUNNING);
        timeRepository.merge(queryTime);
    }
    @Test
    public void testStartProcessAfterChangingLineThrowException(){
        WorkProcessTimeId id = directStartProcess().first;
        WorkProcessTimeEntity queryTime = timeRepository.findById(id).orElse(null);
        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("请先启动换线后再启动工序");
        queryTime.startProcessAfterChangingLine();
    }

    @Test
    public void testInterruptWorkProcess(){
        WorkProcessTimeId id = directStartProcess().first;
        WorkProcessTimeEntity queryTime = timeRepository.findById(id).orElse(null);
        queryTime.interruptProcess();
        Assert.assertNotNull(queryTime.getKeyTime().getStartTime());
        Assert.assertNull(queryTime.getKeyTime().getChangeLineSetTime());
        Assert.assertNotNull(queryTime.getKeyTime().getInterruptTime());
        Assert.assertEquals(queryTime.getKeyTime().getState(), WorkProcessTimeState.WORK_PROCESS_INTERRUPTED);
        timeRepository.merge(queryTime);
    }
}
