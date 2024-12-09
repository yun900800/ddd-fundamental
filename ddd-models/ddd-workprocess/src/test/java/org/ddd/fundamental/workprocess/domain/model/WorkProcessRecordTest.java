package org.ddd.fundamental.workprocess.domain.model;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.workorder.value.WorkOrderId;
import org.ddd.fundamental.workprocess.value.WorkProcessTemplateId;
import org.ddd.fundamental.workprocess.value.WorkProcessValue;
import org.ddd.fundamental.workprocess.value.resources.ProductResources;
import org.ddd.fundamental.workprocess.value.time.WorkProcessKeyTime;
import org.junit.Test;

import java.util.HashSet;

public class WorkProcessRecordTest {

    @Test
    public void testCreateWorkProcessRecord(){
        WorkProcessRecord record = WorkProcessRecord.create(
                ChangeableInfo.create("模切工序","打磨电路板"),
                WorkProcessValue.create(
                        new ProductResources(new HashSet<>()),
                        WorkProcessTemplateId.randomId(WorkProcessTemplateId.class)
                ),
                WorkOrderId.randomId(WorkOrderId.class),
                null
        );
        System.out.println(record);
    }


}
