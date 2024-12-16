package org.ddd.fundamental.equipment.client;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EquipmentFactoryClientFallback implements FallbackFactory<EquipmentClient> {
    @Override
    public EquipmentClient create(Throwable throwable) {
        return new EquipmentClientFallback(throwable);
    }
}
