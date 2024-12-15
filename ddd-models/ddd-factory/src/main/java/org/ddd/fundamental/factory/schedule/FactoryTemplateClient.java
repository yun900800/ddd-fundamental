package org.ddd.fundamental.factory.schedule;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.core.generator.Generators;
import org.ddd.fundamental.factory.ProductionLineId;
import org.ddd.fundamental.factory.WorkStationId;
import org.ddd.fundamental.factory.application.command.FactoryCommandService;
import org.ddd.fundamental.factory.application.query.FactoryQueryService;
import org.ddd.fundamental.factory.domain.model.WorkStation;
import org.ddd.fundamental.factory.helper.FactoryHelper;
import org.ddd.fundamental.factory.value.ProductionLineValue;
import org.ddd.fundamental.factory.value.WorkStationValueObject;
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

    private static final String ADD_PRODUCT_LINE = "http://localhost:9006/factory/add-line";
    private static final String DELETE_WORK_STATION = "http://localhost:9006/factory/delete_work_station/%s/%s";

    private static final String DELETE_PRODUCT_LINE = "http://localhost:9006/factory/delete_line/%s";

    private static final String UPDATE_WORK_STATION = "http://localhost:9006/factory/update_station/%s";

    private static final String CHANGE_PRODUCT_LINE = "http://localhost:9006/factory/change_lineInfo/%s";

    private List<ProductLineDTO> cacheLineDTOS;

    @Autowired
    public FactoryTemplateClient(FactoryCommandService service,
                                 FactoryQueryService queryService){
        this.service = service;
        this.queryService = queryService;
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
}
