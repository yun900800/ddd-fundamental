package org.ddd.fundamental.material.client;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MaterialFactoryClientFallback implements FallbackFactory<MaterialClient> {
    @Override
    public MaterialClient create(Throwable throwable) {
        return new MaterialClientFallback(throwable);
    }
}
