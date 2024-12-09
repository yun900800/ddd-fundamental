package org.ddd.fundamental.equipment.application;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.day.range.DateRangeValue;
import org.ddd.fundamental.equipment.creator.EquipmentAddable;
import org.ddd.fundamental.equipment.creator.ToolingEquipmentAddable;
import org.ddd.fundamental.equipment.domain.model.Equipment;
import org.ddd.fundamental.equipment.domain.model.EquipmentPlan;
import org.ddd.fundamental.equipment.domain.model.ToolingEquipment;
import org.ddd.fundamental.equipment.domain.repository.EquipmentRepository;
import org.ddd.fundamental.equipment.domain.repository.EquipmentResourceRepository;
import org.ddd.fundamental.equipment.domain.repository.ToolingEquipmentRepository;
import org.ddd.fundamental.equipment.value.EquipmentPlanRange;
import org.ddd.fundamental.equipment.value.EquipmentPlanValue;
import org.ddd.fundamental.factory.EquipmentId;
import org.ddd.fundamental.redis.config.RedisStoreManager;
import org.ddd.fundamental.shared.api.equipment.EquipmentDTO;
import org.ddd.fundamental.shared.api.equipment.ToolingDTO;
import org.ddd.fundamental.workorder.value.WorkOrderId;
import org.ddd.fundamental.workprocess.value.WorkProcessId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private ToolingEquipmentRepository toolingRepository;

    @Autowired
    private EquipmentResourceRepository resourceRepository;

    @Autowired
    private ToolingEquipmentAddable creator;

    @Autowired
    private EquipmentAddable equipmentAddable;

    @Autowired
    private RedisStoreManager manager;

    @Transactional
    public void addToolingToEquipment(EquipmentId toolingId, EquipmentId equipmentId) {
        ToolingEquipment toolingEquipment = toolingRepository.findById(toolingId).orElse(null);
        Equipment equipment = equipmentRepository.getOne(equipmentId);
        toolingEquipment.setEquipment(equipment);
        toolingEquipment.enableUse();
        toolingRepository.save(toolingEquipment);
    }

    @Transactional
    public void addWorkOrderPlanToEquipment(
                    EquipmentId equipmentId,
                    WorkOrderId workOrderId,
                    WorkProcessId workProcessId,
                    Instant start, Instant end){
        Equipment equipment = equipmentRepository.findById(equipmentId).orElse(null);
        if (null == equipment){
            log.info("equipment not exists");
            return;
        }
        DateRangeValue workPlan = DateRangeValue.create(start,end,"工单占用设备的时间");
        if (equipment.getEquipmentPlan() == null){
            EquipmentPlan equipmentPlan = EquipmentPlan.create(EquipmentPlanValue.create())
                    .addEquipmentPlan(EquipmentPlanRange.create(workPlan,workOrderId,workProcessId));
            equipment.setEquipmentPlan(equipmentPlan);
        } else {
            equipment.getEquipmentPlan()
                    .addEquipmentPlan(EquipmentPlanRange.create(workPlan,workOrderId,workProcessId));
        }
        equipmentRepository.save(equipment);

    }

    @Transactional
    public void deleteAllEquipments() {
        resourceRepository.deleteAllEquipmentResources();
        equipmentRepository.deleteAllEquipments();
    }

    @Transactional(readOnly = true)
    public List<EquipmentDTO> equipments() {
        if (null != equipmentAddable.getEquipments() && !CollectionUtils.isEmpty(equipmentAddable.getEquipments())) {
            log.info("data from local cache");
            return equipmentAddable.getEquipments().stream()
                    .map(v-> EquipmentDTO.create(v.id(),v.getMaster())).collect(Collectors.toList());
        }
        List<EquipmentDTO> equipmentDTOS = manager.queryAllData(EquipmentDTO.class);
        if (!CollectionUtils.isEmpty(equipmentDTOS)) {
            log.info("equipmentDTOS data from redis cache");
            return equipmentDTOS;
        }
        return equipmentRepository.findAll().stream()
                .map(v-> EquipmentDTO.create(v.id(),v.getMaster())).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ToolingDTO> toolingList() {
        if (null != creator.getToolingEquipments() && !CollectionUtils.isEmpty(creator.getToolingEquipments())) {
            log.info("data from local cache");
            return creator.getToolingEquipments().stream()
                    .map(v-> {
                        if (v.getEquipment() == null) {
                            return ToolingDTO.create(v.id(),v.getToolingEquipmentInfo(),null);
                        }
                        return ToolingDTO.create(v.id(),v.getToolingEquipmentInfo(),v.getEquipment().id());
                    }).collect(Collectors.toList());
        }
        List<ToolingDTO> toolingDTOS = manager.queryAllData(ToolingDTO.class);
        if (!CollectionUtils.isEmpty(toolingDTOS)){
            log.info("toolingDTOS data from redis cache");
            return toolingDTOS;
        }
        return toolingRepository.findAll().stream()
                .map(v-> {
                    if (v.getEquipment() == null) {
                        return ToolingDTO.create(v.id(),v.getToolingEquipmentInfo(),null);
                    }
                    return ToolingDTO.create(v.id(),v.getToolingEquipmentInfo(),v.getEquipment().id());
                }).collect(Collectors.toList());
    }
}
