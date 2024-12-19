package org.ddd.fundamental.equipment.application.command;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.day.range.DateRangeValue;
import org.ddd.fundamental.equipment.creator.EquipmentAddable;
import org.ddd.fundamental.equipment.creator.ToolingEquipmentAddable;
import org.ddd.fundamental.equipment.domain.model.Equipment;
import org.ddd.fundamental.equipment.domain.model.EquipmentPlan;
import org.ddd.fundamental.equipment.domain.model.ToolingEquipment;
import org.ddd.fundamental.equipment.domain.repository.EquipmentPlanRepository;
import org.ddd.fundamental.equipment.domain.repository.EquipmentRepository;
import org.ddd.fundamental.equipment.domain.repository.EquipmentResourceRepository;
import org.ddd.fundamental.equipment.domain.repository.ToolingEquipmentRepository;
import org.ddd.fundamental.equipment.value.EquipmentPlanRange;
import org.ddd.fundamental.equipment.value.EquipmentPlanValue;
import org.ddd.fundamental.factory.EquipmentId;
import org.ddd.fundamental.redis.config.RedisStoreManager;
import org.ddd.fundamental.workorder.value.WorkOrderId;
import org.ddd.fundamental.workprocess.value.WorkProcessId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@Slf4j
public class EquipmentCommandService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private ToolingEquipmentRepository toolingRepository;

    @Autowired
    private EquipmentResourceRepository resourceRepository;

    @Autowired
    private EquipmentPlanRepository planRepository;

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
        planRepository.deleteAllEquipmentPlans();
    }

    @Transactional
    public void saveAllEquipment(List<Equipment> equipmentList){
        this.equipmentRepository.persistAll(equipmentList);
    }

}
