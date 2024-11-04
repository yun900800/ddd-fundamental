package org.ddd.fundamental.workprocess;

import org.ddd.fundamental.day.range.DateRange;
import org.ddd.fundamental.utils.DateUtils;
import org.junit.Assert;
import org.junit.Test;

public class AuxiliaryWorkTimeTest {

    private static AuxiliaryWorkTime create(){
        DateRange setTimeRange = new DateRange(
                DateUtils.strToDate("2024-10-01 09:30:12","yyyy-MM-dd HH:mm:ss"),
                DateUtils.strToDate("2024-10-01 09:55:12","yyyy-MM-dd HH:mm:ss"),"工序设置时间");
        DateRange offlineTimeRange = new DateRange(
                DateUtils.strToDate("2024-10-01 14:30:12","yyyy-MM-dd HH:mm:ss"),
                DateUtils.strToDate("2024-10-01 16:55:12","yyyy-MM-dd HH:mm:ss"),"工序下线时间");

        DateRange checkTimeRange = new DateRange(
                DateUtils.strToDate("2024-10-01 16:59:12","yyyy-MM-dd HH:mm:ss"),
                DateUtils.strToDate("2024-10-01 18:59:12","yyyy-MM-dd HH:mm:ss"),"工序下线时间");
        AuxiliaryWorkTime auxiliaryWorkTime = AuxiliaryWorkTime.create(setTimeRange,offlineTimeRange,checkTimeRange);
        return auxiliaryWorkTime;
    }

    @Test
    public void testCreateAuxiliaryWorkTime() {
        DateRange setTimeRange = new DateRange(
                DateUtils.strToDate("2024-10-01 09:30:12","yyyy-MM-dd HH:mm:ss"),
                DateUtils.strToDate("2024-10-01 09:55:12","yyyy-MM-dd HH:mm:ss"),"工序设置时间");
        DateRange offlineTimeRange = new DateRange(
                DateUtils.strToDate("2024-10-01 14:30:12","yyyy-MM-dd HH:mm:ss"),
                DateUtils.strToDate("2024-10-01 16:55:12","yyyy-MM-dd HH:mm:ss"),"工序下线时间");

        DateRange checkTimeRange = new DateRange(
                DateUtils.strToDate("2024-10-01 16:59:12","yyyy-MM-dd HH:mm:ss"),
                DateUtils.strToDate("2024-10-01 18:59:12","yyyy-MM-dd HH:mm:ss"),"工序检查时间");
        AuxiliaryWorkTime auxiliaryWorkTime = AuxiliaryWorkTime.create(setTimeRange,offlineTimeRange,checkTimeRange);
        Assert.assertEquals("AuxiliaryWorkTime{setTime=DateRange{desc='工序设置时间', start=Tue Oct 01 09:30:12 CST 2024, end=Tue Oct 01 09:55:12 CST 2024}, offlineTime=DateRange{desc='工序下线时间', start=Tue Oct 01 14:30:12 CST 2024, end=Tue Oct 01 16:55:12 CST 2024}, checkTime=DateRange{desc='工序检查时间', start=Tue Oct 01 16:59:12 CST 2024, end=Tue Oct 01 18:59:12 CST 2024}}",
                auxiliaryWorkTime.toString());

        Assert.assertEquals(auxiliaryWorkTime.getSetTime(),setTimeRange);
        Assert.assertEquals(auxiliaryWorkTime.getOfflineTime(),offlineTimeRange);
        Assert.assertEquals(auxiliaryWorkTime.getCheckTime(),checkTimeRange);
    }

    @Test
    public void testChange(){
        AuxiliaryWorkTime auxiliaryWorkTime = create();
        auxiliaryWorkTime = auxiliaryWorkTime.changeSetTime(DateUtils.strToDate("2024-11-04 16:30:12","yyyy-MM-dd HH:mm:ss"));
        System.out.println(auxiliaryWorkTime.getSetTime().minutes());
        auxiliaryWorkTime = auxiliaryWorkTime.changeCheckTime(
                new DateRange(
                        DateUtils.strToDate("2024-11-04 16:30:13","yyyy-MM-dd HH:mm:ss"),
                        DateUtils.strToDate("2024-11-04 16:55:12","yyyy-MM-dd HH:mm:ss"),"工序下线时间")
        );
        System.out.println(auxiliaryWorkTime.getCheckTime().minutes());
        auxiliaryWorkTime = auxiliaryWorkTime.changeOffLineTime(
                new DateRange(
                        DateUtils.strToDate("2024-11-04 16:56:13","yyyy-MM-dd HH:mm:ss"),
                        DateUtils.strToDate("2024-11-04 18:55:12","yyyy-MM-dd HH:mm:ss"),"工序检查时间")
        );
        System.out.println(auxiliaryWorkTime.getOfflineTime().minutes());
    }

}
