package org.ddd.fundamental.redis.cache;

public interface ICacheLoaderService<T> {
    T getBackendData(String key);
}
