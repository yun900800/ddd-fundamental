package org.ddd.fundamental.workorder.application.command;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.day.range.DateTimeRange;
import org.ddd.fundamental.shared.api.material.MaterialDTO;
import org.ddd.fundamental.shared.api.workorder.ProductOrderDTO;
import org.ddd.fundamental.workorder.application.mapper.ProductOrderMapper;
import org.ddd.fundamental.workorder.application.query.WorkOrderQueryService;
import org.ddd.fundamental.workorder.domain.model.ProductOrder;
import org.ddd.fundamental.workorder.domain.repository.ProductOrderRepository;
import org.ddd.fundamental.workorder.domain.repository.WorkOrderRepository;
import org.ddd.fundamental.workorder.enums.OrderStatus;
import org.ddd.fundamental.workorder.enums.Urgency;
import org.ddd.fundamental.workorder.value.OrderId;
import org.ddd.fundamental.workorder.value.ProductOrderValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@Transactional
public class WorkOrderCommandService {


    private final ProductOrderRepository productOrderRepository;

    private final WorkOrderRepository workOrderRepository;

    private final WorkOrderQueryService queryService;

    private final ProductOrderMapper mapper;

    @Autowired(required = false)
    public WorkOrderCommandService(ProductOrderRepository productOrderRepository,
                                 WorkOrderRepository workOrderRepository,
                                   WorkOrderQueryService queryService,
                                   ProductOrderMapper mapper){
        this.productOrderRepository = productOrderRepository;
        this.workOrderRepository = workOrderRepository;
        this.queryService = queryService;
        this.mapper = mapper;
    }

    /**
     * 创建生产订单信息
     * @param materialDTO
     * @param saleOrderId
     * @param productCount
     * @param organization
     * @param dateTimeRange
     */
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
        insertProductOrder(productOrder);
    }

    public void insertProductOrder(ProductOrder productOrder){
        this.productOrderRepository.persist(productOrder);
    }

    public void batchInsertProductOrder(List<ProductOrder> productOrders){
        this.productOrderRepository.persistAll(productOrders);
    }

    public void updateProductOrder(ProductOrder productOrder){
        this.productOrderRepository.merge(productOrder);
    }



    /**
     * 修改山产订单状态
     * @param orderId
     * @param status
     */
    public void changeProductOrderStatus(OrderId orderId, OrderStatus status){
        ProductOrder productOrder = queryService.findProductOrderById(orderId);
        productOrder.changeStatus(status);
    }

    /**
     * 修改紧急等级
     * @param orderId
     * @param urgency
     */
    public void changeUrgency(OrderId orderId, Urgency urgency){
        ProductOrder productOrder = queryService.findProductOrderById(orderId);
        productOrder.changeUrgency(urgency);
    }

    /**
     * 配置生产组织
     * @param orderId
     * @param organization
     */
    public void configOrganization(OrderId orderId, String organization){
        ProductOrder productOrder = queryService.findProductOrderById(orderId);
        productOrder.configOrganization(organization);
    }

    /**
     * 配置销售订单
     * @param orderId
     * @param saleOrderId
     */
    public void configSaleOrderId(OrderId orderId,OrderId saleOrderId){
        ProductOrder productOrder = queryService.findProductOrderById(orderId);
        productOrder.configSaleOrderId(saleOrderId);
    }

    /**
     * 修改计划开始时间
     * @param orderId
     * @param planStartTime
     */
    public void changePlanStartTime(OrderId orderId, Instant planStartTime){
        ProductOrder productOrder = queryService.findProductOrderById(orderId);
        productOrder.changePlanStartTime(planStartTime);
    }

    /**
     * 修改计划开始时间
     * @param orderId
     * @param planFinishTime
     */
    public void changePlanFinishTime(OrderId orderId, Instant planFinishTime){
        ProductOrder productOrder = queryService.findProductOrderById(orderId);
        productOrder.changePlanFinishTime(planFinishTime);
    }

    /**
     * 更新生产订单
     * @param productOrderDTO
     */
    public void changeProductOrder(OrderId orderId,ProductOrderDTO productOrderDTO){
        orderId = productOrderDTO.id();
        ProductOrder productOrder = queryService.findProductOrderById(orderId);
        productOrder.changeProductOrder(productOrderDTO.getProductOrder());
    }
}
