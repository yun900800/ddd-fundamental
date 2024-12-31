package org.ddd.fundamental.workorder.domain.model;

import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.workorder.value.OrderId;
import org.ddd.fundamental.workorder.value.ProductOrderValue;
import org.junit.Assert;
import org.junit.Test;

import java.time.Instant;
import java.time.LocalDate;

public class ProductOrderTest {

    @Test
    public void testCreateProductOrder(){
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
        Assert.assertEquals(productOrder.getProductOrder().getQty(),12000.0,0.0);
        Assert.assertEquals(productOrder.getProductOrder().getProductCode(),"testCode");
        Assert.assertEquals(productOrder.getProductOrder().getProductName(),"测试产品名称");
        Assert.assertEquals(productOrder.getProductOrder().getOrganization(),"班组三组");
    }
}
