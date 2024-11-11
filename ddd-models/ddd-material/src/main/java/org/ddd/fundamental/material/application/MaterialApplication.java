package org.ddd.fundamental.material.application;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.material.domain.model.Material;
import org.ddd.fundamental.material.domain.repository.MaterialRepository;
import org.ddd.fundamental.shared.api.material.MaterialDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
@Slf4j
public class MaterialApplication {

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private MaterialCreator creator;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    @Qualifier(value = "newRedisTemplate")
    private RedisTemplate<String,Object> newRedisTemplate;

    /**
     * 将数据存储到cache
     * @param materials
     */
    private void storeMaterialsToCache(List<MaterialDTO> materials) {
        log.info("存储数据到缓存");
        if (!CollectionUtils.isEmpty(materials)) {
            for (MaterialDTO materialDTO: materials) {
                redisTemplate.opsForValue().set(materialDTO.id().toUUID(),materialDTO.toJson());
            }
        }
    }

    private void storeMaterialsToCacheWithNewMethod(List<MaterialDTO> materials) {
        log.info("存储数据到缓存");
        if (!CollectionUtils.isEmpty(materials)) {
            for (MaterialDTO materialDTO: materials) {
                newRedisTemplate.opsForValue().set(materialDTO.id().toUUID(),materialDTO);
            }
        }
    }

    public List<MaterialDTO> materials() {
        List<Material> materials = creator.getMaterialList();

        if (null == materials || CollectionUtils.isEmpty(materials)) {
            materials =  materialRepository.findAll();
        }
        List<MaterialDTO> materialDTOS = materials.stream()
                .map(v->new MaterialDTO(v.getMaterialMaster(),v.id()))
                .collect(Collectors.toList());
        //storeMaterialsToCache(materialDTOS);
        storeMaterialsToCacheWithNewMethod(materialDTOS);
        return materialDTOS;
    }


}
