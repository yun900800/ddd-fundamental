package org.ddd.fundamental.workprocess.schedule;


import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.shared.api.optemplate.WorkProcessTemplateDTO;
import org.ddd.fundamental.utils.CollectionUtils;
import org.ddd.fundamental.workprocess.application.query.WorkProcessTemplateApplication;
import org.ddd.fundamental.workprocess.creator.WorkProcessTemplateAddable;
import org.ddd.fundamental.workprocess.enums.BatchManagable;
import org.ddd.fundamental.workprocess.value.WorkProcessBeat;
import org.ddd.fundamental.workprocess.value.controller.ReportWorkControl;
import org.ddd.fundamental.workprocess.value.controller.WorkProcessTemplateControl;
import org.ddd.fundamental.workprocess.value.quantity.WorkProcessTemplateQuantity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class WorkProcessTemplateClient {

    @Autowired
    private WorkProcessTemplateApplication application;

    private static final String CHANGE_BEAT = "http://localhost:9003/process/change_beat/%s";
    private static final String CHANGE_QUANTITY = "http://localhost:9003/process/change_quantity/%s";
    private static final String ADD_CONTROL = "http://localhost:9003/process/add_control/%s";

    public static List<Integer> numbersOfProducts(){
        return Arrays.asList(10000,12000,14000,16000,18000,19000,25000,23000);
    }

    public static List<Integer> numbersOfProductsMinutes(){
        return Arrays.asList(8,12,20,50,80,120,160,90,75);
    }

    private List<WorkProcessTemplateDTO> cache;

    @Scheduled(cron = "*/30 * * * * ?")
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

    @Scheduled(cron = "*/60 * * * * ?")
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

    @Scheduled(cron = "*/600 * * * * ?")
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



}
