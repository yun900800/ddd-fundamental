package org.ddd.fundamental.material.application;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.material.domain.model.Material;
import org.ddd.fundamental.material.domain.repository.MaterialRepository;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.shared.api.material.MaterialDTO;
import org.ddd.fundamental.shared.api.material.enums.MaterialType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;
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
                redisTemplate.opsForValue().set(materialDTO.id().toUUID(),materialDTO.toJson(),60*10, TimeUnit.SECONDS);
            }
        }
    }

    private void storeMaterialsToCacheWithNewMethod(List<MaterialDTO> materials) {
        log.info("存储数据到缓存");
        if (!CollectionUtils.isEmpty(materials)) {
            for (MaterialDTO materialDTO: materials) {
                newRedisTemplate.opsForValue().set(materialDTO.id().toUUID(),materialDTO, 60*10, TimeUnit.SECONDS);
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

    /**
     * 从缓存中获取数据
     * @param ids
     * @return
     */
    private List<MaterialDTO> fetchMaterialsFromCache(List<String> ids) {
        List<Object> objects = newRedisTemplate.opsForValue().multiGet(ids);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(objects, new TypeReference<>() {
        });

    }

    /**
     * 根据物料ids 批量查询数据
     * @param ids
     * @return
     */
    public List<MaterialDTO> materialsByIds(List<String> ids){
        List<MaterialDTO> materials = fetchMaterialsFromCache(ids);
        if (CollectionUtils.isEmpty(materials)) {
            List<Material> materialList = materialRepository.findByIdIn(
                    ids.stream().map(v->new MaterialId(v)).collect(Collectors.toList())
            );
            materials = materialList.stream()
                    .map(v->new MaterialDTO(v.getMaterialMaster(),v.id()))
                    .collect(Collectors.toList());
        }
        return materials;
    }

    /**
     * 根据物料类型查询物料
     * @param materialType
     * @return
     */
    public List<MaterialDTO> materialsByMaterialType(MaterialType materialType){
        List<Material> materialList = materialRepository.getByMaterialType(materialType.name());
        return materialList.stream()
                .map(v->new MaterialDTO(v.getMaterialMaster(),v.id()))
                .collect(Collectors.toList());
    }



}
