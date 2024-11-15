package org.ddd.fundamental.workprocess.client;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class MaterialFactoryClientFallback implements FallbackFactory<MaterialClient> {
    @Override
    public MaterialClient create(Throwable throwable) {
        return new MaterialClientFallback(throwable);
    }
}
