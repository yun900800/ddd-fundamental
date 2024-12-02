package org.ddd.fundamental.shared.api.equipment;

import org.ddd.fundamental.core.AbstractDTO;
import org.ddd.fundamental.equipment.value.ToolingEquipmentValue;
import org.ddd.fundamental.factory.EquipmentId;
public class ToolingDTO extends AbstractDTO<EquipmentId> {

    private ToolingEquipmentValue toolingEquipmentValue;

    private EquipmentId equipmentId;

    @SuppressWarnings("unused")
    private ToolingDTO(){}

    private ToolingDTO(EquipmentId id,ToolingEquipmentValue toolingEquipmentValue,
                       EquipmentId equipmentId){
        super(id);
        this.toolingEquipmentValue = toolingEquipmentValue;
        this.equipmentId = equipmentId;
    }

    public static ToolingDTO create(EquipmentId id,ToolingEquipmentValue toolingEquipmentValue,
                                    EquipmentId equipmentId){
        return new ToolingDTO(id,toolingEquipmentValue,equipmentId);
    }


    @Override
    public EquipmentId id() {
        return super.id;
    }

    public EquipmentId getEquipmentId() {
        return equipmentId;
    }

    public ToolingEquipmentValue getToolingEquipmentValue() {
        return toolingEquipmentValue;
    }

    public String toJson() {
        return super.toJson();
    }
    @Override
    public String toString() {
        return "ToolingDTO{" +
                "toolingEquipmentValue=" + toolingEquipmentValue +
                ", id=" + id() +
                '}';
    }
}
