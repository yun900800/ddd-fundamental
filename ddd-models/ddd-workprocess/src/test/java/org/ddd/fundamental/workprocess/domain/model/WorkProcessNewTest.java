package org.ddd.fundamental.workprocess.domain.model;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.day.range.DateRange;
import org.ddd.fundamental.utils.DateUtils;
import org.ddd.fundamental.workprocess.AuxiliaryWorkTime;
import org.ddd.fundamental.workprocess.WorkProcessBeat;
import org.ddd.fundamental.workprocess.WorkProcessQuality;
import org.junit.Test;

public class WorkProcessNewTest {

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
    public void testCreateWorkProcessNew(){
        WorkProcessNew workProcessNew = new WorkProcessNew(
                ChangeableInfo.create("主板加工工序","这是用来加工新能源车的主板的工序"),
                create(),
                WorkProcessQuality.newBuilder()
                        .targetQuantity(1000)
                        .unQualifiedQuantity(20)
                        .transferQuantityWithOverCross(10)
                        .withOverCrossQuantity(1050)
                        .noOverCrossPercent()
                        .build(),
                WorkProcessBeat.create(1000,15)
        );
        System.out.println(workProcessNew);
    }
}

