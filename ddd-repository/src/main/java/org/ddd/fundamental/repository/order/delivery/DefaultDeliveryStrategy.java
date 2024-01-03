package org.ddd.fundamental.repository.order.delivery;

import java.math.BigDecimal;

public class DefaultDeliveryStrategy implements DeliveryStrategy {
    @Override
    public BigDecimal generateDeliveryFee(BigDecimal orderAmount) {
        if (orderAmount.compareTo(BigDecimal.valueOf(25))> 0) {
            return BigDecimal.valueOf(5);
        } else if (orderAmount.compareTo(BigDecimal.valueOf(60)) > 0){
            return BigDecimal.valueOf(2);
        } else if (orderAmount.compareTo(BigDecimal.valueOf(100)) > 0){
            return BigDecimal.ZERO;
        }
        return BigDecimal.TEN;
    }
}
