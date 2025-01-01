package org.ddd.fundamental.workorder.schedule;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.material.client.MaterialClient;
import org.ddd.fundamental.material.value.MaterialType;
import org.ddd.fundamental.shared.api.material.MaterialDTO;
import org.ddd.fundamental.shared.api.workorder.ProductOrderRequest;
import org.ddd.fundamental.utils.CollectionUtils;
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

    private static final String CREATE_PRODUCT_ORDER = "http://localhost:9005/work_order/create_product_order";

    @Autowired(required = false)
    public WorkOrderTemplateClient(MaterialClient materialClient){
        this.materialClient = materialClient;
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
}
