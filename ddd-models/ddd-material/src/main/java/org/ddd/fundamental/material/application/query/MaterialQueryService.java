package org.ddd.fundamental.material.application.query;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.material.application.MaterialConverter;
import org.ddd.fundamental.redis.cache.CacheStore;
import org.ddd.fundamental.redis.cache.ICacheLoaderService;
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

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
@Slf4j
public class MaterialQueryService implements ICacheLoaderService<MaterialDTO> {

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private MaterialAddable creator;

    @Autowired
    private RedisStoreManager manager;

    @Autowired
    private CacheStore<MaterialDTO> cacheStore;


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

    private Material getMaterialFromDbById(MaterialId id){
        Material material = materialRepository.findById(id).orElse(null);
        if (null == material) {
            String msg = "materialId:%s 对应的Material 不存在.";
            throw new RuntimeException(String.format(msg,id));
        }
        return material;
    }

    /**
     * 查询一个物料数据信息
     * @param materialId
     * @return
     */
    public MaterialDTO getMaterialById(MaterialId materialId){
        String id = materialId.toUUID();
        MaterialDTO materialDTO = cacheStore.get(id);
        if (null != materialDTO){
            log.info("fetch id:{} data from local cache",id);
            return materialDTO;
        }
        materialDTO = manager.fetchDataFromCache(materialId,MaterialDTO.class);
        if (null != materialDTO){
            log.info("fetch id:{} data from redis cache",id);
            cacheStore.add(id,materialDTO);
            return materialDTO;
        }
        Material material = getMaterialFromDbById(materialId);
        materialDTO = MaterialConverter.entityToDTO(material);
        manager.storeDataToCache(materialDTO);
        cacheStore.add(id,materialDTO);
        return materialDTO;
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

    @Override
    public MaterialDTO getBackendData(String key) {
        Material material = getMaterialFromDbById(new MaterialId(key));
        return MaterialConverter.entityToDTO(material);
    }
}
