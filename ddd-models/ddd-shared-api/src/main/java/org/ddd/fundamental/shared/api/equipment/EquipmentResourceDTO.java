package org.ddd.fundamental.shared.api.equipment;

import org.ddd.fundamental.core.AbstractDTO;
import org.ddd.fundamental.equipment.value.EquipmentResourceValue;
import org.ddd.fundamental.factory.EquipmentId;

public class EquipmentResourceDTO extends AbstractDTO<EquipmentId> {

    private EquipmentResourceValue equipmentResourceValue;

    @SuppressWarnings("unused")
    protected EquipmentResourceDTO(){}

    public EquipmentResourceValue getEquipmentResourceValue() {
        return equipmentResourceValue;
    }

    private EquipmentResourceDTO(EquipmentId equipmentId,
                                 EquipmentResourceValue equipmentResourceValue){
        super(equipmentId);
        this.equipmentResourceValue = equipmentResourceValue;
    }

    public static EquipmentResourceDTO create(EquipmentId equipmentId,
                                              EquipmentResourceValue equipmentResourceValue){
        return new EquipmentResourceDTO(equipmentId, equipmentResourceValue);
    }

    @Override
    public EquipmentId id() {
        return super.id;
    }

    @Override
    public String toString() {
        return "EquipmentResourceDTO{" +
                "equipmentResourceValue=" + equipmentResourceValue +
                ", id=" + id +
                '}';
    }
}
