package org.ddd.fundamental.equipment.value;

import org.ddd.fundamental.core.ValueObject;
import org.ddd.fundamental.equipment.value.business.WorkOrderComposable;
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
    @Column(columnDefinition = "json", name = "equipment_plan_ranges")
    private List<BusinessRange<WorkOrderComposable>> businessEquipmentPlans;

    @SuppressWarnings("unused")
    protected EquipmentPlanValue(){}

    private EquipmentPlanValue(List<BusinessRange<WorkOrderComposable>> equipmentPlans){
        this.businessEquipmentPlans = new ArrayList<>();
    }

    public static EquipmentPlanValue create(){
        return new EquipmentPlanValue(new ArrayList<>());
    }

    public EquipmentPlanValue addBusinessEquipmentPlan(BusinessRange<WorkOrderComposable> addedValue){
        this.businessEquipmentPlans.add(addedValue);
        return this;
    }

    public EquipmentPlanValue removeBusinessEquipmentPlan(BusinessRange<WorkOrderComposable> addedValue){
        this.businessEquipmentPlans.remove(addedValue);
        return this;
    }

    public EquipmentPlanValue clearBusinessEquipmentPlan(){
        this.businessEquipmentPlans.clear();
        return this;
    }


    public List<BusinessRange<WorkOrderComposable>> getBusinessEquipmentPlans() {
        return new ArrayList<>(businessEquipmentPlans);
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

