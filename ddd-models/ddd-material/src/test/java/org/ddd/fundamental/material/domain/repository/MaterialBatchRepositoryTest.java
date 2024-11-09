package org.ddd.fundamental.material.domain.repository;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.material.MaterialAppTest;
import org.ddd.fundamental.material.domain.enums.BatchClassifyType;
import org.ddd.fundamental.material.domain.model.MaterialBatch;
import org.ddd.fundamental.material.domain.value.MaterialBatchValue;
import org.ddd.fundamental.material.value.MaterialId;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MaterialBatchRepositoryTest extends MaterialAppTest {

    @Autowired
    private MaterialBatchRepository batchRepository;

    @Test
    public void testCreateMaterialBatch(){
        MaterialId materialId = MaterialId.randomId(MaterialId.class);
        MaterialBatch batch0 = MaterialBatch.create(new MaterialBatchValue(
                materialId,
                10, BatchClassifyType.PRODUCT_BATCH
        ), ChangeableInfo.create("工序1物料1批次", "这是在工序1为物料1产生的批次"));
        batchRepository.save(batch0);
    }
}
