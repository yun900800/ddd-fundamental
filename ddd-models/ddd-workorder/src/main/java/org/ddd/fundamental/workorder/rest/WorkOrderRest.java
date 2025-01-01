package org.ddd.fundamental.workorder.rest;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.shared.api.workorder.ProductOrderDTO;
import org.ddd.fundamental.shared.api.workorder.ProductOrderRequest;
import org.ddd.fundamental.workorder.application.command.WorkOrderCommandService;
import org.ddd.fundamental.workorder.application.query.WorkOrderQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
