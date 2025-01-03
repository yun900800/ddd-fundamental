package org.ddd.fundamental.shared.api.equipment;

import org.ddd.fundamental.core.AbstractDTO;
import org.ddd.fundamental.equipment.value.EquipmentPlanValue;
import org.ddd.fundamental.factory.EquipmentId;

public class EquipmentPlanDTO extends AbstractDTO<EquipmentId> {

    private EquipmentPlanValue equipmentPlan;

    @SuppressWarnings("unused")
    protected EquipmentPlanDTO(){}

    private EquipmentPlanDTO(EquipmentId equipmentId,
                             EquipmentPlanValue equipmentPlan){
        super(equipmentId);
        this.equipmentPlan = equipmentPlan;
    }

    public static EquipmentPlanDTO create(EquipmentId equipmentId,
                                          EquipmentPlanValue equipmentPlan){
        return new EquipmentPlanDTO(equipmentId, equipmentPlan);
    }

    public EquipmentPlanValue getEquipmentPlan() {
        return equipmentPlan.clone();
    }

    @Override
    public String toString() {
        return "EquipmentPlanDTO{" +
                "equipmentPlan=" + equipmentPlan +
                ", id=" + id +
                '}';
    }

    @Override
    public EquipmentId id() {
        return super.id;
    }
}
