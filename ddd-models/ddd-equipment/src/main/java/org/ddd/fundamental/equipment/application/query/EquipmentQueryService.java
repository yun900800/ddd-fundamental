package org.ddd.fundamental.equipment.application.query;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.equipment.application.EquipmentConverter;
import org.ddd.fundamental.equipment.domain.model.Equipment;
import org.ddd.fundamental.equipment.domain.model.RPAccount;
import org.ddd.fundamental.equipment.domain.repository.EquipmentRepository;
import org.ddd.fundamental.equipment.domain.repository.RPAccountRepository;
import org.ddd.fundamental.equipment.domain.repository.ToolingEquipmentRepository;
import org.ddd.fundamental.equipment.value.RPAccountId;
import org.ddd.fundamental.factory.EquipmentId;
import org.ddd.fundamental.redis.config.RedisStoreManager;
import org.ddd.fundamental.shared.api.equipment.EquipmentDTO;
import org.ddd.fundamental.shared.api.equipment.RPAccountDTO;
import org.ddd.fundamental.shared.api.equipment.ToolingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
public class EquipmentQueryService {

    private final EquipmentRepository equipmentRepository;

    private final ToolingEquipmentRepository toolingRepository;

    private final RPAccountRepository accountRepository;

    private final RedisStoreManager manager;

    @Autowired
    public EquipmentQueryService(EquipmentRepository equipmentRepository,
                                 ToolingEquipmentRepository toolingRepository,
                                 RPAccountRepository accountRepository,
                                 RedisStoreManager manager){
        this.equipmentRepository = equipmentRepository;
        this.toolingRepository = toolingRepository;
        this.accountRepository = accountRepository;
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

    public Equipment findById(EquipmentId equipmentId){
        Equipment equipment = equipmentRepository.findById(equipmentId).orElse(null);
        if (null == equipment){
            String msg = "id:{} 对应的Equipment不存在.";
            throw new RuntimeException(msg.formatted(msg,equipmentId.toUUID()));
        }
        return equipment;
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

    /**
     * 查询所有的RPA账号
     * @return
     */
    public List<RPAccountDTO> rpAccountList(){
        List<RPAccountDTO> rpAccountDTOS = manager.queryAllData(RPAccountDTO.class);
        if (!CollectionUtils.isEmpty(rpAccountDTOS)){
            log.info("rpAccountDTOS data from redis cache");
            return rpAccountDTOS;
        }
        return EquipmentConverter.entityToRPAccountDTO(accountRepository.findAll());
    }

    public List<RPAccount> findByIdIn(List<RPAccountId> accountIds){
        return accountRepository.findByIdIn(accountIds);
    }

    public RPAccount getProxyRPAccount(RPAccountId rpAccountId){
        return accountRepository.getOne(rpAccountId);
    }

    public Equipment getProxyEquipment(EquipmentId equipmentId){
        return equipmentRepository.getOne(equipmentId);
    }
}
