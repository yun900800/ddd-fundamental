package org.ddd.fundamental.shared.api.equipment;

import org.ddd.fundamental.equipment.value.EquipmentMaster;
import org.ddd.fundamental.factory.EquipmentId;
import org.ddd.fundamental.shared.api.material.MaterialDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EquipmentInputOutputDTO extends EquipmentDTO {

    private EquipmentInputOutputDTO(){
        super();
    }

    private List<MaterialDTO> inputs;

    private List<MaterialDTO> outputs;

    private Set<ConfigureMaterialDTO> pairs;

    private EquipmentInputOutputDTO(EquipmentId equipmentId, EquipmentMaster master,
                                    List<MaterialDTO> inputs,
                                    List<MaterialDTO> outputs,
                                    Set<ConfigureMaterialDTO> pairs){
        super(equipmentId,master);
        this.inputs = inputs;
        this.outputs = outputs;
        this.pairs = pairs;
    }

    public static EquipmentInputOutputDTO create(EquipmentId equipmentId, EquipmentMaster master,
                                                 List<MaterialDTO> inputs,
                                                 List<MaterialDTO> outputs,
                                                 Set<ConfigureMaterialDTO> pairs){
        return new EquipmentInputOutputDTO(equipmentId,
                master,inputs,
                outputs,pairs);
    }

    public List<MaterialDTO> getInputs() {
        return new ArrayList<>(inputs);
    }

    public List<MaterialDTO> getOutputs() {
        return new ArrayList<>(outputs);
    }

    public Set<ConfigureMaterialDTO> getPairs() {
        return new HashSet<>(pairs);
    }

    @Override
    public String toString() {
        return "EquipmentInputOutputDTO{" +
                "inputs=" + inputs +
                ", outputs=" + outputs +
                ", pairs=" + pairs +
                ", id=" + id +
                '}';
    }
}
