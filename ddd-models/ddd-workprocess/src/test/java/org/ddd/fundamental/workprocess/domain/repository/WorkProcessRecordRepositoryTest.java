package org.ddd.fundamental.workprocess.domain.repository;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.workprocess.WorkProcessAppTest;
import org.ddd.fundamental.workprocess.domain.model.WorkProcessId;
import org.ddd.fundamental.workprocess.domain.model.WorkProcessQuantityEntity;
import org.ddd.fundamental.workprocess.domain.model.WorkProcessRecord;
import org.ddd.fundamental.workprocess.value.WorkProcessTemplateId;
import org.ddd.fundamental.workprocess.value.WorkProcessValue;
import org.ddd.fundamental.workprocess.value.quantity.WorkProcessQuantity;
import org.ddd.fundamental.workprocess.value.resources.ProductResources;
import org.ddd.fundamental.workprocess.value.time.WorkProcessKeyTime;
import org.junit.Assert;
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
                        WorkProcessKeyTime.start(),
                        new ProductResources(new HashSet<>()),
                        WorkProcessTemplateId.randomId(WorkProcessTemplateId.class)
                )
        );
        repository.save(record);
    }

    @Test
    public void testCreateAddQtyToWorkProcessRecord(){
        WorkProcessRecord record = WorkProcessRecord.create(
                ChangeableInfo.create("模切工序","打磨电路板"),
                WorkProcessValue.create(
                        WorkProcessKeyTime.start(),
                        new ProductResources(new HashSet<>()),
                        WorkProcessTemplateId.randomId(WorkProcessTemplateId.class)
                )
        );
        WorkProcessQuantity quantity = WorkProcessQuantity.newBuilder()
                .targetQuantity(1200)
                .unQualifiedQuantity(20)
                .transferQuantityWithOverCross(50)
                .overCrossQuantity(80).build();
        WorkProcessQuantityEntity entity = WorkProcessQuantityEntity.create(quantity);
        record.setQuantity(entity);
        repository.save(record);

    }

    @Test
    public void testQueryEntityLazy() {
        WorkProcessRecord record = WorkProcessRecord.create(
                ChangeableInfo.create("模切工序","打磨电路板"),
                WorkProcessValue.create(
                        WorkProcessKeyTime.start(),
                        new ProductResources(new HashSet<>()),
                        WorkProcessTemplateId.randomId(WorkProcessTemplateId.class)
                )
        );
        WorkProcessQuantity quantity = WorkProcessQuantity.newBuilder()
                .targetQuantity(1200)
                .unQualifiedQuantity(20)
                .transferQuantityWithOverCross(50)
                .overCrossQuantity(80).build();
        WorkProcessQuantityEntity entity = WorkProcessQuantityEntity.create(quantity);
        record.setQuantity(entity);
        WorkProcessId id = record.id();
        repository.save(record);

        WorkProcessRecord record1 = repository.findById(id).orElse(null);
        Assert.assertEquals(record1.getProcessInfo().getName(),"模切工序");

        Assert.assertEquals(record1.getQuantity(),entity);
    }

    @Test
    public void testChangeQty() {
        WorkProcessRecord record = WorkProcessRecord.create(
                ChangeableInfo.create("模切工序","打磨电路板"),
                WorkProcessValue.create(
                        WorkProcessKeyTime.start(),
                        new ProductResources(new HashSet<>()),
                        WorkProcessTemplateId.randomId(WorkProcessTemplateId.class)
                )
        );
        WorkProcessQuantity quantity = WorkProcessQuantity.newBuilder()
                .targetQuantity(1200)
                .unQualifiedQuantity(20)
                .transferQuantityWithOverCross(50)
                .overCrossQuantity(80).build();
        WorkProcessQuantityEntity entity = WorkProcessQuantityEntity.create(quantity);
        record.setQuantity(entity);
        WorkProcessId id = record.id();
        repository.save(record);
        WorkProcessRecord record1 = repository.findById(id).orElse(null);
        record1.getQuantity().changeTargetQuantity(1500).changeUnQualifiedQuantity(30);
        repository.save(record1);
    }

}
