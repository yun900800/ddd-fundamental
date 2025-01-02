package org.ddd.fundamental.workorder.schedule;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.material.client.MaterialClient;
import org.ddd.fundamental.material.value.MaterialType;
import org.ddd.fundamental.shared.api.material.MaterialDTO;
import org.ddd.fundamental.shared.api.workorder.ProductOrderRequest;
import org.ddd.fundamental.utils.CollectionUtils;
import org.ddd.fundamental.workorder.application.query.WorkOrderQueryService;
import org.ddd.fundamental.workorder.creator.ProductOrderAddable;
import org.ddd.fundamental.workorder.helper.ProductOrderHelper;
import org.ddd.fundamental.workorder.value.OrderId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
public class WorkOrderTemplateClient {

    private final MaterialClient materialClient;

    private final ProductOrderAddable addable;

    private final WorkOrderQueryService queryService;

    private static final String CREATE_PRODUCT_ORDER = "http://localhost:9005/work_order/create_product_order";
    private static final String CHANGE_PRODUCT_ORDER_STATUS = "http://localhost:9005/work_order/change_product_order_status/%s/%s";

    @Autowired(required = false)
    public WorkOrderTemplateClient(MaterialClient materialClient,
                                   WorkOrderQueryService queryService,
                                   ProductOrderAddable addable){
        this.materialClient = materialClient;
        this.addable = addable;
        this.queryService = queryService;
    }

    @Scheduled(cron = "*/20 * * * * ?")
    public void createProductOrder(){
        List<MaterialDTO> products = materialClient.materialsByMaterialType(MaterialType.PRODUCTION);
        MaterialDTO materialDTO = CollectionUtils.random(products);
        ProductOrderRequest request = ProductOrderRequest.create(
                materialDTO,
                OrderId.randomId(OrderId.class),
                CollectionUtils.random(ProductOrderHelper.productCount()),
                "测试工作组",
                CollectionUtils.random(ProductOrderHelper.createDateTimeRanges())
        );
        log.info("url is {}",CREATE_PRODUCT_ORDER);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(CREATE_PRODUCT_ORDER,request,Void.class);
        log.info("create productOrder finished");
    }

    @Scheduled(cron = "*/25 * * * * ?")
    public void changeProductOrderStatus(){
        OrderId orderId = CollectionUtils.random(queryService.productOrders()).id();
        String url = String.format(CHANGE_PRODUCT_ORDER_STATUS,orderId.toUUID(),"GENERATE_WORK_ORDER");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(url,null,Void.class);
        log.info("change productOrder status finished");
    }
}
