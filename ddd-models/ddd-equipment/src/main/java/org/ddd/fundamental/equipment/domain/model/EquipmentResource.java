package org.ddd.fundamental.equipment.domain.model;

import org.ddd.fundamental.core.AbstractEntity;
import org.ddd.fundamental.day.range.DateRangeValue;
import org.ddd.fundamental.equipment.value.EquipmentResourceValue;
import org.ddd.fundamental.factory.EquipmentId;


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
    //@JoinColumn(name = "equipment_id")
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

    public EquipmentResource setCurrentUseDateRange(DateRangeValue useDateRange) {
        this.equipmentResourceValue.recordUseRange(useDateRange);
        return this;
    }

    public EquipmentResource finishUseRange(){
        this.equipmentResourceValue.finishUseRange();
        return this;
    }

    public EquipmentResource addPlanDateRange(DateRangeValue planDateRange){
        this.equipmentResourceValue.addRange(planDateRange);
        return this;
    }

    public EquipmentResource removePlanDateRange(DateRangeValue planDateRange){
        this.equipmentResourceValue.removeRange(planDateRange);
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
