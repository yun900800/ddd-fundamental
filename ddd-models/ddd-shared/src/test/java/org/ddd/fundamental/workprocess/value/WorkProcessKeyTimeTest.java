package org.ddd.fundamental.workprocess.value;

import org.junit.Assert;
import org.junit.Test;

import java.time.Instant;

public class WorkProcessKeyTimeTest {

    @Test
    public void createWorkProcessKeyTime() {
        WorkProcessKeyTime workProcessKeyTime = WorkProcessKeyTime.create(Instant.parse("2024-11-10T10:34:56.789Z"));
        workProcessKeyTime = workProcessKeyTime.changeInterruptTime(Instant.parse("2024-11-10T16:34:56.789Z"));
        long minutes = workProcessKeyTime.interruptRange();
        Assert.assertEquals(minutes,360,0);
    }
}
