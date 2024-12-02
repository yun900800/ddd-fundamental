package org.ddd.fundamental.workprocess.domain.repository;

import com.mysql.cj.xdevapi.TableImpl;
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

import java.time.Instant;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

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

    @Test
    public void testChangeTime() {
        WorkProcessRecord record = WorkProcessRecord.create(
                ChangeableInfo.create("模切工序","打磨电路板"),
                WorkProcessValue.create(
                        WorkProcessKeyTime.start(),
                        new ProductResources(new HashSet<>()),
                        WorkProcessTemplateId.randomId(WorkProcessTemplateId.class)
                )
        );
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e){

        }
        Instant now = Instant.now();
        record.start(now.plusSeconds(60));
        repository.save(record);
        WorkProcessId id = record.id();
        WorkProcessRecord queryWorkProcess = repository.findById(id).orElse(null);
        queryWorkProcess.start(now.minusSeconds(60));
        repository.save(queryWorkProcess);
    }

    @Test
    public void changeFinishTime() {
        WorkProcessRecord record = WorkProcessRecord.create(
                ChangeableInfo.create("模切工序","打磨电路板"),
                WorkProcessValue.create(
                        WorkProcessKeyTime.start(),
                        new ProductResources(new HashSet<>()),
                        WorkProcessTemplateId.randomId(WorkProcessTemplateId.class)
                )
        );
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e){

        }
        Instant now = Instant.now();
        record.start(now.plusSeconds(60));
        repository.save(record);
        WorkProcessId id = record.id();
        WorkProcessRecord queryWorkProcess = repository.findById(id).orElse(null);
        queryWorkProcess.finish(now.plusSeconds(1600));
        repository.save(queryWorkProcess);
    }

    @Test
    public void testChangeInterruptTime(){
        WorkProcessRecord record = WorkProcessRecord.create(
                ChangeableInfo.create("模切工序","打磨电路板"),
                WorkProcessValue.create(
                        WorkProcessKeyTime.start(),
                        new ProductResources(new HashSet<>()),
                        WorkProcessTemplateId.randomId(WorkProcessTemplateId.class)
                )
        );
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e){

        }
        Instant now = Instant.now();
        record.start(now.plusSeconds(60));
        repository.save(record);
        WorkProcessId id = record.id();
        WorkProcessRecord queryWorkProcess = repository.findById(id).orElse(null);
        queryWorkProcess.interrupt(now.plusSeconds(360));
        queryWorkProcess.finish(now.plusSeconds(720));
        repository.save(queryWorkProcess);
    }

    @Test
    public void testChangeRestartTime() {
        WorkProcessRecord record = WorkProcessRecord.create(
                ChangeableInfo.create("模切工序","打磨电路板"),
                WorkProcessValue.create(
                        WorkProcessKeyTime.start(),
                        new ProductResources(new HashSet<>()),
                        WorkProcessTemplateId.randomId(WorkProcessTemplateId.class)
                )
        );
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e){

        }
        Instant now = Instant.now();
        record.start(now.plusSeconds(60));
        repository.save(record);
        WorkProcessId id = record.id();
        WorkProcessRecord queryWorkProcess = repository.findById(id).orElse(null);
        queryWorkProcess.interrupt(now.plusSeconds(180));
        queryWorkProcess.restart(now.plusSeconds(240));
        repository.save(queryWorkProcess);
    }

}
