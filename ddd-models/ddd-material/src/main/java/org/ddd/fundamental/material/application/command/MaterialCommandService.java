package org.ddd.fundamental.material.application.command;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.material.creator.MaterialAddable;
import org.ddd.fundamental.material.domain.model.Material;
import org.ddd.fundamental.material.domain.repository.MaterialRepository;
import org.ddd.fundamental.redis.config.RedisStoreManager;
import org.ddd.fundamental.shared.api.material.MaterialDTO;
import org.ddd.fundamental.shared.api.material.MaterialRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class MaterialCommandService {
    private final MaterialRepository materialRepository;

    private final RedisStoreManager manager;

    @Autowired
    public MaterialCommandService(
            MaterialRepository materialRepository,
            RedisStoreManager manager
                                  ){
        this.manager = manager;
        this.materialRepository = materialRepository;
    }

    /**
     * 添加物料
     * @param materialRequest
     */
    public void addMaterial(MaterialRequest materialRequest){
        Material material = MaterialAddable.createMaterial(
                materialRequest.getMaterialMaster().getName(),
                materialRequest.getRequiredSets(),
                materialRequest.getCharacterSets(),
                materialRequest.getMaterialType(),
                materialRequest.getRequiredMap(),
                materialRequest.getCharacterMap()
        );
        this.materialRepository.persist(material);
        this.manager.storeDataToCache(
                MaterialDTO.create(
                        material.getMaterialMaster(),
                        material.id()
                )
        );
    }
}
