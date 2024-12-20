package org.ddd.fundamental.shared.api.equipment;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.day.YearModelValue;
import org.ddd.fundamental.equipment.enums.EquipmentType;
import org.ddd.fundamental.equipment.value.EquipmentMaster;
import org.ddd.fundamental.workprocess.enums.ProductResourceType;

@Data
@AllArgsConstructor
public class EquipmentRequest {
    private EquipmentMaster master;
    private YearModelValue model;
    private EquipmentType type;
    private ChangeableInfo resource;
    private ProductResourceType resourceType;
}
