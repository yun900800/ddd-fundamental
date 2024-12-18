package org.ddd.fundamental.material.application.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Slf4j
public class LoadingCacheStore<T> {

    private LoadingCache<String, T> loadingCache;

    public LoadingCacheStore(int expiryDuration,
                             TimeUnit timeUnit, ICacheLoaderService<T> service){
        CacheLoader<String, T> loader = new CacheLoader<>(){

            @Override
            public T load(String key) throws Exception {
                log.info("Record is not present in LoadingCache {}... Fetching from backend api..."
                        ,service.getClass().getSimpleName());
                return service.getBackendData(key);
            }
        };
        loadingCache = CacheBuilder.newBuilder()
                .expireAfterWrite(expiryDuration, timeUnit)
                .concurrencyLevel(Runtime.getRuntime().availableProcessors())
                .build(loader);
    }

    public T get(String key){
        try {
            return loadingCache.get(key);
        } catch (ExecutionException e){
            log.error("fetch key:{} data from local cache error ",key);
        }
        return null;
    }
}
