package org.ddd.fundamental.equipment.creator;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.creator.DataRemovable;
import org.ddd.fundamental.equipment.domain.repository.ToolingEquipmentRepository;
import org.ddd.fundamental.redis.config.RedisStoreManager;
import org.ddd.fundamental.shared.api.equipment.ToolingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@Order(2)
public class ToolingEquipmentRemovable implements DataRemovable {

    private final ToolingEquipmentRepository equipmentRepository;

    private final RedisStoreManager manager;

    @Autowired
    public ToolingEquipmentRemovable(ToolingEquipmentRepository equipmentRepository,
                                     RedisStoreManager manager){
        this.equipmentRepository = equipmentRepository;
        this.manager = manager;
    }

    @Override
    @Transactional
    public void execute() {
        log.info("delete all ToolingEquipments from db start");
        this.equipmentRepository.deleteAllTooling();
        log.info("delete all ToolingEquipments from db finished");
        log.info("delete all ToolingEquipments from cache start");
        this.manager.deleteAllData(ToolingDTO.class);
        log.info("delete all ToolingEquipments from cache finished");
    }
}
