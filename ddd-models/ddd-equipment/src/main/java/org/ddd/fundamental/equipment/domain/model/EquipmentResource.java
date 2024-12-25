package org.ddd.fundamental.equipment.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.ddd.fundamental.core.AbstractEntity;
import org.ddd.fundamental.equipment.domain.value.EquipmentPositionStrategy;
import org.ddd.fundamental.equipment.domain.value.impl.EquipmentFirstPosition;
import org.ddd.fundamental.equipment.domain.value.impl.EquipmentLastPosition;
import org.ddd.fundamental.equipment.domain.value.impl.EquipmentMiddlePosition;
import org.ddd.fundamental.equipment.enums.EquipmentPositionType;
import org.ddd.fundamental.equipment.value.EquipmentPlanRange;
import org.ddd.fundamental.equipment.value.EquipmentResourceValue;
import org.ddd.fundamental.equipment.value.resources.InputAndOutputValue;
import org.ddd.fundamental.factory.EquipmentId;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.material.value.MaterialType;
import org.ddd.fundamental.shared.api.equipment.ConfigureMaterialDTO;
import org.ddd.fundamental.shared.api.material.MaterialDTO;
import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * 设备资源对外提供的API
 * 1、当前设备正在使用的时间段
 * 2、当前设备计划使用的时间段
 * 3、设备资源是否正在启用或者其他
 *
 */
@Entity
@Table(name = "equipment_resource")
public class EquipmentResource extends AbstractEntity<EquipmentId> {

    @Embedded
    private EquipmentResourceValue equipmentResourceValue;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    private Equipment equipment;

    /**
     * 设备对应的资源所在的工序的位置
     * 可能有三个值,设备在工艺的首节点
     * 设备在工艺的中间节点
     * 设备在工艺的尾节点
     */
    @Enumerated(EnumType.STRING)
    private EquipmentPositionType positionType;

    @Transient
    @JsonIgnore
    private EquipmentPositionStrategy strategy;

    @SuppressWarnings("unused")
    protected EquipmentResource(){}

    private EquipmentResource(EquipmentResourceValue value){
        super(EquipmentId.randomId(EquipmentId.class));
        this.equipmentResourceValue = value;
        this.positionType = EquipmentPositionType.MIDDLE;
        this.strategy = new EquipmentMiddlePosition();
    }

    public static EquipmentResource create(EquipmentResourceValue value){
        return new EquipmentResource(value);
    }

    public EquipmentResource setCurrentUseDateRange(EquipmentPlanRange useDateRange) {
        this.equipmentResourceValue.recordUseRange(useDateRange);
        return this;
    }

    public EquipmentResource finishUseRange(){
        this.equipmentResourceValue.finishUseRange();
        return this;
    }

    public EquipmentResource addPlanDateRange(EquipmentPlanRange planDateRange){
        this.equipmentResourceValue.addRange(planDateRange);
        return this;
    }

    public EquipmentResource removePlanDateRange(EquipmentPlanRange planDateRange){
        this.equipmentResourceValue.removeRange(planDateRange);
        return this;
    }

    private EquipmentResource addMaterialInput(MaterialDTO input){
        if (input.getMaterialType().equals(MaterialType.PRODUCTION)){
            return this;
        }
        this.equipmentResourceValue.addMaterialInput(input.id());
        return this;
    }


    private EquipmentResource addMaterialOutput(MaterialDTO output){
        if (output.getMaterialType().equals(MaterialType.RAW_MATERIAL)){
            return this;
        }
        this.equipmentResourceValue.addMaterialOutput(output.id());
        return this;
    }

    public EquipmentResource configureEquipmentPositionType(EquipmentPositionType positionType){
        this.positionType = positionType;
        configureStrategy();
        return this;
    }

