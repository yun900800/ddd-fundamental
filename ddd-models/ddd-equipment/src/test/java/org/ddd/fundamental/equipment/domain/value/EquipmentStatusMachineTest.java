package org.ddd.fundamental.equipment.domain.value;

import com.alibaba.cola.statemachine.StateMachine;
import org.ddd.fundamental.core.machine.Context;
import org.junit.Test;
import org.springframework.util.StopWatch;

public class EquipmentStatusMachineTest {

    private EquipmentStatusMachine machineFactory = new EquipmentStatusMachine();

    @Test
    public void testStatusMachineAction() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        StateMachine<EquipmentStatus, EquipmentStatusEvent, Context>
                machine = machineFactory.createMachine();
        machine.fireEvent(EquipmentStatus.ENABLED,
                EquipmentStatusEvent.ENABLED_TO_LOCKED_EVENT,new EquipmentStatusMachine.EquipmentContext());
        machine.showStateMachine();
        stopWatch.stop();
        // 统计执行时间（秒）
        System.out.printf("执行时长：%f 秒.%n", stopWatch.getTotalTimeSeconds()); // %n 为换行
        // 统计执行时间（毫秒）
        System.out.printf("执行时长：%d 毫秒.%n", stopWatch.getTotalTimeMillis());
        // 统计执行时间（纳秒）
        System.out.printf("执行时长：%d 纳秒.%n", stopWatch.getTotalTimeNanos());

    }
}
