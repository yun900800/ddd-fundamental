package org.ddd.fundamental.workprocess.value.time;

import com.alibaba.cola.statemachine.Action;
import com.alibaba.cola.statemachine.Condition;
import com.alibaba.cola.statemachine.StateMachine;
import com.alibaba.cola.statemachine.builder.StateMachineBuilder;
import com.alibaba.cola.statemachine.builder.StateMachineBuilderFactory;
import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.core.machine.Context;
import org.ddd.fundamental.workprocess.enums.WorkProcessTimeState;

import java.time.Instant;

@Slf4j
public class WorkProcessTimeStateMachine {

    private static final String WORK_PROCESS_TIME_ID = "work_process_time_id";

    public StateMachine<WorkProcessTimeState, WorkProcessTimeEvent, Context> createMachine(){
        StateMachineBuilder<WorkProcessTimeState, WorkProcessTimeEvent, Context> builder = StateMachineBuilderFactory.create();

        //从init到换线的转换
        builder.externalTransition().from(
                WorkProcessTimeState.INIT
        ).to(WorkProcessTimeState.LINE_CHANGED)
                .on(WorkProcessTimeEvent.CHANGE_LINE_START_EVENT)
                .when(checkCondition())
                .perform(doLineChangedAction());

        // 从init到运行或者从换线到运行
        builder.externalTransitions().fromAmong(
                WorkProcessTimeState.INIT, WorkProcessTimeState.LINE_CHANGED
        ).to(WorkProcessTimeState.WORK_PROCESS_RUNNING)
                .on(WorkProcessTimeEvent.WORK_PROCESS_START_EVENT)
                .when(checkStartProcessCondition())
                .perform(doStartProcessAction());

        // 从运行到中断
        builder.externalTransition().from(WorkProcessTimeState.WORK_PROCESS_RUNNING)
                .to(WorkProcessTimeState.WORK_PROCESS_INTERRUPTED)
                .on(WorkProcessTimeEvent.WORK_PROCESS_INTERRUPT_EVENT)
                .when(checkProcessInterruptCondition())
                .perform(doProcessInterruptAction());

        // 从中断到工序检查
        builder.externalTransition().from(WorkProcessTimeState.WORK_PROCESS_INTERRUPTED)
                .to(WorkProcessTimeState.WORK_PROCESS_CHECKED)
                .on(WorkProcessTimeEvent.WORK_PROCESS_CHECK_EVENT)
                .when(checkCondition())
                .perform(doAction());

        // 从中断到运行或者从检查到运行
        builder.externalTransitions().fromAmong(
                WorkProcessTimeState.WORK_PROCESS_CHECKED,
                WorkProcessTimeState.WORK_PROCESS_INTERRUPTED
        ).to(WorkProcessTimeState.WORK_PROCESS_RUNNING)
                .on(WorkProcessTimeEvent.WORK_PROCESS_RESTART_EVENT)
                .when(checkCondition())
                .perform(doAction());

        // 从运行,中断,检查到结束
        builder.externalTransitions().fromAmong(
                WorkProcessTimeState.WORK_PROCESS_CHECKED,
                WorkProcessTimeState.WORK_PROCESS_INTERRUPTED,
                WorkProcessTimeState.WORK_PROCESS_RUNNING
        ).to(WorkProcessTimeState.WORK_PROCESS_FINISHED)
                .on(WorkProcessTimeEvent.WORK_PROCESS_FINISH_EVENT)
                .when(checkCondition())
                .perform(doAction());

        // 从结束到下线
        builder.externalTransition().from(WorkProcessTimeState.WORK_PROCESS_FINISHED)
                .to(WorkProcessTimeState.WORK_PROCESS_OFFLINE)
                .on(WorkProcessTimeEvent.WORK_PROCESS_OFFLINE_EVENT)
                .when(checkCondition())
                .perform(doAction());

        // 从下线到运输
        builder.externalTransition().from(WorkProcessTimeState.WORK_PROCESS_OFFLINE)
                .to(WorkProcessTimeState.WORK_PROCESS_TRANSFER)
                .on(WorkProcessTimeEvent.WORK_PROCESS_TRANSFER_EVENT)
                .when(checkCondition())
                .perform(doAction());

        // 从运输到运输结束
        builder.externalTransition().from(WorkProcessTimeState.WORK_PROCESS_TRANSFER)
                .to(WorkProcessTimeState.WORK_PROCESS_TRANSFER_OVER)
                .on(WorkProcessTimeEvent.WORK_PROCESS_TRANSFER_OVER_EVENT)
                .when(checkCondition())
                .perform(doAction());

        StateMachine<WorkProcessTimeState, WorkProcessTimeEvent, Context> stateMachine = builder.build(WORK_PROCESS_TIME_ID);
        return stateMachine;
    }
    public Condition<Context> checkCondition() {
        return context -> {
            //System.out.println("Check condition : " + context);
            return true;
        };
    }


    public Action<WorkProcessTimeState, WorkProcessTimeEvent, Context> doAction() {
        return (from, to, event, ctx) -> {
            if (ctx instanceof WorkProcessKeyTime) {
                WorkProcessKeyTime keyTime = (WorkProcessKeyTime)ctx;
                keyTime.changeState(to);
            }
            //log.info("ctx is {}",ctx);
        };
    }

    public Action<WorkProcessTimeState, WorkProcessTimeEvent, Context> doLineChangedAction() {
        return (from, to, event, ctx) -> {
            if (ctx instanceof WorkProcessKeyTime) {
                WorkProcessKeyTime keyTime = (WorkProcessKeyTime)ctx;
                keyTime.directChangingLine();
                keyTime.changeState(to);
            }
            //log.info("ctx is {}",ctx);
        };
    }

    public Condition<Context> checkStartProcessCondition() {
        return context -> {
            WorkProcessKeyTime keyTime = (WorkProcessKeyTime)context;
            if (keyTime.getState().equals(WorkProcessTimeState.LINE_CHANGED)) {
                return keyTime.getChangeLineSetTime() != null;
            }
            return  true;
        };
    }

    public Action<WorkProcessTimeState, WorkProcessTimeEvent, Context> doStartProcessAction() {
        return (from, to, event, ctx) -> {
            if (ctx instanceof WorkProcessKeyTime) {
                WorkProcessKeyTime keyTime = (WorkProcessKeyTime)ctx;
                keyTime.changeState(to);
                if (from.equals(WorkProcessTimeState.LINE_CHANGED)) {
                    Instant now = keyTime.getChangeLineSetTime();
                    keyTime.startProcess(now.plusSeconds(3600*2));
                } else if (from.equals(WorkProcessTimeState.INIT)) {
                    keyTime.directStartProcess(Instant.now());
                }
            }
        };
    }


    public Condition<Context> checkProcessInterruptCondition() {
        return context -> {
            WorkProcessKeyTime keyTime = (WorkProcessKeyTime)context;
            return keyTime.getStartTime() != null;
        };
    }

    public Action<WorkProcessTimeState, WorkProcessTimeEvent, Context> doProcessInterruptAction(){
        return (from, to, event, ctx) -> {
            WorkProcessKeyTime keyTime = (WorkProcessKeyTime)ctx;
            Instant startTime = keyTime.getStartTime();
            keyTime.interrupt(startTime.plusSeconds(3600*2));
            keyTime.changeState(to);
        };
    }
}
