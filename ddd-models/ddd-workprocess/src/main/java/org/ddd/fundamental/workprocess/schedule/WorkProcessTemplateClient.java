package org.ddd.fundamental.workprocess.schedule;


import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.shared.api.optemplate.WorkProcessTemplateDTO;
import org.ddd.fundamental.utils.CollectionUtils;
import org.ddd.fundamental.workprocess.application.query.WorkProcessTemplateQueryService;
import org.ddd.fundamental.workprocess.creator.WorkProcessTemplateAddable;
import org.ddd.fundamental.workprocess.enums.BatchManagable;
import org.ddd.fundamental.workprocess.value.WorkProcessBeat;
import org.ddd.fundamental.workprocess.value.WorkProcessTemplateId;
import org.ddd.fundamental.workprocess.value.controller.ReportWorkControl;
import org.ddd.fundamental.workprocess.value.controller.WorkProcessTemplateControl;
import org.ddd.fundamental.workprocess.value.quantity.WorkProcessTemplateQuantity;
import org.ddd.fundamental.workprocess.value.resources.ProductResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class WorkProcessTemplateClient {

    @Autowired
    private WorkProcessTemplateQueryService application;

    @Autowired
    private WorkProcessTemplateAddable templateAddable;

    private static final String CHANGE_BEAT = "http://localhost:9003/process/change_beat/%s";
    private static final String CHANGE_QUANTITY = "http://localhost:9003/process/change_quantity/%s";
    private static final String ADD_CONTROL = "http://localhost:9003/process/add_control/%s";
    private static final String ADD_RESOURCE = "http://localhost:9003/process/add_resource/%s";

    private static final String CONFIGURE_TEMPLATE_PRE_ID = "http://localhost:9003/process/configure_template_preId/%s/%s";

    private static final String CONFIGURE_TEMPLATE_NEXT_ID = "http://localhost:9003/process/configure_template_nextId/%s/%s";

    private static final String REMOVE_TEMPLATE_PRE_ID = "http://localhost:9003/process/remove_template_preId/%s/%s";

    private static final String REMOVE_TEMPLATE_NEXT_ID = "http://localhost:9003/process/remove_template_nextId/%s/%s";

    public static List<Integer> numbersOfProducts(){
        return Arrays.asList(10000,12000,14000,16000,18000,19000,25000,23000);
    }

    public static List<Integer> numbersOfProductsMinutes(){
        return Arrays.asList(8,12,20,50,80,120,160,90,75);
    }

    private List<WorkProcessTemplateDTO> cache;

    private List<ProductResource> cacheResources;

    @Scheduled(cron = "*/30000 * * * * ?")
    public void changeWorkProcessTemplateBeat(){
        if (org.springframework.util.CollectionUtils.isEmpty(cache)) {
            cache = application.workProcessTemplates();
        }
        WorkProcessTemplateDTO templateDTO =  CollectionUtils.random(cache);
        String id = templateDTO.id().toUUID();
        String url = String.format(CHANGE_BEAT,id);
        log.info("url is {}",url);
        WorkProcessBeat beat = WorkProcessBeat.create(CollectionUtils.random(numbersOfProducts()),
                CollectionUtils.random(numbersOfProductsMinutes()));
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(url,beat,Void.class);
        log.info("change workprocess beat finished");
    }

    @Scheduled(cron = "*/60000 * * * * ?")
    public void changeWorkProcessTemplateQuantity(){
        if (org.springframework.util.CollectionUtils.isEmpty(cache)) {
            cache = application.workProcessTemplates();
        }
        WorkProcessTemplateDTO templateDTO =  CollectionUtils.random(cache);
        String id = templateDTO.id().toUUID();
        String url = String.format(CHANGE_QUANTITY,id);
        log.info("url is {}",url);
        WorkProcessTemplateQuantity quantity = null;
        if (CollectionUtils.random(WorkProcessTemplateAddable.trueOrFalse())){
            quantity = WorkProcessTemplateQuantity.newBuilder()
                    .targetQualifiedRate(
                            CollectionUtils.random(WorkProcessTemplateAddable.doubleList())
                    ).transferPercent(CollectionUtils.random(WorkProcessTemplateAddable.doubleList()))
                    .overCrossPercent(CollectionUtils.random(WorkProcessTemplateAddable.doubleList()))
                    .build();
        } else {
            quantity = WorkProcessTemplateQuantity.newBuilder()
                    .targetQualifiedRate(
                            CollectionUtils.random(WorkProcessTemplateAddable.doubleList())
                    ).transferPercent(CollectionUtils.random(WorkProcessTemplateAddable.doubleList()))
                    .owePaymentPercent(CollectionUtils.random(WorkProcessTemplateAddable.doubleList()))
                    .build();
        }

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(url,quantity,Void.class);
        log.info("change workprocess quantity finished");
    }

    @Scheduled(cron = "*/60000 * * * * ?")
    public void addControlInfoToTemplate(){
        if (org.springframework.util.CollectionUtils.isEmpty(cache)) {
            cache = application.workProcessTemplates();
        }
        WorkProcessTemplateDTO templateDTO =  CollectionUtils.random(cache);
        String id = templateDTO.id().toUUID();
        String url = String.format(ADD_CONTROL,id);
        log.info("url is {}",url);
        WorkProcessTemplateControl control = new WorkProcessTemplateControl.Builder(
                1, CollectionUtils.random(Arrays.asList(BatchManagable.values()))
        ).canSplit(CollectionUtils.random(WorkProcessTemplateAddable.trueOrFalse()))
                .isAllowedChecked(CollectionUtils.random(WorkProcessTemplateAddable.trueOrFalse()))
                .reportWorkControl(ReportWorkControl.create(
                        CollectionUtils.random(WorkProcessTemplateAddable.trueOrFalse()), ""
        )).nextProcessSyncMinutes(CollectionUtils.random(numbersOfProductsMinutes()))
                .build();
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(url,control,Void.class);
        log.info("add control to work_process_template finished");
    }

    @Scheduled(cron = "*/60000 * * * * ?")
    public void addProductResource(){
        if (org.springframework.util.CollectionUtils.isEmpty(cache)) {
            cache = application.workProcessTemplates();
        }
        WorkProcessTemplateDTO templateDTO =  CollectionUtils.random(cache);
        String id = templateDTO.id().toUUID();
        String url = String.format(ADD_RESOURCE,id);
        log.info("url is {}",url);
        if (org.springframework.util.CollectionUtils.isEmpty(cacheResources)) {
            cacheResources = templateAddable.createProductResource();
        }
        log.info("cacheResources is {}",cacheResources);
        ProductResource resource = CollectionUtils.random(cacheResources);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(url,resource,Void.class);
        log.info("add resources to work_process_template finished");
    }

    @Scheduled(cron = "*/2000 * * * * ?")
    public void configurePreId(){
        if (org.springframework.util.CollectionUtils.isEmpty(cache)) {
            cache = application.workProcessTemplates();
        }
        WorkProcessTemplateId templateId = CollectionUtils.random(cache).id();
        WorkProcessTemplateId preId = CollectionUtils.random(cache).id();
        String url = String.format(CONFIGURE_TEMPLATE_PRE_ID,templateId.toUUID(),preId.toUUID());
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(url,null,Void.class);
        log.info("configure template with preId finished");
    }

    @Scheduled(cron = "*/2500 * * * * ?")
    public void configureNextId(){
        if (org.springframework.util.CollectionUtils.isEmpty(cache)) {
            cache = application.workProcessTemplates();
        }
        WorkProcessTemplateId templateId = CollectionUtils.random(cache).id();
        WorkProcessTemplateId nextId = CollectionUtils.random(cache).id();
        String url = String.format(CONFIGURE_TEMPLATE_NEXT_ID,templateId.toUUID(),nextId.toUUID());
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(url,null,Void.class);
        log.info("configure template with nextId finished");
    }

    @Scheduled(cron = "*/2000 * * * * ?")
    public void removePreId(){
        if (org.springframework.util.CollectionUtils.isEmpty(cache)) {
            cache = application.workProcessTemplates();
        }
        WorkProcessTemplateId templateId = CollectionUtils.random(cache).id();
        WorkProcessTemplateId preId = CollectionUtils.random(cache).id();
        String url = String.format(REMOVE_TEMPLATE_PRE_ID,templateId.toUUID(),preId.toUUID());
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(url,null,Void.class);
        log.info("remove template with preId finished");
    }

    @Scheduled(cron = "*/2500 * * * * ?")
    public void removeNextId(){
        if (org.springframework.util.CollectionUtils.isEmpty(cache)) {
            cache = application.workProcessTemplates();
        }
        WorkProcessTemplateId templateId = CollectionUtils.random(cache).id();
        WorkProcessTemplateId nextId = CollectionUtils.random(cache).id();
        String url = String.format(REMOVE_TEMPLATE_NEXT_ID,templateId.toUUID(),nextId.toUUID());
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(url,null,Void.class);
        log.info("remove template with nextId finished");
    }

}
