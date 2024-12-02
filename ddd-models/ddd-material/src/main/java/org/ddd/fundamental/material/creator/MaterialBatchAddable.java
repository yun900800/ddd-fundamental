package org.ddd.fundamental.material.creator;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.creator.DataAddable;
import org.ddd.fundamental.material.domain.enums.BatchClassifyType;
import org.ddd.fundamental.material.domain.model.Material;
import org.ddd.fundamental.material.domain.model.MaterialBatch;
import org.ddd.fundamental.material.domain.repository.MaterialBatchRepository;
import org.ddd.fundamental.material.domain.value.MaterialBatchValue;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.redis.config.RedisStoreManager;
import org.ddd.fundamental.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
@Order(2)
public class MaterialBatchAddable implements DataAddable {

    private final MaterialBatchRepository repository;

    private final RedisStoreManager manager;

    private final MaterialAddable materialAddable;

    private List<MaterialBatch> materialBatches;

    @Autowired
    public MaterialBatchAddable(MaterialBatchRepository repository,
                           RedisStoreManager manager,
                                MaterialAddable materialAddable
    ){
        this.repository = repository;
        this.manager = manager;
        this.materialAddable = materialAddable;
    }

    public List<MaterialBatch> getMaterialBatches() {
        return new ArrayList<>(
                materialBatches
        );
    }

    public static List<String> workProcessNames(){
        return Arrays.asList("工序1","工序2","工序3","工序4","工序5");

    }

    public static List<MaterialBatch> createMaterialBatch(){
        List<MaterialBatch> materialBatches1 = new ArrayList<>();
        for (int i = 0 ; i < 200; i++) {
            materialBatches1.add(
                    createMaterialBatch(CollectionUtils.random(workProcessNames()),
                            i+1)
            );
        }
        return materialBatches1;
    }

    private static MaterialBatch createMaterialBatch(String workProcessName,int index) {
        Material material = CollectionUtils.random(MaterialAddable.createMaterials());
        MaterialId materialId = material.id();
        int batchNumber = CollectionUtils.random(MaterialAddable.numbers());
        BatchClassifyType batchClassifyType = CollectionUtils.random(Arrays.asList(
                BatchClassifyType.PRODUCT_BATCH,
                BatchClassifyType.ERP_BATCH
        ));
        MaterialBatch batch = MaterialBatch.create(new MaterialBatchValue(
                materialId,
                batchNumber, batchClassifyType
        ), ChangeableInfo.create(workProcessName+ index + material.name() + index +
                "批次", "这是在"+ workProcessName+ index+ "为"+ material.name() + index +"产生的批次"));
        return batch;
    }

    @Override
    @Transactional
    public void execute() {
        log.info("MaterialBatchAddable execute create all materialBatches start");
        materialBatches = createMaterialBatch();
        repository.persistAll(materialBatches);
        log.info("MaterialBatchAddable execute create all materialBatches finished");
    }
}
