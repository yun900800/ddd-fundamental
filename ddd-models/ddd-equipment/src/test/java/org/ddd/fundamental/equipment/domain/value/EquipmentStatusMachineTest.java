package org.ddd.fundamental.equipment.domain.value;

import com.alibaba.cola.statemachine.StateMachine;
import org.ddd.fundamental.core.machine.Context;
import org.junit.Test;

public class EquipmentStatusMachineTest {

    private EquipmentStatusMachine machineFactory = new EquipmentStatusMachine();

    @Test
    public void testStatusMachineAction() {
        StateMachine<EquipmentStatus, EquipmentStatusEvent, Context>
                machine = machineFactory.createMachine();
        machine.fireEvent(EquipmentStatus.ENABLED,
                EquipmentStatusEvent.ENABLED_TO_LOCKED_EVENT,new EquipmentStatusMachine.EquipmentContext());
        machine.showStateMachine();

    }
}
