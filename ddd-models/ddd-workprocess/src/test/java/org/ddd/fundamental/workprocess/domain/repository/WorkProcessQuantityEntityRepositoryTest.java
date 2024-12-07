package org.ddd.fundamental.workprocess.domain.repository;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.workprocess.WorkProcessAppTest;
import org.ddd.fundamental.workprocess.value.WorkProcessId;
import org.ddd.fundamental.workprocess.domain.model.WorkProcessQuantityEntity;
import org.ddd.fundamental.workprocess.domain.model.WorkProcessRecord;
import org.ddd.fundamental.workprocess.value.WorkProcessTemplateId;
import org.ddd.fundamental.workprocess.value.WorkProcessValue;
import org.ddd.fundamental.workprocess.value.quantity.WorkProcessQuantity;
import org.ddd.fundamental.workprocess.value.resources.ProductResources;
import org.ddd.fundamental.workprocess.value.time.WorkProcessKeyTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;

public class WorkProcessQuantityEntityRepositoryTest extends WorkProcessAppTest {

    @Autowired
    private WorkProcessQuantityEntityRepository repository;

    @Autowired
    private WorkProcessRecordRepository workProcessRecordRepository;

    @Test
    public void testSaveQuantity() {
        WorkProcessQuantityEntity entity = WorkProcessQuantityEntity.create(
                WorkProcessQuantity.newBuilder().targetQuantity(1500)
                        .unQualifiedQuantity(20).transferQuantityWithOverCross(100)
                        .owePaymentQuantity(40).build()
        );
        repository.save(entity);
    }

    @Test
    public void testSaveQuantityAndRecord() {
        WorkProcessRecord record = WorkProcessRecord.create(
                ChangeableInfo.create("模切工序","打磨电路板"),
                WorkProcessValue.create(
                        new ProductResources(new HashSet<>()),
                        WorkProcessTemplateId.randomId(WorkProcessTemplateId.class)
                )
        );
        workProcessRecordRepository.save(record);
        WorkProcessQuantityEntity entity = WorkProcessQuantityEntity.create(
                WorkProcessQuantity.newBuilder().targetQuantity(1500)
                        .unQualifiedQuantity(20).transferQuantityWithOverCross(100)
                        .owePaymentQuantity(40).build()
        );
        repository.persistAndFlush(entity);
        //保存了数据以后
        WorkProcessId id = record.id();
        WorkProcessRecord query = workProcessRecordRepository.findById(id).orElse(null);
        //下面这条语句实际上不会去查询,只是起到这是外键的作用
        WorkProcessQuantityEntity queryEntity = repository.getOne(entity.id());
        query.setQuantity(queryEntity);
        workProcessRecordRepository.save(query);

    }

    @Test
    public void testSaveHibernate() {
        WorkProcessQuantityEntity entity = WorkProcessQuantityEntity.create(
                WorkProcessQuantity.newBuilder().targetQuantity(1500)
                        .unQualifiedQuantity(20).transferQuantityWithOverCross(100)
                        .owePaymentQuantity(40).build()
        );
        repository.persist(entity);
    }
}
