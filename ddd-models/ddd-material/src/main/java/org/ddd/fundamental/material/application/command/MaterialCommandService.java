package org.ddd.fundamental.material.application.command;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.material.MaterialMaster;
import org.ddd.fundamental.material.creator.MaterialAddable;
import org.ddd.fundamental.material.domain.model.Material;
import org.ddd.fundamental.material.domain.repository.MaterialRepository;
import org.ddd.fundamental.material.domain.value.ControlProps;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.redis.config.RedisStoreManager;
import org.ddd.fundamental.shared.api.material.MaterialDTO;
import org.ddd.fundamental.shared.api.material.MaterialRequest;
import org.ddd.fundamental.shared.api.material.PropsContainer;
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
                        material.id(),
                        materialRequest.getMaterialType()
                )
        );
    }

    /**
     * 修改物料基本信息
     * @param info
     * @param id
     */
    public void changeMaterialInfo(ChangeableInfo info, MaterialId id){
        Material material = materialRepository.findById(id).orElse(null);
        if (null == material) {
            return;
        }
        material.changeMaterialInfo(info);
    }

    /**
     * 修改物料主数据
     * @param materialMaster
     * @param id
     */
    public void changeMaterialMaster(MaterialMaster materialMaster, MaterialId id){
        Material material = materialRepository.findById(id).orElse(null);
        if (null == material) {
            return;
        }
        material.changeMaterialMaster(materialMaster);
    }

    /**
     * 修改物料控制信息
     * @param controlProps
     * @param id
     */
    public void changeMaterialControl(ControlProps controlProps, MaterialId id){
        Material material = materialRepository.findById(id).orElse(null);
        if (null == material) {
            return;
        }
        material.changeMaterialControl(controlProps);
    }

    /**
     * 添加可选属性
     * @param propsContainer
     * @param id
     */
    public void addOptionalProps(PropsContainer propsContainer, MaterialId id){
        Material material = materialRepository.findById(id).orElse(null);
        if (null == material) {
            return;
        }
        material.addOptionalProps(propsContainer.getKey(),propsContainer.getValue());
    }

    /**
     *
     * @param propsContainer
     * @param id
     */
    public void addOptionalCharacter(PropsContainer propsContainer, MaterialId id){
        Material material = materialRepository.findById(id).orElse(null);
        if (null == material) {
            return;
        }
        material.addOptionalCharacter(propsContainer.getKey(),propsContainer.getValue());
    }
}
