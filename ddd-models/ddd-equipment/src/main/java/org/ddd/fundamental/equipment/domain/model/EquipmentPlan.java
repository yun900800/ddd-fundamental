package org.ddd.fundamental.equipment.domain.model;

import org.ddd.fundamental.core.AbstractEntity;
import org.ddd.fundamental.equipment.value.BusinessRange;
import org.ddd.fundamental.equipment.value.EquipmentPlanRange;
import org.ddd.fundamental.equipment.value.EquipmentPlanValue;
import org.ddd.fundamental.equipment.value.business.WorkOrderComposable;
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

    public EquipmentPlan addBusinessEquipmentPlan(BusinessRange<WorkOrderComposable> addedValue){
        this.equipmentPlan.addBusinessEquipmentPlan(addedValue);
        return this;
    }

    public EquipmentPlan removeBusinessEquipmentPlan(BusinessRange<WorkOrderComposable> addedValue){
        this.equipmentPlan.removeBusinessEquipmentPlan(addedValue);
        return this;
    }

    public EquipmentPlan clearBusinessEquipmentPlan(){
        this.equipmentPlan.clearBusinessEquipmentPlan();
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
