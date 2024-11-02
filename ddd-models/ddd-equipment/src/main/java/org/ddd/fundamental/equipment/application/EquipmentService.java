package org.ddd.fundamental.equipment.application;

import org.ddd.fundamental.equipment.domain.repository.EquipmentRepository;
import org.ddd.fundamental.equipment.domain.repository.ToolingEquipmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EquipmentService {

    private EquipmentRepository equipmentRepository;

    private ToolingEquipmentRepository toolingRepository;

    @Transactional
    public void addToolingToEquipment() {

    }

}
