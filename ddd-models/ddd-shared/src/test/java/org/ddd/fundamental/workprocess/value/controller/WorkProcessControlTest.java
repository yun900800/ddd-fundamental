package org.ddd.fundamental.workprocess.value.controller;

import org.ddd.fundamental.workorder.enums.WorkOrderType;
import org.ddd.fundamental.workprocess.enums.ReportWorkType;
import org.junit.Assert;
import org.junit.Test;

public class WorkProcessControlTest {

    @Test
    public void testCreateWorkProcessControl(){
        WorkProcessControl control = new WorkProcessControl.Builder(
                WorkOrderType.PRODUCT_WORK_ORDER, 1,true
        ).autoSchedule(true).build();
        Assert.assertEquals(control.getProcessOrder(),1);
        Assert.assertEquals(control.getWorkOrderType(),WorkOrderType.PRODUCT_WORK_ORDER);
        Assert.assertEquals(control.isAutoSchedule(),true);
        Assert.assertEquals(control.isForceChecked(),true);

        WorkProcessControl control1 = new WorkProcessControl.Builder(
                WorkOrderType.PRODUCT_WORK_ORDER, 1,true
        ).autoSchedule(true).isReportWork(true).reportWorkType(ReportWorkType.STANDARD)
                .canSplit(true).isOverOrOweManagable(true).percent(5).build();

        Assert.assertEquals(control1.isFullAutoOver(), false);
        Assert.assertEquals(control1.isReportWork(), true);
        Assert.assertEquals(control1.getReportWorkType(), ReportWorkType.STANDARD);
        Assert.assertEquals(control1.getBatchManagable(), null);
    }

    @Test(expected = RuntimeException.class)
    public void testCreateThrowException() {
        WorkProcessControl control1 = new WorkProcessControl.Builder(
                WorkOrderType.PRODUCT_WORK_ORDER, 1,true
        ).autoSchedule(true).isReportWork(false).reportWorkType(ReportWorkType.STANDARD).build();
    }
}
