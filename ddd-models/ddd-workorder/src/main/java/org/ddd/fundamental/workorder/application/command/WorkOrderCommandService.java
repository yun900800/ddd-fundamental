package org.ddd.fundamental.workorder.application.command;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.day.range.DateTimeRange;
import org.ddd.fundamental.material.MaterialMaster;
import org.ddd.fundamental.shared.api.material.MaterialDTO;
import org.ddd.fundamental.utils.CollectionUtils;
import org.ddd.fundamental.workorder.application.query.WorkOrderQueryService;
import org.ddd.fundamental.workorder.domain.model.ProductOrder;
import org.ddd.fundamental.workorder.domain.repository.ProductOrderRepository;
import org.ddd.fundamental.workorder.domain.repository.WorkOrderRepository;
import org.ddd.fundamental.workorder.value.OrderId;
import org.ddd.fundamental.workorder.value.ProductOrderValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Slf4j
@Transactional
public class WorkOrderCommandService {


    private final ProductOrderRepository productOrderRepository;

    private final WorkOrderRepository workOrderRepository;

    private final WorkOrderQueryService queryService;

    @Autowired(required = false)
    public WorkOrderCommandService(ProductOrderRepository productOrderRepository,
                                 WorkOrderRepository workOrderRepository,
                                   WorkOrderQueryService queryService){
        this.productOrderRepository = productOrderRepository;
        this.workOrderRepository = workOrderRepository;
        this.queryService = queryService;
    }

    public void createProductOrder(MaterialDTO materialDTO, OrderId saleOrderId,double productCount,
                                   String organization, DateTimeRange dateTimeRange){
        ProductOrder productOrder =
                ProductOrder.create(
                        new ProductOrderValue.Builder(
                                materialDTO.id(),
                                productCount,
                                LocalDate.now().plusDays(dateTimeRange.days() + 2)
                        ).withProductCode(materialDTO.getMaterialMaster().getCode())
                                .withProductName(materialDTO.getMaterialMaster().getName())
                                .withSaleOrderId(saleOrderId)
                                .withOrganization(organization)
                                .withPlanStartTime(dateTimeRange.getStart())
                                .withPlanFinishTime(dateTimeRange.getEnd())
                                .build()
                );
        this.productOrderRepository.persist(productOrder);
    }
}
