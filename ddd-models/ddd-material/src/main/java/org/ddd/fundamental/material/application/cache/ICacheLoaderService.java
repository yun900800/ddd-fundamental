package org.ddd.fundamental.material.application.cache;

public interface ICacheLoaderService<T> {
    T getBackendData(String key);
}
