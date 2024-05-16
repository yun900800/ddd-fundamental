package org.ddd.fundamental.design.chain;

import org.ddd.fundamental.design.model.ProductVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductServiceTest {
    @Resource
    private ProductService service;

    @Test
    public void contextLoad() {
        ProductVO param = ProductVO.builder()
                .skuId(123L)
                .skuName("测试商品")
                .imgPath("http://..")
                .price(new BigDecimal(1))
                .stock(1)
                .build();
        service.createProduct(param);
    }


}
