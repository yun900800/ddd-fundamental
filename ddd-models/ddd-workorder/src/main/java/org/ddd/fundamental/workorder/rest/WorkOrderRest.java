package org.ddd.fundamental.workorder.rest;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.shared.api.workorder.ProductOrderDTO;
import org.ddd.fundamental.shared.api.workorder.ProductOrderRequest;
import org.ddd.fundamental.utils.EnumsUtils;
import org.ddd.fundamental.workorder.application.WorkOrderConverter;
import org.ddd.fundamental.workorder.application.command.WorkOrderCommandService;
import org.ddd.fundamental.workorder.application.query.WorkOrderQueryService;
import org.ddd.fundamental.workorder.domain.model.ProductOrder;
import org.ddd.fundamental.workorder.enums.OrderStatus;
import org.ddd.fundamental.workorder.value.OrderId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class WorkOrderRest {

    private final WorkOrderQueryService workOrderQueryService;

    private final WorkOrderCommandService workOrderCommandService;

    @Autowired(required = false)
    public WorkOrderRest(WorkOrderQueryService workOrderQueryService,
                         WorkOrderCommandService workOrderCommandService){
        this.workOrderCommandService = workOrderCommandService;
        this.workOrderQueryService = workOrderQueryService;
    }

    @RequestMapping("/work_order/product_order/{productName}/{pageNumber}/{pageSize}")
    public Page<ProductOrderDTO> findAllByProductName(@PathVariable String productName,
                                                      @PathVariable int pageNumber,
                                                      @PathVariable int pageSize){
        return this.workOrderQueryService.findAllByProductName(productName, pageNumber,pageSize);
    }

    @RequestMapping("/work_order/product_order/{pageNumber}/{pageSize}")
    public Page<ProductOrderDTO> fetchProductOrder(@PathVariable int pageNumber,
                                                   @PathVariable int pageSize){
        return this.workOrderQueryService.fetchProductOrder(pageNumber,pageSize);
    }

    @RequestMapping("/work_order/product_order_id/{pageNumber}/{pageSize}")
    public Page<ProductOrderDTO> fetchProductOrderByIds(@PathVariable int pageNumber,
                                                        @PathVariable int pageSize){
        return this.workOrderQueryService.fetchProductOrderByIds(pageNumber,pageSize);
    }

    @RequestMapping("/work_order/product_order_api/{pageNumber}/{pageSize}")
    public Page<ProductOrderDTO> fetchProductOrderByAPI(@PathVariable int pageNumber,
                                                        @PathVariable int pageSize){
        return this.workOrderQueryService.fetchProductOrderByAPI(pageNumber,pageSize);
    }

    @RequestMapping("/work_order/create_product_order")
    public void createProductOrder(@RequestBody ProductOrderRequest request){
        this.workOrderCommandService.createProductOrder(
                request.getMaterialDTO(),
                request.getSaleOrderId(),
                request.getProductCount(),
                request.getOrganization(),
                request.getDateTimeRange()
        );
    }

    @RequestMapping("/work_order/product_order/{orderId}")
    public ProductOrderDTO findByProductOrderId(@PathVariable String orderId){
        ProductOrder productOrder = workOrderQueryService.findProductOrderById(
                new OrderId(orderId)
        );
        return WorkOrderConverter.entityToDTO(productOrder);
    }

    @PostMapping("/work_order/change_product_order_status/{orderId}/{status}")
    public void changeProductOrderStatus(@PathVariable String orderId,
                                         @PathVariable String status){
        OrderStatus orderStatus = EnumsUtils.findEnumValue(OrderStatus.class,status);
        this.workOrderCommandService.changeProductOrderStatus(new OrderId(orderId),
                orderStatus);
    }

    @PostMapping("/work_order/change_product_order/{orderId}")
    public void changeProductOrder(@PathVariable String orderId,
                                   @RequestBody ProductOrderDTO productOrderDTO){
        this.workOrderCommandService.changeProductOrder(new OrderId(orderId),productOrderDTO);
    }
}
