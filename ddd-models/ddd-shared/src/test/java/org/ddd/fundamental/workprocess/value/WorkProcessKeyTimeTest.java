package org.ddd.fundamental.workprocess.value;

import org.junit.Assert;
import org.junit.Test;

import java.time.Instant;

public class WorkProcessKeyTimeTest {

    @Test
    public void createWorkProcessKeyTime() {
        WorkProcessKeyTime workProcessKeyTime = WorkProcessKeyTime.create(Instant.parse("2024-11-10T10:34:56.789Z"));
        workProcessKeyTime = workProcessKeyTime.changeInterruptTime(Instant.parse("2024-11-10T16:34:56.789Z"));
        workProcessKeyTime.changeRestartTime(Instant.parse("2024-11-10T18:34:56.789Z"));
        long minutes = workProcessKeyTime.interruptRange();
        Assert.assertEquals(minutes,120,0);
    }

    @Test(expected = RuntimeException.class)
    public void testChangeEndTime() {
        WorkProcessKeyTime workProcessKeyTime = WorkProcessKeyTime.create(Instant.parse("2024-11-10T10:34:56.789Z"));
        workProcessKeyTime.changeEndTime();
        WorkProcessKeyTime workProcessKeyTime1 = WorkProcessKeyTime.create(Instant.parse("2024-11-12T10:34:56.789Z"));
        workProcessKeyTime1.changeEndTime(Instant.parse("2024-11-10T10:34:56.789Z"));
    }

    @Test(expected = RuntimeException.class)
    public void changeInterruptTime() {
        WorkProcessKeyTime workProcessKeyTime = WorkProcessKeyTime.create(Instant.parse("2024-11-10T10:34:56.789Z"));
        workProcessKeyTime.changeInterruptTime();
        WorkProcessKeyTime workProcessKeyTime1 = WorkProcessKeyTime.create(Instant.parse("2024-11-12T10:34:56.789Z"));
        workProcessKeyTime1.changeInterruptTime(Instant.parse("2024-11-10T10:34:56.789Z"));
    }

    @Test
    public void changeRestartTime() {
        WorkProcessKeyTime workProcessKeyTime = WorkProcessKeyTime.create(Instant.parse("2024-11-10T10:34:56.789Z"));
        workProcessKeyTime.changeInterruptTime(Instant.parse("2024-11-10T16:34:56.789Z"));
        workProcessKeyTime.changeRestartTime(Instant.parse("2024-11-10T18:34:56.789Z"));
        Assert.assertEquals(workProcessKeyTime.interruptRange(),120,0);
        workProcessKeyTime.changeRestartTime();
        System.out.println(workProcessKeyTime.interruptRange());
    }
}
