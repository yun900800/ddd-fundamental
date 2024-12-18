package org.ddd.fundamental.redis.cache;


import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class CacheStore<T> {

    private Cache<String,T> cache;

    public CacheStore(int expiryDuration, TimeUnit timeUnit){
        this.cache = CacheBuilder.newBuilder()
                .expireAfterWrite(expiryDuration,timeUnit)
                .concurrencyLevel(Runtime.getRuntime().availableProcessors())
                .build();
    }

    public T get(String key){
        return cache.getIfPresent(key);
    }

    public void add(String key,T value){
        if(key != null && value != null) {
            cache.put(key, value);
            log.info("Record stored in {} Cache with Key = {}"
                    ,value.getClass().getSimpleName()
                    ,key);
        }
    }
}
