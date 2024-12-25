package org.ddd.fundamental.equipment.domain.value.impl;

import org.ddd.fundamental.material.value.MaterialType;
import org.ddd.fundamental.shared.api.equipment.ConfigureMaterialDTO;
import org.ddd.fundamental.shared.api.material.MaterialDTO;

import java.util.Arrays;
import java.util.List;

public class EquipmentLastPosition extends AbstractEquipmentPositionStrategy{
    @Override
    List<MaterialDTO> handleInput(ConfigureMaterialDTO configureMaterialDTO) {
        MaterialDTO spareMaterial = null;
        for (MaterialDTO materialDTO: configureMaterialDTO.getMaterialInputs()) {
            if (materialDTO.getMaterialType().equals(MaterialType.WORKING_IN_PROGRESS)) {
                spareMaterial = materialDTO;
            }
        }
        return Arrays.asList(
                spareMaterial
        );
    }

    @Override
    List<MaterialDTO> handleOutput(ConfigureMaterialDTO configureMaterialDTO) {
        MaterialDTO product = null;
        for (MaterialDTO materialDTO: configureMaterialDTO.getMaterialOutputs()) {
            if (materialDTO.getMaterialType().equals(MaterialType.PRODUCTION)) {
                product = materialDTO;
            }
        }
        return Arrays.asList(
                product
        );
    }
}
