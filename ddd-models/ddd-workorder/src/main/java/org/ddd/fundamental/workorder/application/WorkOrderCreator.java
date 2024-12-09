package org.ddd.fundamental.workorder.application;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.core.generator.Generators;
import org.ddd.fundamental.event.core.DomainEventType;
import org.ddd.fundamental.event.workorder.WorkOrderEvent;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.material.value.MaterialType;
import org.ddd.fundamental.shared.api.material.MaterialDTO;
import org.ddd.fundamental.shared.api.optemplate.CraftsmanShipTemplateDTO;
import org.ddd.fundamental.tuple.Tuple;
import org.ddd.fundamental.tuple.TwoTuple;
import org.ddd.fundamental.utils.CollectionUtils;
import org.ddd.fundamental.workorder.client.MaterialClient;
import org.ddd.fundamental.workorder.client.WorkProcessClient;
import org.ddd.fundamental.workorder.domain.model.WorkOrder;
import org.ddd.fundamental.workorder.domain.repository.WorkOrderRepository;
import org.ddd.fundamental.workorder.enums.WorkOrderType;
import org.ddd.fundamental.workorder.value.WorkOrderValue;
import org.ddd.fundamental.workprocess.value.CraftsmanShipId;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class WorkOrderCreator implements SmartInitializingSingleton {

    @Autowired
    private WorkProcessClient workProcessClient;

    private List<WorkOrder> workOrderList;

    @Autowired
    private WorkOrderRepository workOrderRepository;

    public static List<WorkOrderType> workOrderTypes() {
        return Arrays.asList(WorkOrderType.values());
    }

    public List<TwoTuple<CraftsmanShipId,MaterialId>> craftsmanShipIds(){
        List<CraftsmanShipTemplateDTO> craftsmanShipTemplateDTOS = workProcessClient.craftsmanShipTemplates();
        List<TwoTuple<CraftsmanShipId,MaterialId>> twoTuples = new ArrayList<>();
        for (CraftsmanShipTemplateDTO craftsmanShipTemplateDTO: craftsmanShipTemplateDTOS) {
            twoTuples.add(Tuple.tuple(craftsmanShipTemplateDTO.id(),craftsmanShipTemplateDTO.getProductId()));
        }
        return twoTuples;
    }

    public List<Integer> days() {
        return Arrays.asList(1,2,3,4,5,10,12,15);
    }

    public List<Double> doubles(){
        return Arrays.asList(1000.0,1200.0,1500.0,1250.0,1100.0);
    }

    public List<String> productCompanyNames() {
        return Arrays.asList("深圳市卓越科技有限公司","深圳市创新科技有限公司","深圳市梨子科技有限公司");
    }


    public WorkOrder createWorkOrder(){
        Instant start = Instant.now();
        Instant end = start.plusSeconds(3600*24*CollectionUtils.random(days()));
        TwoTuple<CraftsmanShipId,MaterialId> tuple = CollectionUtils.random(craftsmanShipIds());
        WorkOrder workOrder = new WorkOrder(WorkOrderValue.create(
                CollectionUtils.random(workOrderTypes()), tuple.second,
                start, end,CollectionUtils.random(doubles()), CollectionUtils.random(productCompanyNames()),
                tuple.first
        ));
        return workOrder;
    }

    public List<WorkOrder> createWorkOrders(){
        List<WorkOrder> workOrders = new ArrayList<>();
        Generators.fill(workOrders,()->createWorkOrder(),5);
        return workOrders;
    }

    @Autowired
    private WorkOrderProducer producer;

    private List<WorkOrderEvent> toEvents(List<WorkOrder> workOrders){
        return workOrders.stream().map(v->WorkOrderEvent.create(
                    DomainEventType.EQUIPMENT, v.getOrder(),v.id()
                ))
                .collect(Collectors.toList());
    }
    public void init(){
        log.info("start remove workOrders");
        workOrderRepository.deleteAll();
        this.workOrderList = createWorkOrders();
        workOrderRepository.saveAll(workOrderList);
        //producer.sendWorkOrders(toEvents(workOrderList));
        producer.sendWorkOrdersByType(toEvents(workOrderList));
        log.info("finish create workOrders");
    }

    @Override
    public void afterSingletonsInstantiated() {
        init();
    }
}
