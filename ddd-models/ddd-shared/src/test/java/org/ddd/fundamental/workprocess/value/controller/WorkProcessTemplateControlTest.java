package org.ddd.fundamental.workprocess.value.controller;

import org.junit.Assert;
import org.junit.Test;

public class WorkProcessTemplateControlTest {

    @Test
    public void testCreateWorkProcessTemplateControl(){
        WorkProcessTemplateControl control = new WorkProcessTemplateControl.Builder(1,true)
                .canSplit(false).isAllowedChecked(true).build();
        Assert.assertNull(control.getReportWorkControl());
        Assert.assertEquals(control.getProcessOrder(),1,0);
        Assert.assertEquals(control.getBatchManage(),true);

        WorkProcessTemplateControl control1 = new WorkProcessTemplateControl.Builder(1,true)
                .canSplit(false).isAllowedChecked(true)
                .gapRangeControl(GapRangeControl.create(true, false))
                .reportWorkControl(ReportWorkControl.create(true,"测试报工规则"))
                .workOrderControl(WorkOrderControl.create(false,true,false))
                .build();
        Assert.assertEquals(control1.getGapRangeControl(),GapRangeControl.create(true, false));
        Assert.assertEquals(control1.getReportWorkControl(),ReportWorkControl.create(true,"测试报工规则"));
        Assert.assertEquals(control1.getWorkOrderControl(),WorkOrderControl.create(false,true,false));
    }
}
