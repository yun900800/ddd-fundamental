package org.ddd.fundamental.equipment.domain.value.impl;

import org.ddd.fundamental.material.value.MaterialType;
import org.ddd.fundamental.shared.api.equipment.ConfigureMaterialDTO;
import org.ddd.fundamental.shared.api.material.MaterialDTO;
import org.ddd.fundamental.utils.CollectionUtils;

import java.util.Arrays;
import java.util.List;

public class EquipmentFirstPosition extends AbstractEquipmentPositionStrategy{

    //随机选取一个原材料和一个在制品
    @Override
    List<MaterialDTO> handleInput(ConfigureMaterialDTO configureMaterialDTO) {
        MaterialDTO rawMaterial = null;
        MaterialDTO spareMaterial = null;
        for (MaterialDTO materialDTO: configureMaterialDTO.getMaterialInputs()) {
            if (materialDTO.getMaterialType().equals(MaterialType.RAW_MATERIAL)) {
                rawMaterial = materialDTO;
            }
            if (materialDTO.getMaterialType().equals(MaterialType.WORKING_IN_PROGRESS)) {
                spareMaterial = materialDTO;
            }
        }

        return Arrays.asList(
                rawMaterial,spareMaterial
        );
    }

    @Override
    List<MaterialDTO> handleOutput(ConfigureMaterialDTO configureMaterialDTO) {
        MaterialDTO spareMaterial = null;
        for (MaterialDTO materialDTO: configureMaterialDTO.getMaterialOutputs()) {
            if (materialDTO.getMaterialType().equals(MaterialType.WORKING_IN_PROGRESS)) {
                spareMaterial = materialDTO;
            }
        }
        return Arrays.asList(
                spareMaterial
        );
    }
}