    /**
     * 根据不同的位置类型配置不同的计算策略
     * @return
     */
    private EquipmentResource configureStrategy(){
        if (EquipmentPositionType.FIRST.equals(positionType)) {
            strategy = new EquipmentFirstPosition();
        }
        if (EquipmentPositionType.MIDDLE.equals(positionType)) {
            strategy = new EquipmentMiddlePosition();
        }
        if (EquipmentPositionType.LAST.equals(positionType)) {
            strategy = new EquipmentLastPosition();
        }
        return this;
    }

    /**
     * 如果是首节点,输入至少包含一个原材料或者在制品,输出至少一个在制品
     * 如果是中间节点,输入至少包含一个在制品,输出至少包含一个在制品
     * 如果是尾巴节点,输入至少包含一个在制品,输出是一个成品或者半成品
     *
     * 添加设备能够处理的物料信息对
     * @param materialInputs
     * @param materialOutputs
     * @return
     */
    public EquipmentResource addMaterialPairs(List<MaterialDTO> materialInputs,
                                              List<MaterialDTO> materialOutputs) {
        configureStrategy();
        ConfigureMaterialDTO result = strategy.handle(ConfigureMaterialDTO.create(
                this.id(), materialInputs,materialOutputs
        ));

        this.addMaterialPairs(result);
        this.calculateInputAndOutputs(result);
        return this;
    }

    /**
     * 重新计算新的总的输入和输出物料信息
     * @param configureMaterialDTO
     * @return
     */
    private EquipmentResource calculateInputAndOutputs(ConfigureMaterialDTO configureMaterialDTO){
        List<MaterialDTO> inputs = configureMaterialDTO.getMaterialInputs();
        List<MaterialDTO> outputs = configureMaterialDTO.getMaterialOutputs();
        for (MaterialDTO input: inputs) {
            addMaterialInput(input);
        }
        for (MaterialDTO output:outputs) {
            addMaterialOutput(output);
        }
        return this;
    }

  /**
     * 添加设备的配置pairs
     * @param configureMaterialDTO
     * @return
     */
    private EquipmentResource addMaterialPairs(ConfigureMaterialDTO configureMaterialDTO){
        List<MaterialDTO> inputs = configureMaterialDTO.getMaterialInputs();
        List<MaterialDTO> outputs = configureMaterialDTO.getMaterialOutputs();
        InputAndOutputValue inputAndOutputValue = InputAndOutputValue.create(
                inputs.stream().map(v->v.id()).collect(Collectors.toList()),
                outputs.stream().map(v->v.id()).collect(Collectors.toList())
        );
        if (equipmentResourceValue.pairSize() >= 3) {
            String msg = "设备id:%s已经配置了3个pairs,不能配置多余的pairs";
            throw new RuntimeException(String.format(msg,id().toUUID()));
        }
        this.equipmentResourceValue.addMaterialPairs(inputAndOutputValue);
        return this;
    }

    /**
     * 移除设备能够处理的物料信息对
     * @param materialInputs
     * @param materialOutputs
     * @return
     */
    public EquipmentResource removeMaterialPairs(List<MaterialDTO> materialInputs,
                                                 List<MaterialDTO> materialOutputs) {
        InputAndOutputValue inputAndOutputValue = InputAndOutputValue.create(
                materialInputs.stream().map(v->v.id()).collect(Collectors.toList()),
                materialOutputs.stream().map(v->v.id()).collect(Collectors.toList())
        );
        this.equipmentResourceValue.removeMaterialPairs(inputAndOutputValue);
        recalculateInputAndOutput();
        return this;
    }

    /**
     * 重新计算新的输入和输出的总计物料信息
     */
    private void recalculateInputAndOutput(){
        this.equipmentResourceValue.recalculateInputAndOutput();
    }

    /**
     * 计算设备所有的物料信息
     * @return
     */
    public Set<MaterialId> allInputAndOutput(){
        return this.equipmentResourceValue.allInputAndOutput();
    }


    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public EquipmentResourceValue getEquipmentResourceValue() {
        return equipmentResourceValue;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    @Override
    public long created() {
        return 0;
    }
}
