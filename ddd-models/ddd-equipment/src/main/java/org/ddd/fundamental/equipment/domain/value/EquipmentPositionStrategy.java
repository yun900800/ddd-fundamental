package org.ddd.fundamental.equipment.domain.value;

import org.ddd.fundamental.shared.api.equipment.ConfigureMaterialDTO;

public interface EquipmentPositionStrategy {

    ConfigureMaterialDTO handle(ConfigureMaterialDTO configureMaterialDTO);

}
