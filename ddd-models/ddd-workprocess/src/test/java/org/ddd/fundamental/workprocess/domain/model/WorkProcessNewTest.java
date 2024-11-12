package org.ddd.fundamental.workprocess.domain.model;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.day.range.DateRange;
import org.ddd.fundamental.utils.DateUtils;
import org.ddd.fundamental.workprocess.value.controller.GapRangeControl;
import org.ddd.fundamental.workprocess.value.controller.ReportWorkControl;
import org.ddd.fundamental.workprocess.value.controller.WorkOrderControl;
import org.ddd.fundamental.workprocess.value.controller.WorkProcessTemplateControl;
import org.ddd.fundamental.workprocess.value.time.AuxiliaryWorkTime;
import org.ddd.fundamental.workprocess.value.WorkProcessBeat;
import org.ddd.fundamental.workprocess.value.quantity.WorkProcessQuantity;
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
        WorkProcessTemplateControl control = new WorkProcessTemplateControl.Builder(1,true)
                .canSplit(false).isAllowedChecked(true)
                .gapRangeControl(GapRangeControl.create(true, false))
                .reportWorkControl(ReportWorkControl.create(true,"测试报工规则"))
                .workOrderControl(WorkOrderControl.create(false,true,false))
                .build();
        WorkProcessTemplate workProcessNew = new WorkProcessTemplate(
                ChangeableInfo.create("主板加工工序","这是用来加工新能源车的主板的工序"),
                create(),
                WorkProcessBeat.create(1000,15),
                control
        );
        System.out.println(workProcessNew);
    }
}

