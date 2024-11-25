package org.ddd.fundamental.workprocess.domain.value;

import org.ddd.fundamental.day.range.DateRangeValue;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class WorkProcessTimeTest {
    @Test
    public void testCreateWorkProcessExecuteTime(){
        DateRangeValue setTimeRangeValue = DateRangeValue.create(Instant.parse("2024-10-01T09:30:12.00Z"),
                Instant.parse("2024-10-01T09:55:12.00Z"),"工序设置时间");

        DateRangeValue workTimeRangeValue = DateRangeValue.create(Instant.parse("2024-10-01T10:00:12.00Z"),
                Instant.parse("2024-10-01T16:55:12.00Z"),"工序加工时间");

        DateRangeValue offlineTimeValue = DateRangeValue.create(Instant.parse("2024-10-01T16:59:12.00Z"),
                Instant.parse("2024-10-01T18:59:12.00Z"),"工序下线时间");

        WorkProcessExecuteTime executeTime = WorkProcessExecuteTime.create(setTimeRangeValue,workTimeRangeValue,offlineTimeValue);

        WorkProcessExecuteTime copy = executeTime.clone();
        Assert.assertNotSame(executeTime,copy);
        Assert.assertEquals(executeTime,copy);

        executeTime.changeSetTime(workTimeRangeValue);
        Assert.assertNotSame(executeTime,copy);
        Assert.assertNotEquals(executeTime,copy);

        DateRangeValue waitTimeValue = DateRangeValue.create(Instant.parse("2024-10-01T08:00:12.00Z"),
                Instant.parse("2024-10-01T09:00:12.00Z"),"工序等待时间");
        WorkProcessCycle cycle = WorkProcessCycle.create(waitTimeValue,setTimeRangeValue,workTimeRangeValue,offlineTimeValue);
        WorkProcessCycle cycleCopy = cycle.clone();

        Assert.assertNotSame(cycle,cycleCopy);
        Assert.assertEquals(cycle,cycleCopy);

        DateRangeValue transferTimeValue = DateRangeValue.create(Instant.parse("2024-10-01T08:00:12.00Z"),
                Instant.parse("2024-10-01T09:00:12.00Z"),"工序等待时间");
        WorkProcessTime workProcessTime = WorkProcessTime.create(waitTimeValue,setTimeRangeValue,workTimeRangeValue,offlineTimeValue,transferTimeValue);
        WorkProcessTime copyTime = workProcessTime.clone();
        Assert.assertNotSame(workProcessTime,copyTime);
        Assert.assertEquals(workProcessTime,copyTime);
    }

    @Test
    public void testExecuteTime() throws InterruptedException {

        WorkProcessExecuteTime executeTime = new WorkProcessExecuteTime();
        executeTime.startSetTime();
        Assert.assertNull(executeTime.getWorkTime());
        Assert.assertNull(executeTime.getOfflineTime());
        Assert.assertEquals(executeTime.getSetTime().minutes(),0);
        TimeUnit.SECONDS.sleep(1);
        executeTime.finishSetTime("设置时间已经技术");
        Assert.assertEquals(executeTime.getSetTime().range().third,1,0);
        Assert.assertNull(executeTime.getWorkTime());
        Assert.assertNull(executeTime.getOfflineTime());

        executeTime.startWork();
        Assert.assertNotNull(executeTime.getWorkTime());
        Assert.assertNull(executeTime.getOfflineTime());
        TimeUnit.SECONDS.sleep(2);
        executeTime.finishWork();
        Assert.assertEquals(executeTime.getWorkTime().range().third,2,0);
        Assert.assertNull(executeTime.getOfflineTime());

        executeTime.startOffline();
        Assert.assertNotNull(executeTime.getWorkTime());
        Assert.assertNotNull(executeTime.getOfflineTime());
        Assert.assertNotNull(executeTime.getSetTime());
        TimeUnit.SECONDS.sleep(1);
        executeTime.finishOffLine("下线时间结束");
        Assert.assertEquals(executeTime.getOfflineTime().range().third,1,0);
        System.out.println(executeTime.toString());
    }

    @Test
    public void testChangeTime() throws InterruptedException{
        WorkProcessExecuteTime executeTime = new WorkProcessExecuteTime();
        executeTime.startSetTime();
        Instant setStart = Instant.now();
        TimeUnit.SECONDS.sleep(2);
        Instant setEnd = Instant.now();
        executeTime.finishSetTime("花了两秒中设置");
        executeTime.startWork();
        Instant workStart = Instant.now();
        TimeUnit.SECONDS.sleep(2);
        Instant workEnd = Instant.now();
        executeTime.finishWork();
        TimeUnit.SECONDS.sleep(2);
        executeTime.startOffline();
        Instant offStart = Instant.now();
        TimeUnit.SECONDS.sleep(2);
        Instant offEnd = Instant.now();
        executeTime.finishOffLine("花了两秒中下线");

        executeTime.changeSetTime(DateRangeValue.create(setStart,setEnd,"修改设置时间"));
        executeTime.changeWorkTime(DateRangeValue.create(workStart,workEnd,"修改加工时间"));
        executeTime.changeOffLineTime(DateRangeValue.create(offStart,offEnd,"修改下线时间"));
    }

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void testSetTimeThrowException(){
        WorkProcessExecuteTime executeTime = new WorkProcessExecuteTime();
        executeTime.startSetTime();
        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("请先创建一个加工时间");
        executeTime.finishWork();





    }

    @Test
    public void testWorTimeThrowException(){
        WorkProcessExecuteTime executeTime = new WorkProcessExecuteTime();
        executeTime.startSetTime();
        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("请先创建一个加工时间");
        executeTime.startOffline();
    }

    @Test
    public void testOfflineTimeThrowException(){
        WorkProcessExecuteTime executeTime = new WorkProcessExecuteTime();
        executeTime.startSetTime();
        executeTime.finishSetTime("");
        executeTime.startWork();
        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("请先创建一个下线时间");
        executeTime.finishOffLine("");
    }
}
