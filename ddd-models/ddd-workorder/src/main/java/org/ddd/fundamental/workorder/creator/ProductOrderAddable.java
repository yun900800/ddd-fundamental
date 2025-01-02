package org.ddd.fundamental.workorder.creator;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.creator.DataAddable;
import org.ddd.fundamental.material.client.MaterialClient;
import org.ddd.fundamental.material.value.MaterialType;
import org.ddd.fundamental.shared.api.material.MaterialDTO;
import org.ddd.fundamental.workorder.application.command.WorkOrderCommandService;
import org.ddd.fundamental.workorder.domain.model.ProductOrder;
import org.ddd.fundamental.workorder.helper.ProductOrderHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class ProductOrderAddable implements DataAddable {

    private final WorkOrderCommandService commandService;

    private final MaterialClient materialClient;

    private List<ProductOrder> productOrders;

    @Autowired(required = false)
    public ProductOrderAddable(WorkOrderCommandService commandService,
                               MaterialClient materialClient){
        this.commandService = commandService;
        this.materialClient = materialClient;
    }

    public List<ProductOrder> getProductOrders() {
        return new ArrayList<>(productOrders);
    }

    private List<MaterialDTO> products(){
        return materialClient.materialsByMaterialType(MaterialType.PRODUCTION);
    }

    @Override
    @Transactional
    public void execute() {
        log.info("start create productOrders");
        this.productOrders = ProductOrderHelper.createProductOrders(products());
        this.commandService.batchInsertProductOrder(productOrders);
        log.info("finish create productOrders");
    }
}
