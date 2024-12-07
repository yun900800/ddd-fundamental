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
                .when(checkLineChangedCondition())
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
                .when(checkAfterInterruptCondition())
                .perform(doCheckAfterInterruptAction());

        // 从中断到运行或者从检查到运行
        builder.externalTransitions().fromAmong(
                WorkProcessTimeState.WORK_PROCESS_CHECKED,
                WorkProcessTimeState.WORK_PROCESS_INTERRUPTED
        ).to(WorkProcessTimeState.WORK_PROCESS_RUNNING)
                .on(WorkProcessTimeEvent.WORK_PROCESS_RESTART_EVENT)
                .when(checkRestartCondition())
                .perform(doRestartAction());

        // 从运行,中断,检查到结束
        builder.externalTransitions().fromAmong(
                WorkProcessTimeState.WORK_PROCESS_CHECKED,
                WorkProcessTimeState.WORK_PROCESS_INTERRUPTED,
                WorkProcessTimeState.WORK_PROCESS_RUNNING
        ).to(WorkProcessTimeState.WORK_PROCESS_FINISHED)
                .on(WorkProcessTimeEvent.WORK_PROCESS_FINISH_EVENT)
                .when(checkFinishCondition())
                .perform(doFinishAction());

        // 从结束到下线
        builder.externalTransition().from(WorkProcessTimeState.WORK_PROCESS_FINISHED)
                .to(WorkProcessTimeState.WORK_PROCESS_OFFLINE)
                .on(WorkProcessTimeEvent.WORK_PROCESS_OFFLINE_EVENT)
                .when(checkOfflineCondition())
                .perform(doOfflineAction());

        // 从下线到运输
        builder.externalTransition().from(WorkProcessTimeState.WORK_PROCESS_OFFLINE)
                .to(WorkProcessTimeState.WORK_PROCESS_TRANSFER)
                .on(WorkProcessTimeEvent.WORK_PROCESS_TRANSFER_EVENT)
                .when(checkTransferCondition())
                .perform(doTransferAction());

        // 从运输到运输结束
        builder.externalTransition().from(WorkProcessTimeState.WORK_PROCESS_TRANSFER)
                .to(WorkProcessTimeState.WORK_PROCESS_TRANSFER_OVER)
                .on(WorkProcessTimeEvent.WORK_PROCESS_TRANSFER_OVER_EVENT)
                .when(checkFinishTransferCondition())
                .perform(doFinishTransferAction());

        StateMachine<WorkProcessTimeState, WorkProcessTimeEvent, Context> stateMachine = builder.build(WORK_PROCESS_TIME_ID);
        return stateMachine;
    }
    public Condition<Context> checkCondition() {
        return context -> true;
    }


    public Action<WorkProcessTimeState, WorkProcessTimeEvent, Context> doAction() {
        return (from, to, event, ctx) -> {
            if (ctx instanceof WorkProcessKeyTime) {
                WorkProcessKeyTime keyTime = (WorkProcessKeyTime)ctx;
                keyTime.changeState(to);
            }
        };
    }

    public Condition<Context> checkLineChangedCondition() {
        return context -> {
            WorkProcessKeyTime keyTime = (WorkProcessKeyTime)context;
            if (!keyTime.getState().equals(WorkProcessTimeState.INIT)) {
                return false;
            }
            return true;
        };
    }

    public Action<WorkProcessTimeState, WorkProcessTimeEvent, Context> doLineChangedAction() {
        return (from, to, event, ctx) -> {
            if (ctx instanceof WorkProcessKeyTime) {
                WorkProcessKeyTime keyTime = (WorkProcessKeyTime)ctx;
                keyTime.directChangingLine();
                keyTime.changeState(to);
            }
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

    public Condition<Context> checkAfterInterruptCondition() {
        return context -> {
            WorkProcessKeyTime keyTime = (WorkProcessKeyTime)context;
            if (!keyTime.getState().equals(WorkProcessTimeState.WORK_PROCESS_INTERRUPTED)){
                return false;
            }
            return true;
        };
    }

    public Action<WorkProcessTimeState, WorkProcessTimeEvent, Context> doCheckAfterInterruptAction(){
        return (from, to, event, ctx) -> {
            WorkProcessKeyTime keyTime = (WorkProcessKeyTime)ctx;
            Instant startTime = keyTime.getInterruptTime();
            keyTime.startCheck(startTime.plusSeconds(3600*2));
            keyTime.changeState(to);
        };
    }

    public Condition<Context> checkRestartCondition() {
        return context -> {
            WorkProcessKeyTime keyTime = (WorkProcessKeyTime)context;
            if (!keyTime.getState().equals(WorkProcessTimeState.WORK_PROCESS_CHECKED) ||
                    !keyTime.getState().equals(WorkProcessTimeState.WORK_PROCESS_INTERRUPTED)){
                return false;
            }
            return true;
        };
    }

    public Action<WorkProcessTimeState, WorkProcessTimeEvent, Context> doRestartAction(){
        return (from, to, event, ctx) -> {
            WorkProcessKeyTime keyTime = (WorkProcessKeyTime)ctx;
            Instant startTime = keyTime.getInterruptTime();
            keyTime.restart(startTime.plusSeconds(3600*2));
            keyTime.changeState(to);
        };
    }

    public Condition<Context> checkFinishCondition() {
        return context -> {
            WorkProcessKeyTime keyTime = (WorkProcessKeyTime)context;
            if (!keyTime.getState().equals(WorkProcessTimeState.WORK_PROCESS_CHECKED)
                    || !keyTime.getState().equals(WorkProcessTimeState.WORK_PROCESS_INTERRUPTED)
                    || !keyTime.getState().equals(WorkProcessTimeState.WORK_PROCESS_FINISHED)){
                return false;
            }
            return true;
        };
    }

    public Action<WorkProcessTimeState, WorkProcessTimeEvent, Context> doFinishAction(){
        return (from, to, event, ctx) -> {
            WorkProcessKeyTime keyTime = (WorkProcessKeyTime)ctx;
            Instant startTime = keyTime.getInterruptTime();
            keyTime.finish(startTime.plusSeconds(3600*4));
            keyTime.changeState(to);
        };
    }

    public Condition<Context> checkOfflineCondition() {
        return context -> {
            WorkProcessKeyTime keyTime = (WorkProcessKeyTime)context;
            if (!keyTime.getState().equals(WorkProcessTimeState.WORK_PROCESS_FINISHED)){
                return false;
            }
            return true;
        };
    }

    public Action<WorkProcessTimeState, WorkProcessTimeEvent, Context> doOfflineAction(){
        return (from, to, event, ctx) -> {
            WorkProcessKeyTime keyTime = (WorkProcessKeyTime)ctx;
            Instant startTime = keyTime.getInterruptTime();
            keyTime.startOffline(startTime.plusSeconds(3600*6));
            keyTime.changeState(to);
        };
    }

    public Condition<Context> checkTransferCondition() {
        return context -> {
            WorkProcessKeyTime keyTime = (WorkProcessKeyTime)context;
            if (!keyTime.getState().equals(WorkProcessTimeState.WORK_PROCESS_OFFLINE)){
                return false;
            }
            return true;
        };
    }

    public Action<WorkProcessTimeState, WorkProcessTimeEvent, Context> doTransferAction(){
        return (from, to, event, ctx) -> {
            WorkProcessKeyTime keyTime = (WorkProcessKeyTime)ctx;
            Instant startTime = keyTime.getInterruptTime();
            keyTime.startTransfer(startTime.plusSeconds(3600*8));
            keyTime.changeState(to);
        };
    }

    public Condition<Context> checkFinishTransferCondition() {
        return context -> {
            WorkProcessKeyTime keyTime = (WorkProcessKeyTime)context;
            if (!keyTime.getState().equals(WorkProcessTimeState.WORK_PROCESS_TRANSFER)){
                return false;
            }
            return true;
        };
    }

    public Action<WorkProcessTimeState, WorkProcessTimeEvent, Context> doFinishTransferAction(){
        return (from, to, event, ctx) -> {
            WorkProcessKeyTime keyTime = (WorkProcessKeyTime)ctx;
            Instant startTime = keyTime.getInterruptTime();
            keyTime.finishTransfer(startTime.plusSeconds(3600*9));
            keyTime.changeState(to);
        };
    }


}
