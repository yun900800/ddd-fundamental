package org.ddd.fundamental.equipment.application.command;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.day.YearModelValue;
import org.ddd.fundamental.equipment.application.query.EquipmentQueryService;
import org.ddd.fundamental.equipment.domain.model.*;
import org.ddd.fundamental.equipment.domain.repository.*;
import org.ddd.fundamental.equipment.enums.EquipmentType;
import org.ddd.fundamental.equipment.helper.EquipmentHelper;
import org.ddd.fundamental.equipment.value.*;
import org.ddd.fundamental.equipment.value.business.WorkOrderComposable;
import org.ddd.fundamental.factory.EquipmentId;
import org.ddd.fundamental.redis.config.RedisStoreManager;
import org.ddd.fundamental.shared.api.material.MaterialDTO;
import org.ddd.fundamental.utils.CollectionUtils;
import org.ddd.fundamental.workprocess.enums.ProductResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional
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

    public void createEquipment(EquipmentMaster master, YearModelValue model,
                                EquipmentType type, ChangeableInfo resource,
                                ProductResourceType resourceType){
        Equipment equipment = Equipment.create(model, type, master);
        EquipmentResource resource0 = EquipmentResource.create(EquipmentResourceValue.create(
                equipment.id(), resourceType, resource
        ));
        resource0.configureEquipmentPositionType(CollectionUtils.random(EquipmentHelper.positionTypes()));
        equipment.setResource(resource0);
        equipmentRepository.persist(equipment);
    }

    public void addToolingToEquipment(EquipmentId toolingId, EquipmentId equipmentId) {
        ToolingEquipment toolingEquipment = equipmentQueryService.findToolingById(toolingId);
        Equipment equipment = equipmentQueryService.getProxyEquipment(equipmentId);
        toolingEquipment.setEquipment(equipment);
        toolingEquipment.enableUse();
    }

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

    public void deleteAllEquipments() {
        resourceRepository.deleteAllEquipmentResources();
        equipmentRepository.deleteAllEquipments();
        planRepository.deleteAllEquipmentPlans();
    }

    private void deleteToolingRelation(Equipment equipment){
        ToolingEquipment toolingEquipment = toolingRepository.findByEquipment(equipment);
        toolingEquipment.setEquipment(null);
    }


    /**
     * 根据id删除设备信息
     * @param equipmentId
     */
    public void deleteEquipmentById(EquipmentId equipmentId){
        Equipment equipment = equipmentQueryService.findById(equipmentId);
        EquipmentId planId = null;
        if (null !=equipment.getEquipmentPlan()) {
            planId = equipment.getEquipmentPlan().id();
        }
        equipmentRPAccountRepository.deleteAccountRelationByEquipmentId(equipmentId);
        deleteToolingRelation(equipment);
        equipmentRepository.deleteById(equipmentId);
        if (null != planId ) {
            planRepository.deleteById(planId);
        }
    }

    public void saveAllEquipment(List<Equipment> equipmentList){
        this.equipmentRepository.persistAll(equipmentList);
    }

    public void deleteAllTooling(){
        toolingRepository.deleteAllTooling();
    }

    public void saveAllTooling(List<ToolingEquipment> toolingEquipments){
        toolingRepository.persistAll(toolingEquipments);
    }

    public void deleteAllRPAccount(){
        this.equipmentRPAccountRepository.deleteAllAccounts();
        this.accountRepository.deleteAllRPAccounts();
    }

    public void saveAllRPAccount(List<RPAccount> rpAccounts){
        this.accountRepository.persistAll(rpAccounts);
    }

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
    public void addRpAccountToEquipment(EquipmentId equipmentId,
                                        RPAccountId accountId,
                                        BusinessRange<WorkOrderComposable> value){
        Equipment equipment = equipmentQueryService.getProxyEquipment(equipmentId);
        RPAccount account = equipmentQueryService.getProxyRPAccount(accountId);
        EquipmentRPAccount equipmentRPAccount = EquipmentRPAccount.create(equipment,account,value);
        equipmentRPAccountRepository.persist(equipmentRPAccount);
    }

    /**
     * 为设备添加可以处理的输入和输出物料信息
     * @param equipmentId
     * @param materialInputs
     * @param materialOutputs
     */
    public void configureEquipmentInputAndOutput(EquipmentId equipmentId,
                                                 List<MaterialDTO> materialInputs,
                                                 List<MaterialDTO> materialOutputs){
        Equipment equipment = equipmentQueryService.findById(equipmentId);
        equipment.getEquipmentResource().addMaterialPairs(materialInputs,materialOutputs);
    }

    /**
     * 移除设备的输入或者输出物料信息
     * @param equipmentId
     * @param materialInputs
     * @param materialOutputs
     */
    public void removeEquipmentInputAndOutput(EquipmentId equipmentId,
                                             List<MaterialDTO> materialInputs,
                                             List<MaterialDTO> materialOutputs){
        Equipment equipment = equipmentQueryService.findById(equipmentId);
        equipment.getEquipmentResource().removeMaterialPairs(materialInputs, materialOutputs);
    }

    /**
     * 修改设备的名称信息等
     * @param equipmentId
     * @param info
     */
    public void changeInfo(EquipmentId equipmentId,ChangeableInfo info){
        Equipment equipment = equipmentQueryService.findById(equipmentId);
        equipment.changeInfo(info);
    }

    /**
     * 修改设备的大小尺寸
     * @param equipmentId
     * @param size
     */
    public void changeEquipmentSize(EquipmentId equipmentId,EquipmentSize size){
        Equipment equipment = equipmentQueryService.findById(equipmentId);
        equipment.changeEquipmentSize(size);
    }

    /**
     * 修改设备的维护标准
     * @param equipmentId
     * @param standard
     */
    public void changeMaintainStandard(EquipmentId equipmentId,MaintainStandard standard) {
        Equipment equipment = equipmentQueryService.findById(equipmentId);
        equipment.changeMaintainStandard(standard);
    }

    /**
     * 修改设备的人员信息
     * @param equipmentId
     * @param personInfo
     */
    public void changePersonInfo(EquipmentId equipmentId,PersonInfo personInfo){
        Equipment equipment = equipmentQueryService.findById(equipmentId);
        equipment.changePersonInfo(personInfo);
    }

    /**
     * 修改设备的质量信息
     * @param equipmentId
     * @param qualityInfo
     */
    public void changeQuantityInfo(EquipmentId equipmentId,QualityInfo qualityInfo){
        Equipment equipment = equipmentQueryService.findById(equipmentId);
        equipment.changeQuantityInfo(qualityInfo);
    }

    /**
     * 修改设备的资产编号
     * @param equipmentId
     * @param assetNo
     */
    public void changeAssetNo(EquipmentId equipmentId,String assetNo) {
        Equipment equipment = equipmentQueryService.findById(equipmentId);
        equipment.changeAssetNo(assetNo);
    }

}
