package org.ddd.fundamental.shared.api.equipment;

import org.ddd.fundamental.core.AbstractDTO;
import org.ddd.fundamental.equipment.value.ToolingEquipmentValue;
import org.ddd.fundamental.factory.EquipmentId;
public class ToolingDTO extends AbstractDTO<EquipmentId> {

    private ToolingEquipmentValue toolingEquipmentValue;

    @SuppressWarnings("unused")
    private ToolingDTO(){}

    private ToolingDTO(EquipmentId id,ToolingEquipmentValue toolingEquipmentValue){
        super(id);
        this.toolingEquipmentValue = toolingEquipmentValue;
    }

    public static ToolingDTO create(EquipmentId id,ToolingEquipmentValue toolingEquipmentValue){
        return new ToolingDTO(id,toolingEquipmentValue);
    }


    @Override
    public EquipmentId id() {
        return super.id;
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
