package org.ddd.fundamental.repository.order;

import org.ddd.fundamental.repository.Exception.ProductInfoNotExistException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ProductInfoTest {

    @Test
    public void testLoadProductInfo() {
        ProductInfo.loadProductInfo();
        Assert.assertEquals(4,ProductInfo.products().size());
    }

    @Test
    public void testGetProductId(){
        ProductInfo.loadProductInfo();
        Assert.assertEquals("香蕉",ProductInfo.getProductId(1L).getProductName());
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testGetProductIdNotExist(){
        ProductInfo.loadProductInfo();
        expectedException.expect(ProductInfoNotExistException.class);
        expectedException.expectMessage("产品信息id:10对应的产品不存在");
        ProductInfo.getProductId(10L);
    }

}
