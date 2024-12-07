package org.ddd.fundamental.workprocess.value.time;

import org.junit.Assert;
import org.junit.Test;

import java.time.Instant;

public class WorkProcessKeyTimeTest {

    @Test
    public void createWorkProcessKeyTime() {
        WorkProcessKeyTime workProcessKeyTime = WorkProcessKeyTime.init().directStartProcess(Instant.parse("2024-11-10T10:34:56.789Z"));
        workProcessKeyTime = workProcessKeyTime.interrupt(Instant.parse("2024-11-10T16:34:56.789Z"));
        workProcessKeyTime.restart(Instant.parse("2024-11-10T18:34:56.789Z"));
        long minutes = workProcessKeyTime.interruptRange();
        Assert.assertEquals(minutes,120,0);
    }

    @Test(expected = RuntimeException.class)
    public void testChangeEndTime() {
        WorkProcessKeyTime workProcessKeyTime = WorkProcessKeyTime.init().directStartProcess(Instant.parse("2024-11-10T10:34:56.789Z"));
        workProcessKeyTime.finish();
        WorkProcessKeyTime workProcessKeyTime1 = WorkProcessKeyTime.init().directStartProcess(Instant.parse("2024-11-12T10:34:56.789Z"));
        workProcessKeyTime1.finish(Instant.parse("2024-11-10T10:34:56.789Z"));
    }

    @Test(expected = RuntimeException.class)
    public void changeInterruptTime() {
        WorkProcessKeyTime workProcessKeyTime = WorkProcessKeyTime.init().directStartProcess(Instant.parse("2024-11-10T10:34:56.789Z"));
        workProcessKeyTime.interrupt();
        WorkProcessKeyTime workProcessKeyTime1 = WorkProcessKeyTime.init().directStartProcess(Instant.parse("2024-11-12T10:34:56.789Z"));
        workProcessKeyTime1.interrupt(Instant.parse("2024-11-10T10:34:56.789Z"));
    }

    @Test
    public void changeRestartTime() {
        WorkProcessKeyTime workProcessKeyTime = WorkProcessKeyTime.init().directStartProcess(Instant.parse("2024-11-10T10:34:56.789Z"));
        workProcessKeyTime.interrupt(Instant.parse("2024-11-10T16:34:56.789Z"));
        workProcessKeyTime.restart(Instant.parse("2024-11-10T18:34:56.789Z"));
        Assert.assertEquals(workProcessKeyTime.interruptRange(),120,0);
        workProcessKeyTime.restart();
        System.out.println(workProcessKeyTime.interruptRange());
    }
}
