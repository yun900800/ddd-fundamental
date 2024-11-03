package org.ddd.fundamental.equipment.application;

import org.ddd.fundamental.equipment.domain.model.Equipment;
import org.ddd.fundamental.equipment.domain.model.ToolingEquipment;
import org.ddd.fundamental.equipment.domain.repository.EquipmentRepository;
import org.ddd.fundamental.equipment.domain.repository.ToolingEquipmentRepository;
import org.ddd.fundamental.factory.EquipmentId;
import org.ddd.fundamental.factory.ToolingEquipmentId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private ToolingEquipmentRepository toolingRepository;

    @Transactional
    public void addToolingToEquipment(ToolingEquipmentId toolingId, EquipmentId equipmentId) {
        ToolingEquipment toolingEquipment = toolingRepository.getById(toolingId);
        Equipment equipment = equipmentRepository.getById(equipmentId);
        toolingEquipment.enableUse();
        equipment.addToolingId(toolingEquipment.id());
        toolingRepository.save(toolingEquipment);
        equipmentRepository.save(equipment);
    }

}
