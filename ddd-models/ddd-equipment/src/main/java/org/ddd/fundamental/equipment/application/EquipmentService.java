package org.ddd.fundamental.equipment.application;

import org.ddd.fundamental.equipment.domain.model.Equipment;
import org.ddd.fundamental.equipment.domain.model.ToolingEquipment;
import org.ddd.fundamental.equipment.domain.repository.EquipmentRepository;
import org.ddd.fundamental.equipment.domain.repository.ToolingEquipmentRepository;
import org.ddd.fundamental.factory.EquipmentId;
import org.ddd.fundamental.factory.ToolingEquipmentId;
import org.ddd.fundamental.shared.api.equipment.EquipmentDTO;
import org.ddd.fundamental.shared.api.equipment.ToolingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private ToolingEquipmentRepository toolingRepository;

    @Autowired
    private ToolingEquipmentCreator creator;

    @Transactional
    public void addToolingToEquipment(ToolingEquipmentId toolingId, EquipmentId equipmentId) {
        ToolingEquipment toolingEquipment = toolingRepository.getById(toolingId);
        Equipment equipment = equipmentRepository.getById(equipmentId);
        toolingEquipment.enableUse();
        equipment.addToolingId(toolingEquipment.id());
        toolingRepository.save(toolingEquipment);
        equipmentRepository.save(equipment);
    }

    @Transactional(readOnly = true)
    public List<EquipmentDTO> equipments() {
        if (null != creator.getEquipments() && !CollectionUtils.isEmpty(creator.getEquipments())) {
            return creator.getEquipments().stream()
                    .map(v-> EquipmentDTO.create(v.id(),v.getMaster())).collect(Collectors.toList());
        }
        return equipmentRepository.findAll().stream()
                .map(v-> EquipmentDTO.create(v.id(),v.getMaster())).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ToolingDTO> toolingList() {
        if (null != creator.getToolingEquipments() && !CollectionUtils.isEmpty(creator.getToolingEquipments())) {
            return creator.getToolingEquipments().stream()
                    .map(v-> ToolingDTO.create(v.id(),v.getToolingEquipmentInfo())).collect(Collectors.toList());
        }
        return toolingRepository.findAll().stream()
                .map(v-> ToolingDTO.create(v.id(),v.getToolingEquipmentInfo())).collect(Collectors.toList());
    }
}
