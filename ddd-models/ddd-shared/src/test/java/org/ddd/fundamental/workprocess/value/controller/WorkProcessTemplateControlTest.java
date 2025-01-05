package org.ddd.fundamental.workprocess.value.controller;

import org.ddd.fundamental.workprocess.enums.BatchManagable;
import org.junit.Assert;
import org.junit.Test;

public class WorkProcessTemplateControlTest {

    @Test
    public void testCreateWorkProcessTemplateControl(){
        WorkProcessTemplateControlValue control = new WorkProcessTemplateControlValue.Builder(1, BatchManagable.BATCH_YES)
                .canSplit(false).isAllowedChecked(true).build();
        Assert.assertNull(control.getReportWorkControl());
        Assert.assertEquals(control.getProcessOrder(),1,0);

        WorkProcessTemplateControlValue control1 = new WorkProcessTemplateControlValue.Builder(1,BatchManagable.BATCH_YES)
                .canSplit(false).isAllowedChecked(true)
                .reportWorkControl(ReportingControl.create(true,"测试报工规则"))
                .build();
        Assert.assertEquals(control1.getReportWorkControl(), ReportingControl.create(true,"测试报工规则"));
    }
}
