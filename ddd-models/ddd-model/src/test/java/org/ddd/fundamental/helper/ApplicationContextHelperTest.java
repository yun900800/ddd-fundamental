package org.ddd.fundamental.helper;

import org.ddd.fundamental.ModelApp;
import org.ddd.fundamental.design.chain.ProductService;
import org.ddd.fundamental.design.chain.impl.AbstractCheckHandler;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@SpringBootTest(classes = ModelApp.class)
@RunWith(SpringRunner.class)
public class ApplicationContextHelperTest {

    @Resource
    private Map<String, AbstractCheckHandler> handlerMap;

    @Test
    public void testApplicationContextIsNull() {
        Assert.assertNotNull(ApplicationContextHelper.getApplicationContext());
    }

    @Test
    public void testGetBean() {
        ProductService service = ApplicationContextHelper.getBean(ProductService.class);
        Assert.assertNotNull(service);
        Assert.assertEquals(handlerMap.size(),3,0);
    }
}
