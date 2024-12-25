package org.ddd.fundamental.shared.api.equipment;

import org.ddd.fundamental.core.AbstractDTO;
import org.ddd.fundamental.equipment.value.EquipmentMaster;
import org.ddd.fundamental.factory.EquipmentId;

public class EquipmentDTO extends AbstractDTO<EquipmentId> {

    private EquipmentMaster master;

    @SuppressWarnings("unused")
    protected EquipmentDTO(){}

    protected EquipmentDTO(EquipmentId id,EquipmentMaster master){
        super(id);
        this.master = master;
    }

    public static EquipmentDTO create(EquipmentId id,EquipmentMaster master){
        return new EquipmentDTO(id, master);
    }

    public EquipmentMaster getMaster() {
        return master.clone();
    }

    @Override
    public EquipmentId id() {
        return super.id;
    }

    public String toJson() {
        return super.toJson();
    }

    @Override
    public String toString() {
        return "EquipmentDTO{" +
                "id=" + id() +
                ", master=" + master +
                '}';
    }
}
