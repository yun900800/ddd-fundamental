package org.ddd.fundamental.workprocess.domain.repository;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.workprocess.WorkProcessAppTest;
import org.ddd.fundamental.workprocess.domain.model.WorkProcessRecord;
import org.ddd.fundamental.workprocess.value.WorkProcessTemplateId;
import org.ddd.fundamental.workprocess.value.WorkProcessValue;
import org.ddd.fundamental.workprocess.value.quantity.WorkProcessQuantity;
import org.ddd.fundamental.workprocess.value.resources.ProductResources;
import org.ddd.fundamental.workprocess.value.time.WorkProcessKeyTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;

public class WorkProcessRecordRepositoryTest extends WorkProcessAppTest {

    @Autowired
    private WorkProcessRecordRepository repository;

    @Test
    public void createWorkProcessRecord(){
        WorkProcessRecord record = WorkProcessRecord.create(
                ChangeableInfo.create("模切工序","打磨电路板"),
                WorkProcessValue.create(
                        WorkProcessKeyTime.create(),
                        WorkProcessQuantity.newBuilder()
                                .targetQuantity(10000)
                                .unQualifiedQuantity(0)
                                .transferQuantityWithOverCross(10)
                                .overCrossQuantity(100)
                                .build(),
                        new ProductResources(new HashSet<>()),
                        WorkProcessTemplateId.randomId(WorkProcessTemplateId.class)
                )
        );
        repository.save(record);
    }
}
