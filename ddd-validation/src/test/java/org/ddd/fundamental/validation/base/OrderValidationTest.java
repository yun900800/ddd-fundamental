package org.ddd.fundamental.validation.base;

import org.junit.Assert;
import org.junit.Test;

public class OrderValidationTest {

    @Test
    public void testOrderFailedValidation() {
        Contact contact = new Contact();
        Order order = new Order("airplane",null);
        ParameterValidationResult result = order.validate();
        Assert.assertEquals(result.isSuccess(), false);
        Assert.assertEquals(((CompositeParameterValidateResult)result).getResultList().size(),3);
    }

    @Test
    public void testOrderSuccessValidation() {
        Contact contact = new Contact();
        Order order = new Order("airplane",contact,80);
        ParameterValidationResult result = order.validate();
        Assert.assertEquals(result.isSuccess(), true);
        Assert.assertEquals(((CompositeParameterValidateResult)result).getResultList().size(),0);
    }

    @Test
    public void testOrderAndValidation() {
        Contact contact = new Contact();
        Order order = new Order("airplane",contact,60);
        ParameterValidationResult result = order.validate();
        Assert.assertEquals(result.isSuccess(), true);
        Assert.assertEquals(((CompositeParameterValidateResult)result).getResultList().size(),0);
    }
}
