package org.ddd.fundamental.workprocess.value.time;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.day.range.DateTimeRange;
import org.junit.Assert;
import org.junit.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
public class WorkingHourValueTest {

    @Test
    public void createWorkingHour(){
        Instant instant = Instant.now();
        WorkingHourValue workingHour =
                WorkingHourValue.create(
                        2.5,
                        2.0,
                        DateTimeRange.create(
                                instant,
                                instant.plus(2, ChronoUnit.HOURS)

                        )
                );
        log.info("workingHour is {}",workingHour);

        WorkingHourValue workingHourValue =
                WorkingHourValue.createFromStart(2.5);
        Assert.assertEquals(workingHourValue.getActualHours(),0,0);
        Assert.assertNull(workingHourValue.getTimeRange().getEnd());
        Instant end = workingHourValue.getTimeRange().getStart().plus(120, ChronoUnit.MINUTES);

        workingHourValue.finish(end);
        Assert.assertEquals(workingHourValue.getActualHours(),2,0);
        Assert.assertNotNull(workingHourValue.getTimeRange().getEnd());
        log.info("workingHourValue is {}",workingHourValue);
    }
}
