package org.ddd.fundamental.bom.schedule;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.bom.application.query.BomQueryService;
import org.ddd.fundamental.bom.value.MaterialIdNode;
import org.ddd.fundamental.bom.value.ProductStructureNode;
import org.ddd.fundamental.material.MaterialMaster;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.material.value.MaterialType;
import org.ddd.fundamental.shared.api.bom.BomIdDTO;
import org.ddd.fundamental.shared.api.material.MaterialDTO;
import org.ddd.fundamental.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
public class BomTemplateClient {

    private final BomQueryService bomQueryService;

    private static final String CREATE_PRODUCT_BOM = "http://localhost:9007/bom/create_product_bom/%s/%s/%s";

    private static final String ADD_NODE_TO_BOM = "http://localhost:9007//bom/add_node_to_bom/%s";

    @Autowired
    public BomTemplateClient(BomQueryService bomQueryService){
        this.bomQueryService = bomQueryService;
    }

    @Scheduled(cron = "*/36000 * * * * ?")
    public void createProductBom(){
        List<MaterialDTO> products = bomQueryService.materialsByMaterialType(MaterialType.PRODUCTION);
        MaterialId productId = CollectionUtils.random(products).id();
        String url = String.format(CREATE_PRODUCT_BOM,productId.toUUID(),6,10);
        log.info("url is {}",url);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(url, null,Void.class);
        log.info("create product bom finished");
    }

    @Scheduled(cron = "*/72000 * * * * ?")
    public void addNodeToBom(){
        List<BomIdDTO> products = bomQueryService.allBomIds();
        MaterialId productId = CollectionUtils.random(products).getProductId();
        String url = String.format(ADD_NODE_TO_BOM,productId.toUUID());
        MaterialIdNode<ProductStructureNode>  node = MaterialIdNode.create(
                MaterialId.randomId(MaterialId.class),
                null,
                productId,
                ProductStructureNode.create(MaterialMaster.create(
                    "测试编码",
                        "测试名称",
                        "spec",
                        "个"
                ),1, MaterialType.WORKING_IN_PROGRESS)
        );
        log.info("url is {}",url);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(url, node,Void.class);
        log.info("add node to bom finished");
    }
}
