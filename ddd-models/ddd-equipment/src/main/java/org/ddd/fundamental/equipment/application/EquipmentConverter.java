package org.ddd.fundamental.equipment.application;

import org.ddd.fundamental.equipment.domain.model.Equipment;
import org.ddd.fundamental.equipment.domain.model.RPAccount;
import org.ddd.fundamental.equipment.domain.model.ToolingEquipment;
import org.ddd.fundamental.shared.api.equipment.EquipmentDTO;
import org.ddd.fundamental.shared.api.equipment.RPAccountDTO;
import org.ddd.fundamental.shared.api.equipment.ToolingDTO;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class EquipmentConverter {

    public static List<EquipmentDTO> entityToDTO(List<Equipment> entities){
        if (CollectionUtils.isEmpty(entities)) {
            return new ArrayList<>();
        }
        return entities.stream().map(EquipmentConverter::entityToDTO)
                .collect(Collectors.toList());
    }

    public static EquipmentDTO entityToDTO(Equipment entity){
        return EquipmentDTO.create(entity.id(),entity.getMaster());
    }

    public static List<ToolingDTO> entityToToolingDTO(List<ToolingEquipment> entities){
        if (CollectionUtils.isEmpty(entities)) {
            return new ArrayList<>();
        }
        return entities.stream().map(EquipmentConverter::entityToToolingDTO)
                .collect(Collectors.toList());
    }


    public static ToolingDTO entityToToolingDTO(ToolingEquipment entity){
        if (entity.getEquipment() == null) {
            return ToolingDTO.create(entity.id(),entity.getToolingEquipmentInfo(),null);
        }
        return ToolingDTO.create(entity.id(),entity.getToolingEquipmentInfo(),entity.getEquipment().id());
    }

    public static List<RPAccountDTO> entityToRPAccountDTO(List<RPAccount> entities){
        if (CollectionUtils.isEmpty(entities)){
            return new ArrayList<>();
        } else {
            return entities.stream().map(EquipmentConverter::entityToRPAccountDTO)
                    .collect(Collectors.toList());
        }
    }

    public static RPAccountDTO entityToRPAccountDTO(RPAccount entity){
        return RPAccountDTO.create(entity.id(),entity.getAccountValue());
    }

}
