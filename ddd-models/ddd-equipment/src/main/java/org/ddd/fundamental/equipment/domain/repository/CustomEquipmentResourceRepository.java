package org.ddd.fundamental.equipment.domain.repository;

import org.ddd.fundamental.equipment.domain.model.EquipmentResource;
import org.ddd.fundamental.material.value.MaterialId;

import java.util.List;

public interface CustomEquipmentResourceRepository {

    List<EquipmentResource> queryResourcesByInputAndOutput(MaterialId inputId,
            MaterialId outputId
    );

    List<String> queryResourcesByInputAndOutputByJPQL(MaterialId inputId,
                                                                 MaterialId outputId);

    List<EquipmentResource> queryResourcesByInputAndOutputIds(List<MaterialId> inputIds,
                                                              List<MaterialId> outputIds);
}
