package org.ddd.fundamental.equipment.domain.model;

import org.ddd.fundamental.core.AbstractAggregateRoot;
import org.ddd.fundamental.core.AbstractEntity;
import org.ddd.fundamental.day.range.DateRangeValue;
import org.ddd.fundamental.equipment.value.EquipmentPlanRange;
import org.ddd.fundamental.equipment.value.EquipmentPlanValue;
import org.ddd.fundamental.factory.EquipmentId;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table( name = "equipment_plan")
public class EquipmentPlan extends AbstractEntity<EquipmentId> {

    private EquipmentPlanValue equipmentPlan;

    @SuppressWarnings("unused")
    protected EquipmentPlan(){}

    private EquipmentPlan(EquipmentPlanValue equipmentPlan){
        super(EquipmentId.randomId(EquipmentId.class));
        this.equipmentPlan = equipmentPlan;
    }

    public static EquipmentPlan create(EquipmentPlanValue equipmentPlan){
        return new EquipmentPlan(equipmentPlan);
    }


    public EquipmentPlan addEquipmentPlan(EquipmentPlanRange addedValue){
        this.equipmentPlan.addEquipmentPlan(addedValue);
        return this;
    }

    public EquipmentPlan removeEquipmentPlan(EquipmentPlanRange addedValue){
        this.equipmentPlan.removeEquipmentPlan(addedValue);
        return this;
    }

    public EquipmentPlan clearEquipmentPlan(){
        this.equipmentPlan.clearEquipmentPlan();
        return this;
    }

    public EquipmentPlanValue getEquipmentPlan() {
        return equipmentPlan.clone();
    }

    @Override
    public long created() {
        return 0;
    }
}
