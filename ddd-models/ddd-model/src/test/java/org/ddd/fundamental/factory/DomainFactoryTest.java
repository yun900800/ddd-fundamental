package org.ddd.fundamental.factory;

import org.ddd.fundamental.ModelApp;
import org.ddd.fundamental.annotation.Customer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = ModelApp.class)
@RunWith(SpringRunner.class)
public class DomainFactoryTest {

    @Test
    public void testDomainFactoryCreateDomain() {
        Customer customer = DomainFactory.create(Customer.class);
        Assert.assertEquals(96.0,customer.getPurchasePowerScore(),0);
    }

}
