package org.ddd.fundamental.workorder.creator;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.core.generator.Generators;
import org.ddd.fundamental.creator.DataAddable;
import org.ddd.fundamental.event.core.DomainEventType;
import org.ddd.fundamental.event.workorder.WorkOrderEvent;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.shared.api.optemplate.CraftsmanShipTemplateDTO;
import org.ddd.fundamental.tuple.Tuple;
import org.ddd.fundamental.tuple.TwoTuple;
import org.ddd.fundamental.utils.CollectionUtils;
import org.ddd.fundamental.workorder.domain.model.WorkOrder;
import org.ddd.fundamental.workorder.domain.repository.WorkOrderRepository;
import org.ddd.fundamental.workorder.helper.ProductOrderHelper;
import org.ddd.fundamental.workorder.producer.WorkOrderProducer;
import org.ddd.fundamental.workorder.value.WorkOrderValue;
import org.ddd.fundamental.workprocess.client.WorkProcessClient;
import org.ddd.fundamental.workprocess.value.CraftsmanShipId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class WorkOrderAddable implements DataAddable {

    private final WorkOrderRepository workOrderRepository;

    private final WorkProcessClient workProcessClient;

    private final WorkOrderProducer producer;

    private List<WorkOrder> workOrderList;

    @Autowired(required = false)
    public WorkOrderAddable(WorkOrderRepository workOrderRepository,
                            WorkProcessClient workProcessClient,
                            WorkOrderProducer producer){
        this.workOrderRepository = workOrderRepository;
        this.workProcessClient = workProcessClient;
        this.producer = producer;
    }

    public List<TwoTuple<CraftsmanShipId, MaterialId>> craftsmanShipIds(){
        List<CraftsmanShipTemplateDTO> craftsmanShipTemplateDTOS = workProcessClient.craftsmanShipTemplates();
        List<TwoTuple<CraftsmanShipId,MaterialId>> twoTuples = new ArrayList<>();
        for (CraftsmanShipTemplateDTO craftsmanShipTemplateDTO: craftsmanShipTemplateDTOS) {
            twoTuples.add(Tuple.tuple(craftsmanShipTemplateDTO.id(),craftsmanShipTemplateDTO.getProductId()));
        }
        return twoTuples;
    }

    public WorkOrder createWorkOrder(){
        Instant start = Instant.now();
        Instant end = start.plusSeconds(3600*24* CollectionUtils.random(ProductOrderHelper.days()));
        TwoTuple<CraftsmanShipId,MaterialId> tuple = CollectionUtils.random(craftsmanShipIds());
        WorkOrder workOrder = new WorkOrder(WorkOrderValue.create(
                CollectionUtils.random(ProductOrderHelper.workOrderTypes()), tuple.second,
                start, end,CollectionUtils.random(ProductOrderHelper.doubles()), CollectionUtils.random(ProductOrderHelper.productCompanyNames()),
                tuple.first
        ));
        return workOrder;
    }

    public List<WorkOrder> createWorkOrders(){
        List<WorkOrder> workOrders = new ArrayList<>();
        Generators.fill(workOrders,()->createWorkOrder(),5);
        return workOrders;
    }

    private List<WorkOrderEvent> toEvents(List<WorkOrder> workOrders){
        return workOrders.stream().map(v->WorkOrderEvent.create(
                        DomainEventType.EQUIPMENT, v.getOrder(),v.id()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void execute() {
        log.info("start create workOrders");
        this.workOrderList = createWorkOrders();
        workOrderRepository.saveAll(workOrderList);
        producer.sendWorkOrdersByType(toEvents(workOrderList));
        log.info("finish create workOrders");
    }
}
