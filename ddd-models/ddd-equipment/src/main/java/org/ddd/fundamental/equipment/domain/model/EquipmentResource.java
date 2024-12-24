package org.ddd.fundamental.equipment.domain.model;

import org.ddd.fundamental.core.AbstractEntity;
import org.ddd.fundamental.equipment.value.EquipmentPlanRange;
import org.ddd.fundamental.equipment.value.EquipmentResourceValue;
import org.ddd.fundamental.factory.EquipmentId;
import org.ddd.fundamental.material.value.MaterialType;
import org.ddd.fundamental.shared.api.material.MaterialDTO;
import javax.persistence.*;


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



    @SuppressWarnings("unused")
    protected EquipmentResource(){}

    private EquipmentResource(EquipmentResourceValue value){
        super(EquipmentId.randomId(EquipmentId.class));
        this.equipmentResourceValue = value;
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

    public EquipmentResource addMaterialInput(MaterialDTO input){
        if (input.getMaterialType().equals(MaterialType.PRODUCTION)){
            return this;
        }
        this.equipmentResourceValue.addMaterialInput(input.id());
        return this;
    }

    public EquipmentResource removeMaterialInput(MaterialDTO input){
        this.equipmentResourceValue.removeMaterialInput(input.id());
        return this;
    }

    public EquipmentResource addMaterialOutput(MaterialDTO output){
        if (output.getMaterialType().equals(MaterialType.RAW_MATERIAL)){
            return this;
        }
        this.equipmentResourceValue.addMaterialOutput(output.id());
        return this;
    }

    public EquipmentResource removeMaterialOutput(MaterialDTO output){
        this.equipmentResourceValue.removeMaterialOutput(output.id());
        return this;
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
