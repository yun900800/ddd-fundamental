package org.ddd.fundamental.validation.base;

import org.junit.Test;

public class OrderValidationTest {

    @Test
    public void testOrderValidation() {
        Contact contact = new Contact();
        Order order = new Order("airplane",null);
        ParameterValidationResult result = order.validate();
        System.out.println(result);
    }
}
