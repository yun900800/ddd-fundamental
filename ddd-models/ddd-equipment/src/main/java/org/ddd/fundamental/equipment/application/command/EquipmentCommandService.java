package org.ddd.fundamental.equipment.application.command;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.day.YearModelValue;
import org.ddd.fundamental.day.range.DateRangeValue;
import org.ddd.fundamental.equipment.application.query.EquipmentQueryService;
import org.ddd.fundamental.equipment.domain.model.*;
import org.ddd.fundamental.equipment.domain.repository.*;
import org.ddd.fundamental.equipment.enums.EquipmentType;
import org.ddd.fundamental.equipment.value.*;
import org.ddd.fundamental.equipment.value.business.WorkOrderComposable;
import org.ddd.fundamental.factory.EquipmentId;
import org.ddd.fundamental.redis.config.RedisStoreManager;
import org.ddd.fundamental.workorder.value.WorkOrderId;
import org.ddd.fundamental.workprocess.enums.ProductResourceType;
import org.ddd.fundamental.workprocess.value.WorkProcessId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
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
    private RPAccountRepository accountRepository;

    @Autowired
    private EquipmentRPAccountRepository equipmentRPAccountRepository;

    @Autowired
    private EquipmentQueryService equipmentQueryService;


    @Autowired
    private RedisStoreManager manager;

    @Transactional
    public void createEquipment(EquipmentMaster master, YearModelValue model,
                                EquipmentType type, ChangeableInfo resource,
                                ProductResourceType resourceType){
        Equipment equipment = Equipment.create(model, type, master);
        EquipmentResource resource0 = EquipmentResource.create(EquipmentResourceValue.create(
                equipment.id(), resourceType, resource
        ));
        equipment.setResource(resource0);
        equipmentRepository.persist(equipment);
    }

    @Transactional
    public void addToolingToEquipment(EquipmentId toolingId, EquipmentId equipmentId) {
        ToolingEquipment toolingEquipment = equipmentQueryService.findToolingById(toolingId);
        Equipment equipment = equipmentQueryService.getProxyEquipment(equipmentId);
        toolingEquipment.setEquipment(equipment);
        toolingEquipment.enableUse();
    }

    @Transactional
    public void addBusinessPlanRangeToEquipment(EquipmentId equipmentId,BusinessRange<WorkOrderComposable> addedValue){
        Equipment equipment = equipmentQueryService.findById(equipmentId);
        if (equipment.getEquipmentPlan() == null){
            EquipmentPlan equipmentPlan = EquipmentPlan.create(EquipmentPlanValue.create())
                    .addBusinessEquipmentPlan(addedValue);
            equipment.setEquipmentPlan(equipmentPlan);
        } else {
            equipment.getEquipmentPlan()
                    .addBusinessEquipmentPlan(addedValue);
        }
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

    @Transactional
    public void deleteAllTooling(){
        toolingRepository.deleteAllTooling();
    }

    @Transactional
    public void saveAllTooling(List<ToolingEquipment> toolingEquipments){
        toolingRepository.persistAll(toolingEquipments);
    }

    @Transactional
    public void deleteAllRPAccount(){
        this.equipmentRPAccountRepository.deleteAllAccounts();
        this.accountRepository.deleteAllRPAccounts();
    }

    @Transactional
    public void saveAllRPAccount(List<RPAccount> rpAccounts){
        this.accountRepository.persistAll(rpAccounts);
    }

    @Transactional
    public void addRpAccountsToEquipment(EquipmentId equipmentId,
                                         List<RPAccountId> accountIds){
        Equipment equipment = equipmentQueryService.getProxyEquipment(equipmentId);
        List<EquipmentRPAccount> rpAccountList = new ArrayList<>();
        for (RPAccountId accountId: accountIds) {
            RPAccount account = equipmentQueryService.getProxyRPAccount(accountId);
            EquipmentRPAccount equipmentRPAccount = EquipmentRPAccount.create(equipment,account);
            rpAccountList.add(equipmentRPAccount);
        }
        equipmentRPAccountRepository.persistAll(rpAccountList);
    }

    /**
     * 添加账户信息到设备
     * @param equipmentId
     * @param accountId
     * @param value
     */
    @Transactional
    public void addRpAccountToEquipment(EquipmentId equipmentId,
                                        RPAccountId accountId,
                                        BusinessRange<WorkOrderComposable> value){
        Equipment equipment = equipmentQueryService.getProxyEquipment(equipmentId);
        RPAccount account = equipmentQueryService.getProxyRPAccount(accountId);
        EquipmentRPAccount equipmentRPAccount = EquipmentRPAccount.create(equipment,account,value);
        equipmentRPAccountRepository.persist(equipmentRPAccount);
    }
}
