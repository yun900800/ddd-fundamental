package org.ddd.fundamental.shared.api.equipment;

import org.ddd.fundamental.core.AbstractDTO;
import org.ddd.fundamental.factory.EquipmentId;
import org.ddd.fundamental.shared.api.material.MaterialDTO;

import java.util.ArrayList;
import java.util.List;

public class ConfigureMaterialDTO extends AbstractDTO<EquipmentId> {

    private List<MaterialDTO> materialInputs;

    private List<MaterialDTO> materialOutputs;

    @SuppressWarnings("unused")
    protected ConfigureMaterialDTO(){

    }

    private ConfigureMaterialDTO(EquipmentId id,
                                List<MaterialDTO> materialInputs,
                                List<MaterialDTO> materialOutputs){
        super(id);
        this.materialInputs = materialInputs;
        this.materialOutputs = materialOutputs;
    }

    public static ConfigureMaterialDTO create(EquipmentId id,
                                              List<MaterialDTO> materialInputs,
                                              List<MaterialDTO> materialOutputs){
        return new ConfigureMaterialDTO(id, materialInputs,materialOutputs);
    }

    @Override
    public EquipmentId id() {
        return super.id;
    }

    public List<MaterialDTO> getMaterialInputs() {
        return new ArrayList<>(materialInputs);
    }

    public List<MaterialDTO> getMaterialOutputs() {
        return new ArrayList<>(materialOutputs);
    }

    @Override
    public String toString() {
        return "ConfigureMaterialDTO{" +
                "materialInputs=" + materialInputs +
                ", materialOutputs=" + materialOutputs +
                ", id=" + id +
                '}';
    }
}
