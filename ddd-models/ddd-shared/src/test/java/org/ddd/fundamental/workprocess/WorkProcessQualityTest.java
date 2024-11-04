package org.ddd.fundamental.workprocess;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.StopWatch;

public class WorkProcessQualityTest {

    @Test
    public void testCreateWorkProcessQuality(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        WorkProcessQuality workProcessQuality1 =
                WorkProcessQuality.newBuilder()
                        .targetQuantity(1000)
                        .unQualifiedQuantity(20)
                        .transferQuantityWithOverCross(10)
                        .withOverCrossQuantity(1050)
                        .noOverCrossPercent()
                        .build();

        Assert.assertNull(workProcessQuality1.getOverCrossPercent());
        Assert.assertNull(workProcessQuality1.getOwePaymentPercent());
        Assert.assertNull(workProcessQuality1.getOwePaymentQuantity());

        WorkProcessQuality workProcessQuality2 =
                WorkProcessQuality.newBuilder()
                        .targetQuantity(1000)
                        .unQualifiedQuantity(20)
                        .transferQuantityWithOverCross(10)
                        .withOverCrossQuantity(1050)
                        .overCrossPercent(5)
                        .build();

        Assert.assertNull(workProcessQuality2.getOwePaymentPercent());
        Assert.assertNull(workProcessQuality2.getOwePaymentQuantity());

        WorkProcessQuality workProcessQuality3 =
                WorkProcessQuality.newBuilder()
                        .targetQuantity(1000)
                        .unQualifiedQuantity(20)
                        .transferQuantityWithOverCross(10)
                        .withOwePaymentQuantity(960)
                        .noOwePaymentPercent()
                        .build();

        Assert.assertNull(workProcessQuality3.getOwePaymentPercent());
        Assert.assertNull(workProcessQuality3.getOverCrossQuantity());
        Assert.assertNull(workProcessQuality3.getOverCrossPercent());
        WorkProcessQuality workProcessQuality4 =
                WorkProcessQuality.newBuilder()
                        .targetQuantity(1000)
                        .unQualifiedQuantity(20)
                        .transferQuantityWithOverCross(10)
                        .withOwePaymentQuantity(960)
                        .owePaymentPercent(98)
                        .build();
        Assert.assertNull(workProcessQuality4.getOverCrossQuantity());
        Assert.assertNull(workProcessQuality4.getOverCrossPercent());

        WorkProcessQuality workProcessQuality5 =
                WorkProcessQuality.newBuilder()
                        .targetQuantity(1000)
                        .unQualifiedQuantity(20)
                        .transferQuantityWithOverCross(10)
                        .withOverCrossPercent(8)
                        .overCrossQuantity(1080)
                        .build();

        Assert.assertNull(workProcessQuality5.getOwePaymentQuantity());
        Assert.assertNull(workProcessQuality5.getOwePaymentPercent());

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
        WorkProcessQuality workProcessQuality1 = WorkProcessQuality.create(1000,20,10,
                1050,null,null,null);
        Assert.assertNull(workProcessQuality1.getOverCrossPercent());
        Assert.assertNull(workProcessQuality1.getOwePaymentPercent());
        Assert.assertNull(workProcessQuality1.getOwePaymentQuantity());
        WorkProcessQuality workProcessQuality2 = WorkProcessQuality.create(1000,20,10,
                1050,null,5,null);
        Assert.assertNull(workProcessQuality2.getOwePaymentPercent());
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
        WorkProcessQuality workProcessQuality1 =
                WorkProcessQuality.newBuilder()
                        .targetQuantity(1000)
                        .unQualifiedQuantity(20)
                        .transferQuantityWithOverCross(10)
                        .withOverCrossQuantity(1050)
                        .noOverCrossPercent()
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
        WorkProcessQuality workProcessQuality1 =
                WorkProcessQuality.newBuilder()
                        .targetQuantity(1000)
                        .unQualifiedQuantity(20)
                        .transferQuantityWithOverCross(10)
                        .withOverCrossQuantity(1050)
                        .noOverCrossPercent()
                        .build();
        workProcessQuality1 = workProcessQuality1.changeOverCrossPercent(6).changeOverCrossQuantity(1060);
        Assert.assertEquals(workProcessQuality1.getOverCrossQuantity(),1060,0);
        Assert.assertEquals(workProcessQuality1.getOverCrossPercent(),6,0);
    }

    @Test(expected = RuntimeException.class)
    public void testChangeOverCrossThrowException() {
        WorkProcessQuality workProcessQuality1 =
                WorkProcessQuality.newBuilder()
                        .targetQuantity(1000)
                        .unQualifiedQuantity(20)
                        .transferQuantityWithOverCross(10)
                        .withOverCrossQuantity(1050)
                        .noOverCrossPercent()
                        .build();
        workProcessQuality1.changeOwePaymentPercent(6).changeOwePaymentQuantity(940);
    }

    @Test
    public void testWorkProcessTimeClone(){
        WorkProcessQuality workProcessQuality1 =
                WorkProcessQuality.newBuilder()
                        .targetQuantity(1000)
                        .unQualifiedQuantity(20)
                        .transferQuantityWithOverCross(10)
                        .withOverCrossQuantity(1050)
                        .noOverCrossPercent()
                        .build();
        WorkProcessQuality workProcessQuality2 = workProcessQuality1.clone();
        Assert.assertFalse(workProcessQuality1==workProcessQuality2);
    }
}
