package org.ddd.fundamental.workprocess.value.time;

import com.alibaba.cola.statemachine.StateMachine;
import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.core.machine.Context;
import org.ddd.fundamental.workprocess.enums.WorkProcessTimeState;
import org.junit.Assert;
import org.junit.Test;

@Slf4j
public class WorkProcessTimeStateMachineTest {

    private WorkProcessTimeStateMachine stateMachineFactory = new WorkProcessTimeStateMachine();

    @Test
    public void testWorkProcessTimeStateMachineChangeLine() {
        StateMachine<WorkProcessTimeState, WorkProcessTimeEvent, Context> machine = stateMachineFactory.createMachine();

        WorkProcessKeyTime keyTime = WorkProcessKeyTime.changeLineStart();
        WorkProcessTimeState state = machine.fireEvent(WorkProcessTimeState.INIT,WorkProcessTimeEvent.CHANGE_LINE_START_EVENT,
                keyTime);
        Assert.assertEquals(state,WorkProcessTimeState.LINE_CHANGED);
        log.info("keyTime is {}",keyTime);
    }

    @Test
    public void testWorkProcessTimeStateMachineStartProcess(){
        StateMachine<WorkProcessTimeState, WorkProcessTimeEvent, Context> machine = stateMachineFactory.createMachine();

        WorkProcessKeyTime keyTime = WorkProcessKeyTime.start();
        WorkProcessTimeState state = machine.fireEvent(WorkProcessTimeState.INIT,WorkProcessTimeEvent.WORK_PROCESS_START_EVENT,
                keyTime);
        Assert.assertEquals(state,WorkProcessTimeState.WORK_PROCESS_RUNNING);
        log.info("keyTime is {}",keyTime);

        WorkProcessKeyTime keyTime1 = WorkProcessKeyTime.changeLineStart();
        WorkProcessTimeState state1 = machine.fireEvent(WorkProcessTimeState.LINE_CHANGED,WorkProcessTimeEvent.WORK_PROCESS_START_EVENT,
                keyTime1);
        Assert.assertEquals(state1,WorkProcessTimeState.WORK_PROCESS_RUNNING);
        log.info("keyTime1 is {}",keyTime1);
    }

    @Test
    public void testInterruptTime(){
        StateMachine<WorkProcessTimeState, WorkProcessTimeEvent, Context> machine = stateMachineFactory.createMachine();
        WorkProcessKeyTime keyTime = WorkProcessKeyTime.start();
        WorkProcessTimeState state = machine.fireEvent(WorkProcessTimeState.INIT,WorkProcessTimeEvent.WORK_PROCESS_START_EVENT,
                keyTime);
        WorkProcessTimeState newState = machine.fireEvent(state,WorkProcessTimeEvent.WORK_PROCESS_INTERRUPT_EVENT,
                keyTime);
        Assert.assertEquals(newState,WorkProcessTimeState.WORK_PROCESS_INTERRUPTED);
        log.info("keyTime is {}",keyTime);

        WorkProcessKeyTime keyTime1 = WorkProcessKeyTime.changeLineStart();
        WorkProcessTimeState state1 = machine.fireEvent(WorkProcessTimeState.INIT,WorkProcessTimeEvent.CHANGE_LINE_START_EVENT,
                keyTime1);
        WorkProcessTimeState newState1 = machine.fireEvent(state1,WorkProcessTimeEvent.WORK_PROCESS_START_EVENT,
                keyTime1);
        WorkProcessTimeState newState2 = machine.fireEvent(newState1,WorkProcessTimeEvent.WORK_PROCESS_INTERRUPT_EVENT,
                keyTime1);
        Assert.assertEquals(newState2,WorkProcessTimeState.WORK_PROCESS_INTERRUPTED);
        log.info("keyTime1 is {}",keyTime1);

    }
}
