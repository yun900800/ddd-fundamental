package org.ddd.fundamental.equipment.application.query;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.equipment.application.EquipmentConverter;
import org.ddd.fundamental.equipment.domain.repository.EquipmentRepository;
import org.ddd.fundamental.equipment.domain.repository.ToolingEquipmentRepository;
import org.ddd.fundamental.redis.config.RedisStoreManager;
import org.ddd.fundamental.shared.api.equipment.EquipmentDTO;
import org.ddd.fundamental.shared.api.equipment.ToolingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(readOnly = true)
public class EquipmentQueryService {

    private final EquipmentRepository equipmentRepository;

    private final ToolingEquipmentRepository toolingRepository;

    private final RedisStoreManager manager;

    @Autowired
    public EquipmentQueryService(EquipmentRepository equipmentRepository,
                                 ToolingEquipmentRepository toolingRepository,
                                 RedisStoreManager manager){
        this.equipmentRepository = equipmentRepository;
        this.toolingRepository = toolingRepository;
        this.manager = manager;
    }

    /**
     * 查询所有设备信息
     * @return
     */
    public List<EquipmentDTO> equipments() {
        List<EquipmentDTO> equipmentDTOS = manager.queryAllData(EquipmentDTO.class);
        if (!CollectionUtils.isEmpty(equipmentDTOS)) {
            log.info("equipmentDTOS data from redis cache");
            return equipmentDTOS;
        }
        return EquipmentConverter.entityToDTO(equipmentRepository.findAll());
    }

    /**
     * 查询所有工装治具信息
     * @return
     */
    public List<ToolingDTO> toolingList() {
        List<ToolingDTO> toolingDTOS = manager.queryAllData(ToolingDTO.class);
        if (!CollectionUtils.isEmpty(toolingDTOS)){
            log.info("toolingDTOS data from redis cache");
            return toolingDTOS;
        }
        return EquipmentConverter.entityToToolingDTO(toolingRepository.findAll());
    }
}
