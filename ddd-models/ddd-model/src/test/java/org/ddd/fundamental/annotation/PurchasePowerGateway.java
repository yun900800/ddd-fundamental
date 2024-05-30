package org.ddd.fundamental.annotation;

import org.springframework.stereotype.Component;

@Component
public class PurchasePowerGateway {

    public Long getScore(){
        return 96L;
    }
}
