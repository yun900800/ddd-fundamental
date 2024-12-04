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
    public void testWorkProcessTimeStateMachineWithTime() {
        StateMachine<WorkProcessTimeState, WorkProcessTimeEvent, Context> machine = stateMachineFactory.createMachine();

        WorkProcessKeyTime keyTime = WorkProcessKeyTime.changeLineStart();
        WorkProcessTimeState state = machine.fireEvent(WorkProcessTimeState.INIT,WorkProcessTimeEvent.CHANGE_LINE_START_EVENT,
                keyTime);
        Assert.assertEquals(state,WorkProcessTimeState.LINE_CHANGED);
        log.info("keyTime is {}",keyTime);
    }
}
