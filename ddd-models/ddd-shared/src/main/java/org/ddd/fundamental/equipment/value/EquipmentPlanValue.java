package org.ddd.fundamental.equipment.value;

import org.ddd.fundamental.core.ValueObject;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.ArrayList;
import java.util.List;

/**
 * 记录当前设备被哪个工单，工序使用的时间段
 */
@MappedSuperclass
@Embeddable
public class EquipmentPlanValue implements ValueObject, Cloneable {

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "equipment_plan_range")
    private List<EquipmentPlanRange> equipmentPlans;

    @SuppressWarnings("unused")
    protected EquipmentPlanValue(){}

    private EquipmentPlanValue(List<EquipmentPlanRange> equipmentPlans){
        this.equipmentPlans = new ArrayList<>();
    }

    public static EquipmentPlanValue create(){
        return new EquipmentPlanValue(new ArrayList<>());
    }

    public EquipmentPlanValue addEquipmentPlan(EquipmentPlanRange addedValue){
        this.equipmentPlans.add(addedValue);
        return this;
    }

    public EquipmentPlanValue removeEquipmentPlan(EquipmentPlanRange addedValue){
        this.equipmentPlans.remove(addedValue);
        return this;
    }

    public EquipmentPlanValue clearEquipmentPlan(){
        this.equipmentPlans.clear();
        return this;
    }

    public List<EquipmentPlanRange> getDateRangeValues() {
        return new ArrayList<>(equipmentPlans);
    }

    @Override
    public EquipmentPlanValue clone() {
        try {
            EquipmentPlanValue clone = (EquipmentPlanValue) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}

