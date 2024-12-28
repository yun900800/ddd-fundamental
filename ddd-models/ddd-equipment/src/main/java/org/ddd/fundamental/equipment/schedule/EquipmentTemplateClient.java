package org.ddd.fundamental.equipment.schedule;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.day.YearModelValue;
import org.ddd.fundamental.day.range.DateRangeValue;
import org.ddd.fundamental.equipment.application.command.EquipmentCommandService;
import org.ddd.fundamental.equipment.application.query.EquipmentQueryService;
import org.ddd.fundamental.equipment.enums.EquipmentType;
import org.ddd.fundamental.equipment.helper.EquipmentHelper;
import org.ddd.fundamental.equipment.value.*;
import org.ddd.fundamental.equipment.value.business.WorkOrderComposable;
import org.ddd.fundamental.factory.EquipmentId;
import org.ddd.fundamental.material.client.MaterialClient;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.material.value.MaterialType;
import org.ddd.fundamental.shared.api.equipment.*;
import org.ddd.fundamental.shared.api.material.MaterialDTO;
import org.ddd.fundamental.utils.CollectionUtils;
import org.ddd.fundamental.workorder.value.WorkOrderId;
import org.ddd.fundamental.workprocess.enums.ProductResourceType;
import org.ddd.fundamental.workprocess.value.WorkProcessId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EquipmentTemplateClient {

    private final EquipmentCommandService commandService;

    private final EquipmentQueryService queryService;

    private final MaterialClient materialClient;

    private static final String CREATE_EQUIPMENT = "http://localhost:9004/equipment/create_equipment";

    private static final String ADD_ACCOUNTS_TO_EQUIPMENT = "http://localhost:9004/equipment/add_accounts_to_equipment/%s";

    private static final String ADD_ACCOUNT_TO_EQUIPMENT = "http://localhost:9004/equipment/add_account_to_equipment/%s/%s";

    private static final String ADD_TOOLING_TO_EQUIPMENT = "http://localhost:9004/equipment/add_tooling_to_equipment/%s/%s";

    private static final String ADD_PLAN_TO_EQUIPMENT = "http://localhost:9004/equipment/add_plan_to_equipment/%s";

    private static final String CONFIGURE_MATERIAL_INPUT_OUTPUT = "http://localhost:9004/equipment/configure_material_input_output/%s";

    private static final String QUERY_RESOURCES_INPUT_OUTPUT = "http://localhost:9004/equipment/resources_by_input_output";

    @Autowired(required = false)
    public EquipmentTemplateClient(EquipmentCommandService commandService,
                                   EquipmentQueryService queryService,
                                   MaterialClient materialClient){
        this.commandService = commandService;
        this.queryService = queryService;
        this.materialClient = materialClient;
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
        BusinessRange<WorkOrderComposable> businessRange = new BusinessRange<>(
                WorkOrderComposable.create(WorkOrderId.randomId(WorkOrderId.class),WorkProcessId.randomId(WorkProcessId.class)),
                DateRangeValue.create(start, start.plusSeconds(3600*4),"测试不同的原因")
        );
        log.info("url is {}",url);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(url,businessRange,Void.class);
        log.info("add rpaAccount to equipment finished");
    }

    @Scheduled(cron = "*/36000 * * * * ?")
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

    @Scheduled(cron = "*/20000 * * * * ?")
    public void addBusinessPlanRangeToEquipment(){
        List<EquipmentDTO> equipments = queryService.equipments();
        EquipmentId equipmentId = CollectionUtils.random(equipments).id();
        String url = String.format(ADD_PLAN_TO_EQUIPMENT,equipmentId.toUUID());
        Instant start = Instant.now();
        BusinessRange<WorkOrderComposable> businessRange = new BusinessRange<>(
                WorkOrderComposable.create(WorkOrderId.randomId(WorkOrderId.class),WorkProcessId.randomId(WorkProcessId.class)),
                DateRangeValue.create(start, start.plusSeconds(3600*4),"测试不同的原因")
        );
        log.info("url is {}",url);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(url,businessRange,Void.class);
        log.info("add tooling to equipment finished");
    }

    private static int adjustInputCount(int inputCount){
        if (inputCount > 2){
            return 2;
        }
        if (inputCount < 1 ) {
            return 1;
        }
        return 2;
    }

    private static int adjustOutputCount(int outputCount){
        return 1;
    }

    private static List<MaterialDTO> inputsOrOutput(List<MaterialDTO> rawMaterialList,
                                            List<MaterialDTO> spareMaterialList){
        int randomOneCount = new Random().nextInt(rawMaterialList.size()/2);
        randomOneCount = adjustInputCount(randomOneCount);
        List<MaterialDTO> result = new ArrayList<>();
        for (int i = 0 ; i< randomOneCount;i++ ) {
            result.add(CollectionUtils.random(rawMaterialList));
        }
        int randomTwoCount = new Random().nextInt(spareMaterialList.size()/2);
        randomTwoCount = adjustOutputCount(randomTwoCount);
        for (int i = 0 ; i< randomTwoCount;i++ ) {
            result.add(CollectionUtils.random(spareMaterialList));
        }
        return result;
    }

    @Scheduled(cron = "*/2000 * * * * ?")
    public void configureEquipmentInputAndOutput(){
        List<MaterialDTO> rawMaterialList = materialClient.materialsByMaterialType(MaterialType.RAW_MATERIAL);
        List<MaterialDTO> spareMaterialList = materialClient.materialsByMaterialType(MaterialType.WORKING_IN_PROGRESS);
        List<MaterialDTO> productsList = materialClient.materialsByMaterialType(MaterialType.PRODUCTION);
        List<EquipmentDTO> equipments = queryService.equipments();
        EquipmentId equipmentId = CollectionUtils.random(equipments).id();
        String url = String.format(CONFIGURE_MATERIAL_INPUT_OUTPUT,equipmentId.toUUID());
        List<MaterialDTO> inputs = inputsOrOutput(rawMaterialList,spareMaterialList);
        List<MaterialDTO> outputs = inputsOrOutput(spareMaterialList,productsList);
        ConfigureMaterialDTO configureMaterialDTO = ConfigureMaterialDTO.create(equipmentId,
                inputs,outputs);
        log.info("url is {}",url);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(url,configureMaterialDTO,Void.class);
        log.info("configure material input and output finished");
    }

    @Scheduled(cron = "*/20 * * * * ?")
    public void queryResourcesByInputAndOutputIds(){
        String url = QUERY_RESOURCES_INPUT_OUTPUT;
        ConfigureMaterialDTO configureMaterialDTO =
                ConfigureMaterialDTO.create(
                        EquipmentId.randomId(EquipmentId.class),
                        Arrays.asList(
                                MaterialDTO.create(null,new MaterialId("68b95a96-e3ca-4e93-b2e3-d7b78cc4343e"),
                                        MaterialType.RAW_MATERIAL),
                                MaterialDTO.create(null,new MaterialId("608cdac8-d58c-47f2-a30d-1d9ec051ad03"),
                                        MaterialType.RAW_MATERIAL)
                        ),
                        Arrays.asList(
                                MaterialDTO.create(null,new MaterialId("c5ddab93-3f57-473e-bd2d-df825daab0af"),
                                        MaterialType.RAW_MATERIAL),
                                MaterialDTO.create(null,new MaterialId("0c16a03b-1e9a-4c41-8a29-800a26310719"),
                                        MaterialType.RAW_MATERIAL)
                        )
                );
        log.info("url is {}",url);
        RestTemplate restTemplate = new RestTemplate(new CustomHttpComponentsClientHttpRequestFactory());
        List<EquipmentResourceDTO> dtoList = restTemplate.exchange(url, HttpMethod.GET,
                new HttpEntity<>(configureMaterialDTO), List.class).getBody();
        log.info("dtoList is {}",dtoList);
    }

}
