package org.ddd.fundamental.equipment.domain.value.impl;

import org.ddd.fundamental.equipment.domain.value.EquipmentPositionStrategy;
import org.ddd.fundamental.shared.api.equipment.ConfigureMaterialDTO;
import org.ddd.fundamental.shared.api.material.MaterialDTO;

import java.util.List;

public abstract class AbstractEquipmentPositionStrategy implements EquipmentPositionStrategy {

    abstract List<MaterialDTO> handleInput(ConfigureMaterialDTO configureMaterialDTO);

    abstract List<MaterialDTO> handleOutput(ConfigureMaterialDTO configureMaterialDTO);


    @Override
    public ConfigureMaterialDTO handle(ConfigureMaterialDTO configureMaterialDTO) {
        List<MaterialDTO> inputs = handleInput(configureMaterialDTO);
        List<MaterialDTO> outputs = handleOutput(configureMaterialDTO);
        return ConfigureMaterialDTO.create(configureMaterialDTO.id(),
                inputs,outputs);
    }
}
