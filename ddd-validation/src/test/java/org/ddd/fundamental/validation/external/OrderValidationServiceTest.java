package org.ddd.fundamental.validation.external;

import org.junit.Test;

public class OrderValidationServiceTest {

    @Test
    public void testOrderValidationServiceValidate() {
        OrderValidationContext context = new OrderValidationContext("alice");
        OrderValidationService service = new OrderValidationService();
        service.validate(context);
    }
}
