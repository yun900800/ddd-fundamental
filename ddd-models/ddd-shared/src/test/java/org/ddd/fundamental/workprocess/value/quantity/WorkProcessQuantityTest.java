package org.ddd.fundamental.workprocess.value.quantity;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.StopWatch;

public class WorkProcessQuantityTest {

    @Test
    public void testCreateWorkProcessQuality(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        WorkProcessQuantity workProcessQuality1 =
                WorkProcessQuantity.newBuilder()
                        .targetQuantity(1000)
                        .unQualifiedQuantity(20)
                        .transferQuantityWithOverCross(10)
                        .overCrossQuantity(100)
                        .build();

        Assert.assertNull(workProcessQuality1.getOwePaymentQuantity());

        WorkProcessQuantity workProcessQuality2 =
                WorkProcessQuantity.newBuilder()
                        .targetQuantity(1000)
                        .unQualifiedQuantity(20)
                        .transferQuantityWithOverCross(10)
                        .overCrossQuantity(100)
                        .build();

        Assert.assertNull(workProcessQuality2.getOwePaymentQuantity());

        WorkProcessQuantity workProcessQuality3 =
                WorkProcessQuantity.newBuilder()
                        .targetQuantity(1000)
                        .unQualifiedQuantity(20)
                        .transferQuantityWithOverCross(10)
                        .owePaymentQuantity(100)
                        .build();

        Assert.assertNull(workProcessQuality3.getOverCrossQuantity());
        WorkProcessQuantity workProcessQuality4 =
                WorkProcessQuantity.newBuilder()
                        .targetQuantity(1000)
                        .unQualifiedQuantity(20)
                        .transferQuantityWithOverCross(10)
                        .owePaymentQuantity(98)
                        .build();
        Assert.assertNull(workProcessQuality4.getOverCrossQuantity());

        WorkProcessQuantity workProcessQuality5 =
                WorkProcessQuantity.newBuilder()
                        .targetQuantity(1000)
                        .unQualifiedQuantity(20)
                        .transferQuantityWithOverCross(10)
                        .overCrossQuantity(1080)
                        .build();

        Assert.assertNull(workProcessQuality5.getOwePaymentQuantity());

        stopWatch.stop();
        // 统计执行时间（秒）
        System.out.printf("执行时长：%f 秒.%n", stopWatch.getTotalTimeSeconds()); // %n 为换行
        // 统计执行时间（毫秒）
        System.out.printf("执行时长：%d 毫秒.%n", stopWatch.getTotalTimeMillis());
        // 统计执行时间（纳秒）
        System.out.printf("执行时长：%d 纳秒.%n", stopWatch.getTotalTimeNanos());
    }

    @Test
    public void testCreateWorkProcessQualityWithFactoryMethod(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        WorkProcessQuantity workProcessQuality1 = WorkProcessQuantity.create(1000,20,10,
                1050,null);

        Assert.assertNull(workProcessQuality1.getOwePaymentQuantity());
        WorkProcessQuantity workProcessQuality2 = WorkProcessQuantity.create(1000,20,10,
                1050,null);
        Assert.assertNull(workProcessQuality2.getOwePaymentQuantity());
        stopWatch.stop();
        // 统计执行时间（秒）
        System.out.printf("执行时长：%f 秒.%n", stopWatch.getTotalTimeSeconds()); // %n 为换行
        // 统计执行时间（毫秒）
        System.out.printf("执行时长：%d 毫秒.%n", stopWatch.getTotalTimeMillis());
        // 统计执行时间（纳秒）
        System.out.printf("执行时长：%d 纳秒.%n", stopWatch.getTotalTimeNanos());
    }

    @Test
    public void testChangeQuantity() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        WorkProcessQuantity workProcessQuality1 =
                WorkProcessQuantity.newBuilder()
                        .targetQuantity(1000)
                        .unQualifiedQuantity(20)
                        .transferQuantityWithOverCross(10)
                        .owePaymentQuantity(100)
                        .build();
        Assert.assertEquals(workProcessQuality1.getTargetQuantity(),1000);
        workProcessQuality1 = workProcessQuality1.changeTargetQuantity(2000);
        Assert.assertEquals(workProcessQuality1.getTargetQuantity(),2000);

        Assert.assertEquals(workProcessQuality1.getUnQualifiedQuantity(),20);
        workProcessQuality1 = workProcessQuality1.changeUnQualifiedQuantity(25);
        Assert.assertEquals(workProcessQuality1.getUnQualifiedQuantity(),25);

        Assert.assertEquals(workProcessQuality1.getTransferQuantity(),10);
        workProcessQuality1 = workProcessQuality1.changeTransferQuantity(8);
        Assert.assertEquals(workProcessQuality1.getTransferQuantity(),8);
        stopWatch.stop();
        // 统计执行时间（秒）
        System.out.printf("执行时长：%f 秒.%n", stopWatch.getTotalTimeSeconds()); // %n 为换行
        // 统计执行时间（毫秒）
        System.out.printf("执行时长：%d 毫秒.%n", stopWatch.getTotalTimeMillis());
        // 统计执行时间（纳秒）
        System.out.printf("执行时长：%d 纳秒.%n", stopWatch.getTotalTimeNanos());
    }

    @Test
    public void testChangeOverCross() {
        WorkProcessQuantity workProcessQuality1 =
                WorkProcessQuantity.newBuilder()
                        .targetQuantity(1000)
                        .unQualifiedQuantity(20)
                        .transferQuantityWithOverCross(10)
                        .overCrossQuantity(1050)
                        .build();

        Assert.assertEquals(workProcessQuality1.getOverCrossQuantity(),1050,0);
    }

    @Test
    public void testWorkProcessTimeClone(){
        WorkProcessQuantity workProcessQuality1 =
                WorkProcessQuantity.newBuilder()
                        .targetQuantity(1000)
                        .unQualifiedQuantity(20)
                        .transferQuantityWithOverCross(10)
                        .owePaymentQuantity(100)
                        .build();
        WorkProcessQuantity workProcessQuality2 = workProcessQuality1.clone();
        Assert.assertFalse(workProcessQuality1==workProcessQuality2);
    }
}
