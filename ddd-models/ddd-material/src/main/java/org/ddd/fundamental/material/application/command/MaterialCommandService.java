package org.ddd.fundamental.material.application.command;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.event.core.DomainEventType;
import org.ddd.fundamental.event.material.ProductEventCreated;
import org.ddd.fundamental.material.MaterialMaster;
import org.ddd.fundamental.material.creator.MaterialAddable;
import org.ddd.fundamental.material.domain.model.Material;
import org.ddd.fundamental.material.domain.repository.MaterialRepository;
import org.ddd.fundamental.material.domain.value.ControlProps;
import org.ddd.fundamental.material.enums.MaterialInputOutputType;
import org.ddd.fundamental.material.producer.MaterialProducer;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.redis.config.RedisStoreManager;
import org.ddd.fundamental.shared.api.material.MaterialDTO;
import org.ddd.fundamental.shared.api.material.MaterialRequest;
import org.ddd.fundamental.shared.api.material.PropsContainer;
import org.ddd.fundamental.utils.EnumsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class MaterialCommandService {
    private final MaterialRepository materialRepository;

    private final RedisStoreManager manager;

    private final MaterialProducer materialProducer;

    @Autowired
    public MaterialCommandService(
            MaterialRepository materialRepository,
            RedisStoreManager manager,
            MaterialProducer materialProducer
                                  ){
        this.manager = manager;
        this.materialRepository = materialRepository;
        this.materialProducer = materialProducer;
    }

    public static ProductEventCreated toEvent(Material material){
        return ProductEventCreated.create(DomainEventType.MATERIAL,
                material.getMaterialMaster(),
                material.getMaterialControlProps().getMaterialType(),
                material.id());
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
        this.materialProducer.sendProductEventCreated(toEvent(material));
        this.manager.storeDataToCache(
                MaterialDTO.create(
                        material.getMaterialMaster(),
                        material.id(),
                        materialRequest.getMaterialType(),
                        EnumsUtils.findEnumValue(MaterialInputOutputType.class,
                                material.getMaterialRequiredProps().get("inputOrOutputType"))
                )
        );
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
