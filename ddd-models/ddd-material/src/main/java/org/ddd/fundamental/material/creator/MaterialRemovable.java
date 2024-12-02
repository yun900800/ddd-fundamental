package org.ddd.fundamental.material.creator;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.creator.DataRemovable;
import org.ddd.fundamental.material.domain.repository.MaterialRepository;
import org.ddd.fundamental.redis.config.RedisStoreManager;
import org.ddd.fundamental.shared.api.material.MaterialDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@Order(2)
public class MaterialRemovable implements DataRemovable {

    private final MaterialRepository repository;

    private final RedisStoreManager manager;

    @Autowired
    public MaterialRemovable(MaterialRepository repository,
                             RedisStoreManager manager
    ){
        this.repository = repository;
        this.manager = manager;
    }

    @Override
    @Transactional
    public void execute() {
        log.info("MaterialRemovable execute delete all materials start");
        this.repository.deleteAllMaterials();
        log.info("MaterialRemovable execute delete all materials finished");
        manager.deleteAllData(MaterialDTO.class);
    }
}
