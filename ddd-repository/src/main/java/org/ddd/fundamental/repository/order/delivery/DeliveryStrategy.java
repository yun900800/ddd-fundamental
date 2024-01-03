package org.ddd.fundamental.repository.order.delivery;

import java.math.BigDecimal;

public interface DeliveryStrategy {

    BigDecimal generateDeliveryFee(BigDecimal orderAmount);

}
