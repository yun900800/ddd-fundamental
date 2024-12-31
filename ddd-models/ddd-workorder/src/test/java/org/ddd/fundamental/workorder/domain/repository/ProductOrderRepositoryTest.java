package org.ddd.fundamental.workorder.domain.repository;

import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.workorder.WorkOrderAppTest;
import org.ddd.fundamental.workorder.domain.model.ProductOrder;
import org.ddd.fundamental.workorder.value.OrderId;
import org.ddd.fundamental.workorder.value.ProductOrderValue;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.LocalDate;

public class ProductOrderRepositoryTest extends WorkOrderAppTest {

    @Autowired
    private ProductOrderRepository productOrderRepository;

    private static ProductOrder create(){
        ProductOrder productOrder =
                ProductOrder.create(
                        new ProductOrderValue.Builder(
                                MaterialId.randomId(MaterialId.class),
                                12000,
                                LocalDate.now().plusDays(20)
                        ).withProductCode("testCode")
                                .withProductName("测试产品名称")
                                .withSaleOrderId(OrderId.randomId(OrderId.class))
                                .withOrganization("班组三组")
                                .withPlanStartTime(Instant.now().plusSeconds(3600*24))
                                .withPlanFinishTime(Instant.now().plusSeconds(3600*24 *18))
                                .build()
                );
        return productOrder;
    }

    @Test
    public void testCreateProductOrder(){
        productOrderRepository.persist(create());
    }
}
