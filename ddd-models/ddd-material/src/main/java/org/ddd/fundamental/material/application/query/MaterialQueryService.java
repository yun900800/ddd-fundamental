package org.ddd.fundamental.material.application.query;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.material.application.MaterialConverter;
import org.ddd.fundamental.material.creator.MaterialAddable;
import org.ddd.fundamental.material.domain.model.Material;
import org.ddd.fundamental.material.domain.repository.MaterialRepository;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.redis.config.RedisStoreManager;
import org.ddd.fundamental.shared.api.material.MaterialDTO;
import org.ddd.fundamental.material.value.MaterialType;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
@Slf4j
public class MaterialQueryService {

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private MaterialAddable creator;

    @Autowired
    private RedisStoreManager manager;


    public List<MaterialDTO> materials() {
        List<Material> materials = null;
        materials = creator.getMaterialList();
        if (!CollectionUtils.isEmpty(materials)) {
            log.info("fetch data from local cache");
            return MaterialConverter.entityToDTO(materials);
        }
        List<MaterialDTO> materialDTOS = manager.queryAllData(MaterialDTO.class);
        if (!CollectionUtils.isEmpty(materialDTOS)) {
            log.info("fetch data from redis cache");
            return materialDTOS;
        }
        materials =  materialRepository.findAll();
        return MaterialConverter.entityToDTO(materials);
    }

    /**
     * 从缓存中获取数据
     * @param ids
     * @return
     */
    private List<MaterialDTO> fetchMaterialsFromCache(List<String> ids) {
        return manager.fetchDataListFromCache(ids.stream().map(v->new MaterialId(v))
                .collect(Collectors.toList()), MaterialDTO.class);
    }

    /**
     * 根据物料ids 批量查询数据
     * @param ids
     * @return
     */
    public List<MaterialDTO> materialsByIds(List<String> ids){
        List<MaterialDTO> materials = fetchMaterialsFromCache(ids);
        if (!CollectionUtils.isEmpty(materials)) {
            log.info("fetch data from redis cache");
            return materials;
        }
        List<Material> materialList = materialRepository.findByIdIn(
                ids.stream().map(v->new MaterialId(v)).collect(Collectors.toList())
        );
        log.info("fetch data from db cache");
        return MaterialConverter.entityToDTO(materialList);
    }

    /**
     * 根据物料类型查询物料
     * @param materialType
     * @return
     */
    public List<MaterialDTO> materialsByMaterialType(MaterialType materialType){
        List<Material> materialList = materialRepository.getByMaterialType(materialType.name());
        return MaterialConverter.entityToDTO(materialList);
    }

}
