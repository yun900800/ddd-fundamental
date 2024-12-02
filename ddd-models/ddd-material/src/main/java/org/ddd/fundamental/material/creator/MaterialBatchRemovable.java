package org.ddd.fundamental.material.creator;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.creator.DataRemovable;
import org.ddd.fundamental.material.domain.repository.MaterialBatchRepository;
import org.ddd.fundamental.redis.config.RedisStoreManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@Order(1)
public class MaterialBatchRemovable implements DataRemovable {

    private final MaterialBatchRepository repository;

    private final RedisStoreManager manager;

    @Autowired
    public MaterialBatchRemovable(MaterialBatchRepository repository,
                             RedisStoreManager manager
    ){
        this.repository = repository;
        this.manager = manager;
    }
    @Override
    @Transactional
    public void execute() {
        log.info("MaterialBatchRemovable execute delete all materialBatches start");
        this.repository.deleteAllMaterialBatches();
        log.info("MaterialBatchRemovable execute delete all materialBatches finished");
    }
}
