package org.ddd.fundamental.workprocess.client;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WorkProcessFactoryClientFallback implements FallbackFactory<WorkProcessClient> {



    @Override
    public WorkProcessClient create(Throwable throwable) {
        return new WorkProcessClientFallback(throwable);
    }
}
