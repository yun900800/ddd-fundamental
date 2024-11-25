package org.ddd.fundamental.day.range;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.StopWatch;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class DateRangeValueTest {

    @Test
    public void testDateRangeValueCreate(){
        DateRangeValue rangeValue = DateRangeValue.create(
                Instant.parse("2024-11-08T12:55:58.00Z"),
                Instant.parse("2024-11-08T14:55:58.00Z"),
                "这是一个使用parse创建的时间段"
        );
        System.out.println(rangeValue);
    }

    @Test
    public void testMinutes(){
        DateRangeValue rangeValue = DateRangeValue.create(
                Instant.parse("2024-11-08T12:55:58.00Z"),
                Instant.parse("2024-11-08T14:55:58.00Z"),
                "这是一个使用parse创建的时间段"
        );
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (int i = 0 ; i < 2000; i++) {
            rangeValue.minutes();
        }

        stopWatch.stop();
        // 统计执行时间（秒）
        System.out.printf("执行时长：%f 秒.%n", stopWatch.getTotalTimeSeconds()); // %n 为换行
        // 统计执行时间（毫秒）
        System.out.printf("执行时长：%d 毫秒.%n", stopWatch.getTotalTimeMillis());
        // 统计执行时间（纳秒）
        System.out.printf("执行时长：%d 纳秒.%n", stopWatch.getTotalTimeNanos());
        Assert.assertEquals(rangeValue.minutes(),120,0);

        DateRangeValue rangeValue1 = DateRangeValue.create(
                Instant.parse("2024-11-07T12:55:58.00Z"),
                Instant.parse("2024-11-08T14:55:58.00Z"),
                "这是一个使用parse创建的时间段"
        );
        Assert.assertEquals(rangeValue1.minutes(),120+24*60,0);
    }

    @Test
    public void testContainsRange() {
        DateRangeValue range = DateRangeValue.create(
                Instant.parse("2024-11-08T12:55:58.00Z"),
                Instant.parse("2024-11-08T14:55:58.00Z"),
                "这是一个使用parse创建的时间段"
        );
        DateRangeValue other = DateRangeValue.create(
                Instant.parse("2024-11-08T12:55:58.00Z"),
                Instant.parse("2024-11-08T14:55:58.00Z"),
                "这是一个使用parse创建的时间段"
        );
        DateRangeValue other1 = DateRangeValue.create(
                Instant.parse("2024-11-08T14:55:58.00Z"),
                Instant.parse("2024-11-08T15:55:58.00Z"),
                "这是一个使用parse创建的时间段"
        );
        Assert.assertTrue(range.containsRange(other));
        Assert.assertFalse(range.containsRange(other1));
    }

    @Test
    public void testIsAfterRange(){
        DateRangeValue range = DateRangeValue.create(
                Instant.parse("2024-11-08T12:55:58.00Z"),
                Instant.parse("2024-11-08T14:55:58.00Z"),
                "这是一个使用parse创建的时间段"
        );
        DateRangeValue other1 = DateRangeValue.create(
                Instant.parse("2024-11-08T14:55:59.00Z"),
                Instant.parse("2024-11-08T15:55:58.00Z"),
                "这是一个使用parse创建的时间段"
        );
        DateRangeValue other = DateRangeValue.create(
                Instant.parse("2024-11-08T14:55:58.00Z"),
                Instant.parse("2024-11-08T15:55:58.00Z"),
                "这是一个使用parse创建的时间段"
        );
        Assert.assertTrue(other1.isAfterRange(range));
        Assert.assertFalse(other.isAfterRange(range));
    }

    @Test
    public void testIsBeforeRange() {
        DateRangeValue range = DateRangeValue.create(
                Instant.parse("2024-11-08T12:55:58.00Z"),
                Instant.parse("2024-11-08T14:55:58.00Z"),
                "这是一个使用parse创建的时间段"
        );
        DateRangeValue other1 = DateRangeValue.create(
                Instant.parse("2024-11-08T14:55:59.00Z"),
                Instant.parse("2024-11-08T15:55:58.00Z"),
                "这是一个使用parse创建的时间段"
        );
        DateRangeValue other = DateRangeValue.create(
                Instant.parse("2024-11-08T14:55:58.00Z"),
                Instant.parse("2024-11-08T15:55:58.00Z"),
                "这是一个使用parse创建的时间段"
        );
        Assert.assertTrue(range.isBeforeRange(other1));
        Assert.assertFalse(range.isAfterRange(other));
    }

    @Test
    public void testDateRangeStartAndFinish() throws InterruptedException {
        DateRangeValue rangeValue = DateRangeValue.start();
        TimeUnit.SECONDS.sleep(5);
        rangeValue.finish("5秒以后机器故障");
        Assert.assertEquals(rangeValue.minutes(),0);
    }
}

