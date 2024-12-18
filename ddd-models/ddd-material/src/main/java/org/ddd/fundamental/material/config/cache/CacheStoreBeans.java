package org.ddd.fundamental.material.config.cache;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.redis.cache.CacheStore;
import org.ddd.fundamental.redis.cache.ICacheLoaderService;
import org.ddd.fundamental.redis.cache.LoadingCacheStore;
import org.ddd.fundamental.shared.api.material.MaterialDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@Slf4j
public class CacheStoreBeans {

    @Bean
    public CacheStore<MaterialDTO> materialCache() {
        return new CacheStore<>(120, TimeUnit.SECONDS);
    }

    @Bean
    public LoadingCacheStore<MaterialDTO> materialLoadingCache(ICacheLoaderService<MaterialDTO> materialQueryService) {
        return new LoadingCacheStore<>(120, TimeUnit.SECONDS, materialQueryService);
    }
}
