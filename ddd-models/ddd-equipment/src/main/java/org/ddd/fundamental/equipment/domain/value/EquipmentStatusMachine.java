package org.ddd.fundamental.equipment.domain.value;

import com.alibaba.cola.statemachine.Action;
import com.alibaba.cola.statemachine.Condition;
import com.alibaba.cola.statemachine.StateMachine;
import com.alibaba.cola.statemachine.builder.StateMachineBuilder;
import com.alibaba.cola.statemachine.builder.StateMachineBuilderFactory;
import org.ddd.fundamental.core.machine.Context;

public class EquipmentStatusMachine {

    private static final String MACHINE_ID = "equipment_id";

    public StateMachine<EquipmentStatus, EquipmentStatusEvent, Context> createMachine(){
        StateMachineBuilder<EquipmentStatus, EquipmentStatusEvent, Context> builder = StateMachineBuilderFactory.create();
        builder.externalTransition()
                .from(EquipmentStatus.ENABLED)
                .to(EquipmentStatus.LOCKED)
                .on(EquipmentStatusEvent.ENABLED_TO_LOCKED_EVENT)
                .when(checkCondition())
                .perform(doAction());

        builder.externalTransition()
                .from(EquipmentStatus.ENABLED)
                .to(EquipmentStatus.USED)
                .on(EquipmentStatusEvent.ENABLED_TO_USED_EVENT)
                .when(checkCondition())
                .perform(doAction());

        builder.externalTransition()
                .from(EquipmentStatus.USED)
                .to(EquipmentStatus.ENABLED)
                .on(EquipmentStatusEvent.USED_TO_ENABLED_EVENT)
                .when(checkCondition())
                .perform(doAction());

        builder.externalTransition()
                .from(EquipmentStatus.LOCKED)
                .to(EquipmentStatus.ENABLED)
                .on(EquipmentStatusEvent.LOCKED_TO_ENABLED_EVENT)
                .when(checkCondition())
                .perform(doAction());

        builder.externalTransition()
                .from(EquipmentStatus.LOCKED)
                .to(EquipmentStatus.MAINTAINED)
                .on(EquipmentStatusEvent.LOCKED_TO_MAINTAINED_EVENT)
                .when(checkCondition())
                .perform(doAction());

        builder.externalTransition()
                .from(EquipmentStatus.USED)
                .to(EquipmentStatus.MAINTAINED)
                .on(EquipmentStatusEvent.USED_TO_MAINTAINED_EVENT)
                .when(checkCondition())
                .perform(doAction());

        builder.externalTransition()
                .from(EquipmentStatus.MAINTAINED)
                .to(EquipmentStatus.USED)
                .on(EquipmentStatusEvent.MAINTAINED_TO_USED_EVENT)
                .when(checkCondition())
                .perform(doAction());

        builder.externalTransition()
                .from(EquipmentStatus.USED)
                .to(EquipmentStatus.FAULT)
                .on(EquipmentStatusEvent.USED_TO_FAULT_EVENT)
                .when(checkCondition())
                .perform(doAction());

        builder.externalTransition()
                .from(EquipmentStatus.FAULT)
                .to(EquipmentStatus.REPAIR)
                .on(EquipmentStatusEvent.FAULT_TO_REPAIR_EVENT)
                .when(checkCondition())
                .perform(doAction());

        builder.externalTransition()
                .from(EquipmentStatus.REPAIR)
                .to(EquipmentStatus.USED)
                .on(EquipmentStatusEvent.REPAIR_TO_USED_EVENT)
                .when(checkCondition())
                .perform(doAction());

        StateMachine<EquipmentStatus, EquipmentStatusEvent, Context> stateMachine = builder.build(MACHINE_ID);
        return stateMachine;
    }

    public Action<EquipmentStatus, EquipmentStatusEvent, Context> doAction() {
        return (from, to, event, ctx) -> {
            if (ctx instanceof EquipmentContext) {
                EquipmentContext ectx = (EquipmentContext)ctx;
                System.out.println(
                        ectx.operator + " is operating " + ectx.entityId + " from:" + from + " to:" + to + " on:" + event);
                ectx.status = to.getStatus();
                System.out.println("context status is : " + ectx.status);
            }

        };
    }

    public Condition<Context> checkCondition() {
        return context -> {
            System.out.println("Check condition : " + context);
            return true;
        };
    }

    public static class EquipmentContext extends Context{
        String operator = "frank";
        String entityId = "123465";

        String status = "可用";

        @Override
        public String toString() {
            return "EquipmentContext{" +
                    "operator='" + operator + '\'' +
                    ", entityId='" + entityId + '\'' +
                    ", status='" + status + '\'' +
                    '}';
        }
    }

}


