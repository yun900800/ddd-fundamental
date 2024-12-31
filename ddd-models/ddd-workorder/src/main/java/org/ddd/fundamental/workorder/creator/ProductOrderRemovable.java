package org.ddd.fundamental.workorder.creator;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.creator.DataRemovable;
import org.ddd.fundamental.workorder.domain.repository.ProductOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class ProductOrderRemovable implements DataRemovable {

    private final ProductOrderRepository productOrderRepository;

    @Autowired(required = false)
    public ProductOrderRemovable(ProductOrderRepository productOrderRepository){
        this.productOrderRepository = productOrderRepository;
    }


    @Transactional
    @Override
    public void execute() {
        log.info("start remove productOrders");
        productOrderRepository.deleteAllProductOrders();
        log.info("finish remove productOrders");
    }
}
