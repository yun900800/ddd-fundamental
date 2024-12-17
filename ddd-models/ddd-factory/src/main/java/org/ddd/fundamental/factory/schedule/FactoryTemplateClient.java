package org.ddd.fundamental.factory.schedule;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.core.generator.Generators;
import org.ddd.fundamental.equipment.client.EquipmentClient;
import org.ddd.fundamental.factory.EquipmentId;
import org.ddd.fundamental.factory.MachineShopId;
import org.ddd.fundamental.factory.ProductionLineId;
import org.ddd.fundamental.factory.WorkStationId;
import org.ddd.fundamental.factory.application.command.FactoryCommandService;
import org.ddd.fundamental.factory.application.query.FactoryQueryService;
import org.ddd.fundamental.factory.domain.model.WorkStation;
import org.ddd.fundamental.factory.helper.FactoryHelper;
import org.ddd.fundamental.factory.value.MachineShopValueObject;
import org.ddd.fundamental.factory.value.ProductionLineValue;
import org.ddd.fundamental.factory.value.WorkStationValueObject;
import org.ddd.fundamental.shared.api.equipment.EquipmentDTO;
import org.ddd.fundamental.shared.api.factory.MachineShopDTO;
import org.ddd.fundamental.shared.api.factory.ProductLineDTO;
import org.ddd.fundamental.shared.api.factory.WorkStationDTO;
import org.ddd.fundamental.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FactoryTemplateClient {

    private final FactoryCommandService service;

    private final FactoryQueryService queryService;

    private final EquipmentClient equipmentClient;

    private static final String ADD_PRODUCT_LINE = "http://localhost:9006/factory/add-line";
    private static final String DELETE_WORK_STATION = "http://localhost:9006/factory/delete_work_station/%s/%s";

    private static final String DELETE_PRODUCT_LINE = "http://localhost:9006/factory/delete_line/%s";

    private static final String UPDATE_WORK_STATION = "http://localhost:9006/factory/update_station/%s";

    private static final String CHANGE_PRODUCT_LINE = "http://localhost:9006/factory/change_lineInfo/%s";

    private static final String ADD_EQUIPMENT_TO_LINE = "http://localhost:9006/factory/add_equipment_to_line/%s/%s";

    private static final String ADD_MACHINE_SHOP = "http://localhost:9006/factory/add_machineShop";
    private static final String CHANGE_MACHINE_SHOP = "http://localhost:9006/factory/change_machineShop/%s";

    private static final String ADD_LINE_TO_MACHINE = "http://localhost:9006/factory/add_line_to_machine/%s/%s";

    private static final String REMOVE_LINE_FROM_MACHINE = "http://localhost:9006/factory/remove_line_from_machine/%s/%s";

    private List<ProductLineDTO> cacheLineDTOS;

    private List<EquipmentDTO> cacheEquipmentDTOs;

    private List<MachineShopDTO> cacheMachineShopDTOs;

    @Autowired
    public FactoryTemplateClient(FactoryCommandService service,
                                 FactoryQueryService queryService,
                                 EquipmentClient equipmentClient){
        this.service = service;
        this.queryService = queryService;
        this.equipmentClient = equipmentClient;
    }

    public List<WorkStationDTO> createWorkStationDTOs(){
        List<WorkStationDTO> result = new ArrayList<>();
        Generators.fill(result, ()-> {
                    String workStationName = CollectionUtils.random(FactoryHelper.workStationNames());
                    WorkStationDTO workStationDTO =  WorkStationDTO.create(
                            new WorkStationId("0"),
                            new WorkStationValueObject(
                                    ChangeableInfo.create(
                                            workStationName
                                            ,
                                            workStationName + "_工位描述信息")
                            )
                    );
                    return workStationDTO;
                },10);
        return result;
    }

    @Scheduled(cron = "*/3000 * * * * ?")
    public void addProductLine(){
        String lineName = CollectionUtils.random(FactoryHelper.productLineNames());
        ProductLineDTO productLineDTO = ProductLineDTO.create(
                new ProductionLineId("0"),
                new ProductionLineValue(
                        ChangeableInfo.create(lineName,lineName+ "产线说明描述")
                ),
                new ArrayList<>(),
                createWorkStationDTOs()
        );
        log.info("url is {}",ADD_PRODUCT_LINE);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(ADD_PRODUCT_LINE,productLineDTO,Void.class);
        log.info("add product line finished");
    }

    @Scheduled(cron = "*/3600 * * * * ?")
    public void deleteWorkStation(){
        if (org.springframework.util.CollectionUtils.isEmpty(this.cacheLineDTOS)) {
            this.cacheLineDTOS = queryService.productLines();
        }
        ProductionLineId lineId = CollectionUtils.random(cacheLineDTOS).id();
        List<WorkStationDTO> workStationDTOS = queryService.findByLineId(lineId);
        WorkStationId stationId = CollectionUtils.random(workStationDTOS).id();
        String url = String.format(DELETE_WORK_STATION,lineId.toUUID(),stationId.toUUID());
        log.info("url is {}",url);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(url, null,Void.class);
        log.info("delete workstation from line finished");
        this.cacheLineDTOS.clear();
    }

    @Scheduled(cron = "*/3600 * * * * ?")
    public void deleteProductLine(){
        if (org.springframework.util.CollectionUtils.isEmpty(this.cacheLineDTOS)) {
            this.cacheLineDTOS = queryService.productLines();
        }
        ProductionLineId lineId = CollectionUtils.random(cacheLineDTOS).id();
        String url = String.format(DELETE_PRODUCT_LINE,lineId.toUUID());
        log.info("url is {}",url);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(url, null,Void.class);
        log.info("delete line finished");
        this.cacheLineDTOS.clear();
    }

    @Scheduled(cron = "*/3600 * * * * ?")
    public void updateWorkStation(){
        if (org.springframework.util.CollectionUtils.isEmpty(this.cacheLineDTOS)) {
            this.cacheLineDTOS = queryService.productLines();
        }
        WorkStationDTO station = CollectionUtils.random(cacheLineDTOS).getWorkStations().get(0);
        WorkStationId stationId = station.id();
        String workStationName = CollectionUtils.random(FactoryHelper.workStationNames());
        WorkStationDTO stationDTO = WorkStationDTO.create(stationId,
                new WorkStationValueObject(
                        ChangeableInfo.create(workStationName,"新的描述",true)
                ));
        String url = String.format(UPDATE_WORK_STATION,stationId.toUUID());
        log.info("url is {}",url);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(url, stationDTO,Void.class);
        log.info("update work_station finished");
    }

    @Scheduled(cron = "*/3600 * * * * ?")
    public void changeProductLineInfo(){
        if (org.springframework.util.CollectionUtils.isEmpty(this.cacheLineDTOS)) {
            this.cacheLineDTOS = queryService.productLines();
        }
        ProductionLineId lineId = CollectionUtils.random(cacheLineDTOS).id();
        String url = String.format(CHANGE_PRODUCT_LINE,lineId.toUUID());
        log.info("url is {}",url);
        String lineName = CollectionUtils.random(FactoryHelper.productLineNames());
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(url, ChangeableInfo.create(lineName,"新的产线描述",true),Void.class);
        log.info("change line finished");
    }

    @Scheduled(cron = "*/3600 * * * * ?")
    public void addEquipmentIdToLine(){
        if (org.springframework.util.CollectionUtils.isEmpty(this.cacheLineDTOS)) {
            this.cacheLineDTOS = queryService.productLines();
        }
        if (org.springframework.util.CollectionUtils.isEmpty(this.cacheEquipmentDTOs)) {
            this.cacheEquipmentDTOs = equipmentClient.equipments();
        }
        EquipmentId equipmentId = CollectionUtils.random(cacheEquipmentDTOs).id();
        ProductionLineId lineId = CollectionUtils.random(cacheLineDTOS).id();
        String url = String.format(ADD_EQUIPMENT_TO_LINE,lineId.toUUID(),equipmentId.toUUID());
        log.info("url is {}",url);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(url, null,Void.class);
        log.info("add equipment to line finished");
    }

    @Scheduled(cron = "*/3600 * * * * ?")
    public void addMachineShop(){
        String shopName = CollectionUtils.random(FactoryHelper.machineShopNames());
        MachineShopDTO machineShopDTO = MachineShopDTO.create(
                new MachineShopId("0"),
                new MachineShopValueObject(ChangeableInfo.create(
                        shopName,shopName+":相关描述"
                ))
        );
        log.info("url is {}",ADD_MACHINE_SHOP);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(ADD_MACHINE_SHOP,machineShopDTO,Void.class);
        log.info("add machine shop finished");
    }

    @Scheduled(cron = "*/3600 * * * * ?")
    public void changeMachineShop(){
        if (org.springframework.util.CollectionUtils.isEmpty(this.cacheMachineShopDTOs)) {
            this.cacheMachineShopDTOs = queryService.machineShops();
        }
        String shopName = CollectionUtils.random(FactoryHelper.machineShopNames());
        MachineShopId shopId = CollectionUtils.random(cacheMachineShopDTOs).id();
        ChangeableInfo shopInfo = ChangeableInfo.create(
                shopName,shopName+":相关描述"
        );
        String url = String.format(CHANGE_MACHINE_SHOP,shopId.toUUID());
        log.info("url is {}",url);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(url,shopInfo,Void.class);
        log.info("change machine shop finished");
    }

    @Scheduled(cron = "*/30 * * * * ?")
    public void removeLineFromMachine(){
        if (org.springframework.util.CollectionUtils.isEmpty(this.cacheMachineShopDTOs)) {
            this.cacheMachineShopDTOs = queryService.machineShops();
        }
        if (org.springframework.util.CollectionUtils.isEmpty(this.cacheLineDTOS)) {
            this.cacheLineDTOS = queryService.productLines();
        }
        String shopId = CollectionUtils.random(cacheMachineShopDTOs).id().toUUID();
        String lineId = CollectionUtils.random(cacheLineDTOS).id().toUUID();
        String url = String.format(REMOVE_LINE_FROM_MACHINE,shopId,lineId);
        log.info("url is {}",url);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(url,null,Void.class);
        log.info("remove line from shop finished");
    }

    @Scheduled(cron = "*/20 * * * * ?")
    public void addLineToMachine(){
        if (org.springframework.util.CollectionUtils.isEmpty(this.cacheMachineShopDTOs)) {
            this.cacheMachineShopDTOs = queryService.machineShops();
        }
        if (org.springframework.util.CollectionUtils.isEmpty(this.cacheLineDTOS)) {
            this.cacheLineDTOS = queryService.productLines();
        }
        String shopId = CollectionUtils.random(cacheMachineShopDTOs).id().toUUID();
        String lineId = CollectionUtils.random(cacheLineDTOS).id().toUUID();
        String url = String.format(ADD_LINE_TO_MACHINE,shopId,lineId);
        log.info("url is {}",url);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(url,null,Void.class);
        log.info("add line to machine shop finished");
    }
}
