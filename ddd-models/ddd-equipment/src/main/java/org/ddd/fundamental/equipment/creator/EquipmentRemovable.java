package org.ddd.fundamental.equipment.creator;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.creator.DataRemovable;
import org.ddd.fundamental.equipment.application.command.EquipmentCommandService;
import org.ddd.fundamental.equipment.domain.repository.EquipmentRepository;
import org.ddd.fundamental.redis.config.RedisStoreManager;
import org.ddd.fundamental.shared.api.equipment.EquipmentDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@Order(3)
public class EquipmentRemovable implements DataRemovable {


    private final RedisStoreManager manager;

    private final EquipmentCommandService service;

    @Autowired(required = false)
    public EquipmentRemovable(RedisStoreManager manager,
                              EquipmentCommandService service){
        this.manager = manager;
        this.service = service;
    }

    @Override
    @Transactional
    public void execute() {
        log.info("delete all Equipments from db start");
        this.service.deleteAllEquipments();
        log.info("delete all Equipments from db finished");
        log.info("delete all Equipments from cache start");
        this.manager.deleteAllData(EquipmentDTO.class);
        log.info("delete all Equipments from cache finished");
    }
}
