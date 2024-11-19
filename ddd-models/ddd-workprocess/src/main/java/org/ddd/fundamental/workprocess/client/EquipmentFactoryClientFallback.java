package org.ddd.fundamental.workprocess.client;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class EquipmentFactoryClientFallback implements FallbackFactory<EquipmentClient> {
    @Override
    public EquipmentClient create(Throwable throwable) {
        return new EquipmentClientFallback(throwable);
    }
}
