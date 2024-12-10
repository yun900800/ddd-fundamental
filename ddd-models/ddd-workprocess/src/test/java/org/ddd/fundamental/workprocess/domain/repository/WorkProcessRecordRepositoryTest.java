package org.ddd.fundamental.workprocess.domain.repository;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.workorder.value.WorkOrderId;
import org.ddd.fundamental.workprocess.WorkProcessAppTest;
import org.ddd.fundamental.workprocess.domain.model.WorkProcessTimeEntity;
import org.ddd.fundamental.workprocess.value.WorkProcessId;
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

import java.time.Instant;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

@Slf4j
public class WorkProcessRecordRepositoryTest extends WorkProcessAppTest {

    @Autowired
    private WorkProcessRecordRepository repository;

    @Autowired
    private  WorkProcessTimeRepository timeRepository;

    @Test
    public void createWorkProcessRecord(){
        WorkProcessRecord record = WorkProcessRecord.create(
                ChangeableInfo.create("模切工序","打磨电路板"),
                WorkProcessValue.create(
                        new ProductResources(new HashSet<>()),
                        WorkProcessTemplateId.randomId(WorkProcessTemplateId.class)
                ),
                WorkOrderId.randomId(WorkOrderId.class),
                null
        );
        repository.save(record);
    }

    @Test
    public void testCreateAddQtyToWorkProcessRecord(){
        WorkProcessRecord record = WorkProcessRecord.create(
                ChangeableInfo.create("模切工序","打磨电路板"),
                WorkProcessValue.create(
                        new ProductResources(new HashSet<>()),
                        WorkProcessTemplateId.randomId(WorkProcessTemplateId.class)
                ),
                WorkOrderId.randomId(WorkOrderId.class),
                null
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

                        new ProductResources(new HashSet<>()),
                        WorkProcessTemplateId.randomId(WorkProcessTemplateId.class)
                ),
                WorkOrderId.randomId(WorkOrderId.class),
                null
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
                        new ProductResources(new HashSet<>()),
                        WorkProcessTemplateId.randomId(WorkProcessTemplateId.class)
                ),
                WorkOrderId.randomId(WorkOrderId.class),
                null
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





    @Test
    public void testCreateRecordWithTime(){
        WorkProcessRecord record = WorkProcessRecord.create(
                ChangeableInfo.create("模切工序","打磨电路板测试删除时间"),
                WorkProcessValue.create(
                        new ProductResources(new HashSet<>()),
                        WorkProcessTemplateId.randomId(WorkProcessTemplateId.class)
                ),
                WorkOrderId.randomId(WorkOrderId.class),
                null
        );
        WorkProcessTimeEntity time = WorkProcessTimeEntity.create(
            WorkProcessKeyTime.init()
        );
        record.setWorkProcessTime(time);
        repository.save(record);
        WorkProcessId id = record.id();
        WorkProcessRecord queryRecord = repository.findById(id).orElse(null);
        queryRecord.setWorkProcessTime(null);
        repository.save(queryRecord);

    }

    @Test
    public void testUpdateTime(){
        WorkProcessRecord record = WorkProcessRecord.create(
                ChangeableInfo.create("模切工序","打磨电路板测试删除时间"),
                WorkProcessValue.create(
                        new ProductResources(new HashSet<>()),
                        WorkProcessTemplateId.randomId(WorkProcessTemplateId.class)
                ),
                WorkOrderId.randomId(WorkOrderId.class),
                null
        );
        WorkProcessTimeEntity time = WorkProcessTimeEntity.create(
                WorkProcessKeyTime.init()
        );
        record.setWorkProcessTime(time);
        repository.save(record);
        WorkProcessId id = record.id();
        WorkProcessRecord queryRecord = repository.findById(id).orElse(null);
        Instant startTime = queryRecord.getWorkProcessTime().getKeyTime().getInitTime();
        queryRecord.getWorkProcessTime().getKeyTime().directStartProcess(startTime.plusSeconds(3600*2));
        repository.save(queryRecord);
    }

    @Test
    public void testDirectStartProcess(){
        WorkProcessRecord record = repository.findAll().get(0);
        record.directStartProcess();
        repository.save(record);
    }

}
