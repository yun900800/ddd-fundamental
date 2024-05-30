package org.ddd.fundamental.annotation;

import org.ddd.fundamental.ModelApp;
import org.ddd.fundamental.helper.ApplicationContextHelper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = ModelApp.class)
@RunWith(SpringRunner.class)
public class AnnotationTest {

    @Test
    public void testAnnotation(){
        Customer customer = ApplicationContextHelper.getBean(Customer.class);
        Assert.assertEquals(96.0,customer.getPurchasePowerScore(),0);
    }
}
