package org.ddd.fundamental.equipment.schedule;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.day.YearModelValue;
import org.ddd.fundamental.equipment.application.command.EquipmentCommandService;
import org.ddd.fundamental.equipment.domain.model.Equipment;
import org.ddd.fundamental.equipment.domain.model.EquipmentResource;
import org.ddd.fundamental.equipment.enums.EquipmentType;
import org.ddd.fundamental.equipment.helper.EquipmentHelper;
import org.ddd.fundamental.equipment.value.*;
import org.ddd.fundamental.shared.api.equipment.EquipmentRequest;
import org.ddd.fundamental.utils.CollectionUtils;
import org.ddd.fundamental.workprocess.enums.ProductResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Service
@Slf4j
public class EquipmentTemplateClient {

    private final EquipmentCommandService commandService;

    private static final String CREATE_EQUIPMENT = "http://localhost:9004/equipment/create_equipment";

    @Autowired
    public EquipmentTemplateClient(EquipmentCommandService commandService){
        this.commandService = commandService;
    }

    @Scheduled(cron = "*/600 * * * * ?")
    public void createEquipment(){
        YearModelValue modelValue = CollectionUtils.random(EquipmentHelper.yearModelValueList());
        String equipmentName = CollectionUtils.random(EquipmentHelper.equipmentNames());
        EquipmentMaster master = EquipmentMaster.newBuilder().assetNo(
                EquipmentHelper.assetNo("test_create")
        ).info(ChangeableInfo.create(equipmentName,equipmentName+"相关的描述"))
                .size(EquipmentSize.create(
                        CollectionUtils.random(EquipmentHelper.randomDoubles()),
                        CollectionUtils.random(EquipmentHelper.randomDoubles()),
                        CollectionUtils.random(EquipmentHelper.randomDoubles())
                )).standard(MaintainStandard.create(
                        CollectionUtils.random(EquipmentHelper.maintainRules())
                        ,new Date()))
                .personInfo(PersonInfo.create(
                        CollectionUtils.random(EquipmentHelper.personCounts()),"需要设备资质信息"
                )).qualityInfo(QualityInfo.create(
                        CollectionUtils.random(EquipmentHelper.checkStatus()),
                        CollectionUtils.random(EquipmentHelper.checkPlans()), true
                )).build();
        ChangeableInfo resource = ChangeableInfo.create(equipmentName,equipmentName+"相关的描述");
        EquipmentRequest equipmentRequest = new EquipmentRequest(
                master,modelValue, EquipmentType.RESOURCE_ONE,
                resource, ProductResourceType.EQUIPMENT

        );
        log.info("url is {}",CREATE_EQUIPMENT);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(CREATE_EQUIPMENT,equipmentRequest,Void.class);
        log.info("create equipment and resources finished");
    }

}
