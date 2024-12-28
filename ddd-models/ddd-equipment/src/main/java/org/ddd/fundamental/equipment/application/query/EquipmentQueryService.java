package org.ddd.fundamental.equipment.application.query;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.equipment.application.EquipmentConverter;
import org.ddd.fundamental.equipment.domain.model.Equipment;
import org.ddd.fundamental.equipment.domain.model.EquipmentResource;
import org.ddd.fundamental.equipment.domain.model.RPAccount;
import org.ddd.fundamental.equipment.domain.model.ToolingEquipment;
import org.ddd.fundamental.equipment.domain.repository.EquipmentRepository;
import org.ddd.fundamental.equipment.domain.repository.EquipmentResourceRepository;
import org.ddd.fundamental.equipment.domain.repository.RPAccountRepository;
import org.ddd.fundamental.equipment.domain.repository.ToolingEquipmentRepository;
import org.ddd.fundamental.equipment.helper.EquipmentHelper;
import org.ddd.fundamental.equipment.value.RPAccountId;
import org.ddd.fundamental.factory.EquipmentId;
import org.ddd.fundamental.material.client.MaterialClient;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.redis.config.RedisStoreManager;
import org.ddd.fundamental.shared.api.equipment.*;
import org.ddd.fundamental.shared.api.material.MaterialDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(readOnly = true)
public class EquipmentQueryService {

    private final EquipmentRepository equipmentRepository;

    private final ToolingEquipmentRepository toolingRepository;

    private final RPAccountRepository accountRepository;

    private final EquipmentResourceRepository resourceRepository;

    private final MaterialClient materialClient;

    private final RedisStoreManager manager;

    @Autowired(required = false)
    public EquipmentQueryService(EquipmentRepository equipmentRepository,
                                 ToolingEquipmentRepository toolingRepository,
                                 RPAccountRepository accountRepository,
                                 EquipmentResourceRepository resourceRepository,
                                 RedisStoreManager manager,
                                 MaterialClient materialClient){
        this.equipmentRepository = equipmentRepository;
        this.toolingRepository = toolingRepository;
        this.accountRepository = accountRepository;
        this.resourceRepository = resourceRepository;
        this.manager = manager;
        this.materialClient = materialClient;
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

    private Map<MaterialId,MaterialDTO> toMaterialMap(List<MaterialDTO> materialDTOS){
        if (CollectionUtils.isEmpty(materialDTOS)){
            return new HashMap<>();
        }
        return materialDTOS.stream().collect(Collectors.toMap(
                v->v.id(),
                v->v
        ));
    }

    public EquipmentInputOutputDTO getEquipmentInputOutput(EquipmentId equipmentId){
        Equipment equipment = findById(equipmentId);
        EquipmentResource equipmentResource = equipment.getEquipmentResource();
        Set<MaterialId> allInputAndOutputs = equipmentResource.allInputAndOutput();
        List<MaterialDTO> materialDTOS = materialClient.materialsByIds(allInputAndOutputs.stream()
                .map(v->v.toUUID()).collect(Collectors.toList()));
        Map<MaterialId,MaterialDTO> materialDTOMap = toMaterialMap(materialDTOS);
        List<MaterialDTO> inputs = equipmentResource.getEquipmentResourceValue().getInputs()
                .stream().map(materialDTOMap::get).collect(Collectors.toList());
        List<MaterialDTO> outputs = equipmentResource.getEquipmentResourceValue().getOutputs()
                .stream().map(materialDTOMap::get).collect(Collectors.toList());
        Set<ConfigureMaterialDTO> pairs = equipmentResource.getEquipmentResourceValue().getInputAndOutputPairs()
                .stream().map(v->{
                    List<MaterialDTO> inputsOfPairs = v.getMaterialInputs().stream()
                            .map(materialDTOMap::get).collect(Collectors.toList());
                    List<MaterialDTO> outputsOfPairs = v.getMaterialOutputs().stream()
                            .map(materialDTOMap::get).collect(Collectors.toList());
                    return ConfigureMaterialDTO.create(equipmentId,inputsOfPairs,outputsOfPairs);
                }).collect(Collectors.toSet());
        return EquipmentInputOutputDTO.create(equipmentId, equipment.getMaster(),
                inputs,outputs,pairs);
    }

    public Equipment findById(EquipmentId equipmentId){
        Equipment equipment = equipmentRepository.findById(equipmentId).orElse(null);
        if (null == equipment){
            String msg = "id:%s 对应的Equipment不存在.";
            throw new RuntimeException(String.format(msg,equipmentId.toUUID()));
        }
        return equipment;
    }

    public ToolingEquipment findToolingById(EquipmentId toolingId){
        ToolingEquipment toolingEquipment = toolingRepository.findById(toolingId).orElse(null);
        if (null == toolingEquipment){
            String msg = "id:{} 对应的ToolingEquipment不存在.";
            throw new RuntimeException(msg.formatted(msg,toolingId.toUUID()));
        }
        return toolingEquipment;
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

    public List<EquipmentResourceDTO> queryResourcesByInputAndOutput(MaterialId inputId,MaterialId outputId){
        List<EquipmentResource> resources =  resourceRepository.queryResourcesByInputAndOutput(inputId,outputId);
        return EquipmentConverter.entityToResourceDTO(resources);
    }

    public List<String> queryResourcesByInputAndOutputByJPQL(MaterialId inputId,MaterialId outputId){

        List<EquipmentResource> equipmentResources = resourceRepository.
                queryResourcesById("76fadfb9-f2d7-4991-a45f-320916c15fb8");
        log.info("equipmentResources is {}",EquipmentConverter.entityToResourceDTO(equipmentResources));
        List<String> resources =  resourceRepository.queryResourcesByInputAndOutputByJPQL(inputId,outputId);
//        return EquipmentConverter.entityToResourceDTO(resources);
        return resources;
    }

    public List<EquipmentResourceDTO> queryResourcesByInputAndOutputIds(List<MaterialId> inputIds, List<MaterialId> outputIds){
        List<EquipmentResource> resources =  resourceRepository.queryResourcesByInputAndOutputIds(inputIds,outputIds);
        return EquipmentConverter.entityToResourceDTO(resources);
    }

}
