package org.ddd.fundamental.equipment.schedule;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.day.YearModelValue;
import org.ddd.fundamental.day.range.DateRangeValue;
import org.ddd.fundamental.equipment.application.command.EquipmentCommandService;
import org.ddd.fundamental.equipment.application.query.EquipmentQueryService;
import org.ddd.fundamental.equipment.domain.model.Equipment;
import org.ddd.fundamental.equipment.domain.model.EquipmentResource;
import org.ddd.fundamental.equipment.enums.EquipmentType;
import org.ddd.fundamental.equipment.helper.EquipmentHelper;
import org.ddd.fundamental.equipment.value.*;
import org.ddd.fundamental.factory.EquipmentId;
import org.ddd.fundamental.shared.api.equipment.EquipmentDTO;
import org.ddd.fundamental.shared.api.equipment.EquipmentRequest;
import org.ddd.fundamental.shared.api.equipment.RPAccountDTO;
import org.ddd.fundamental.shared.api.equipment.ToolingDTO;
import org.ddd.fundamental.utils.CollectionUtils;
import org.ddd.fundamental.workorder.value.WorkOrderId;
import org.ddd.fundamental.workprocess.enums.ProductResourceType;
import org.ddd.fundamental.workprocess.value.WorkProcessId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EquipmentTemplateClient {

    private final EquipmentCommandService commandService;

    private final EquipmentQueryService queryService;

    private static final String CREATE_EQUIPMENT = "http://localhost:9004/equipment/create_equipment";

    private static final String ADD_ACCOUNTS_TO_EQUIPMENT = "http://localhost:9004/equipment/add_accounts_to_equipment/%s";

    private static final String ADD_ACCOUNT_TO_EQUIPMENT = "http://localhost:9004/equipment/add_account_to_equipment/%s/%s";

    private static final String ADD_TOOLING_TO_EQUIPMENT = "http://localhost:9004/equipment/add_tooling_to_equipment/%s/%s";

    @Autowired
    public EquipmentTemplateClient(EquipmentCommandService commandService,
                                   EquipmentQueryService queryService){
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @Scheduled(cron = "*/36000 * * * * ?")
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

    @Scheduled(cron = "*/36000 * * * * ?")
    public void addRpAccountsToEquipment(){
        List<EquipmentDTO> equipments = queryService.equipments();
        EquipmentId equipmentId = CollectionUtils.random(equipments).id();
        String url = String.format(ADD_ACCOUNTS_TO_EQUIPMENT,equipmentId.toUUID());
        List<RPAccountId> accountIds = queryService.rpAccountList().stream()
                .map(v->v.id()).collect(Collectors.toList());
        log.info("url is {}",url);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(url,accountIds,Void.class);
        log.info("add rpaAccounts to equipment finished");
    }

    @Scheduled(cron = "*/36000 * * * * ?")
    public void addRpAccountToEquipment(){
        List<EquipmentDTO> equipments = queryService.equipments();
        EquipmentId equipmentId = CollectionUtils.random(equipments).id();
        List<RPAccountDTO> accounts = queryService.rpAccountList();
        RPAccountId rpAccountId = CollectionUtils.random(accounts).id();
        String url = String.format(ADD_ACCOUNT_TO_EQUIPMENT,equipmentId.toUUID(),rpAccountId.toUUID());
        Instant start = Instant.now();
        EquipmentRPAccountValue value = EquipmentRPAccountValue.create(
                WorkOrderId.randomId(WorkOrderId.class),
                WorkProcessId.randomId(WorkProcessId.class),
                DateRangeValue.create(start, start.plusSeconds(3600*4),"测试不同的原因")
        );
        log.info("url is {}",url);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(url,value,Void.class);
        log.info("add rpaAccount to equipment finished");
    }

    @Scheduled(cron = "*/20 * * * * ?")
    public void addToolingToEquipment(){
        List<EquipmentDTO> equipments = queryService.equipments();
        EquipmentId equipmentId = CollectionUtils.random(equipments).id();
        List<ToolingDTO> toolingDTOS = queryService.toolingList();
        EquipmentId toolingId = CollectionUtils.random(toolingDTOS).id();
        String url = String.format(ADD_TOOLING_TO_EQUIPMENT,equipmentId.toUUID(),toolingId.toUUID());
        log.info("url is {}",url);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(url,null,Void.class);
        log.info("add tooling to equipment finished");
    }

}
