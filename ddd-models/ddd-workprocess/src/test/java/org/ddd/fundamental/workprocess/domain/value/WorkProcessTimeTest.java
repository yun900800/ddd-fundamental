package org.ddd.fundamental.workprocess.domain.value;

import org.ddd.fundamental.day.range.DateRange;
import org.ddd.fundamental.utils.DateUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class WorkProcessTimeTest {
    @Test
    public void testCreateWorkProcessExecuteTime(){
        DateRange setTimeRange = new DateRange(
                DateUtils.strToDate("2024-10-01 09:30:12","yyyy-MM-dd HH:mm:ss"),
                DateUtils.strToDate("2024-10-01 09:55:12","yyyy-MM-dd HH:mm:ss"),"工序设置时间");
        DateRange workTime = new DateRange(
                DateUtils.strToDate("2024-10-01 10:00:12","yyyy-MM-dd HH:mm:ss"),
                DateUtils.strToDate("2024-10-01 16:55:12","yyyy-MM-dd HH:mm:ss"),"工序加工时间");

        DateRange offlineTime = new DateRange(
                DateUtils.strToDate("2024-10-01 16:59:12","yyyy-MM-dd HH:mm:ss"),
                DateUtils.strToDate("2024-10-01 18:59:12","yyyy-MM-dd HH:mm:ss"),"工序下线时间");

        WorkProcessExecuteTime executeTime = WorkProcessExecuteTime.create(setTimeRange,workTime,offlineTime);

        WorkProcessExecuteTime copy = executeTime.clone();
        Assert.assertNotSame(executeTime,copy);
        Assert.assertEquals(executeTime,copy);

        executeTime.changeSetTime(workTime);
        Assert.assertNotSame(executeTime,copy);
        Assert.assertNotEquals(executeTime,copy);

        DateRange waitTime = new DateRange(
                DateUtils.strToDate("2024-10-01 08:00:12","yyyy-MM-dd HH:mm:ss"),
                DateUtils.strToDate("2024-10-01 09:00:12","yyyy-MM-dd HH:mm:ss"),"工序等待时间");
        WorkProcessCycle cycle = WorkProcessCycle.create(waitTime,setTimeRange,workTime,offlineTime);
        WorkProcessCycle cycleCopy = cycle.clone();

        Assert.assertNotSame(cycle,cycleCopy);
        Assert.assertEquals(cycle,cycleCopy);

        DateRange transferTime = new DateRange(
                DateUtils.strToDate("2024-10-02 08:00:12","yyyy-MM-dd HH:mm:ss"),
                DateUtils.strToDate("2024-10-02 09:00:12","yyyy-MM-dd HH:mm:ss"),"工序运输时间");

        WorkProcessTime workProcessTime = WorkProcessTime.create(waitTime,setTimeRange,workTime,offlineTime,transferTime);
        WorkProcessTime copyTime = workProcessTime.clone();
        Assert.assertNotSame(workProcessTime,copyTime);
        Assert.assertEquals(workProcessTime,copyTime);
    }
}
