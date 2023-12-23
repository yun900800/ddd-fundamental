package org.ddd.fundamental.base.loader;

import java.util.*;

public final class SpiFactory {
    private static Map<Class<?>, Object> cache = new HashMap<>();
    private static Map<Class<?>, List<Object>> cacheList = new HashMap<>();

    public static <T> T get(Class<T> c) {
        return get(c, null);
    }

    public static <T> List<T> getList(Class<T> c) {
        if (cacheList.get(c) != null) {
            return (List<T>) cacheList.get(c);
        }
        synchronized (SpiFactory.class) {
            if (cacheList.get(c) != null) {
                return (List<T>) cacheList.get(c);
            }

            ServiceLoader<T> load = ServiceLoader.load(c);
            List<Object> list = new ArrayList<>();
            cacheList.put(c, list);
            for (T obj : load) {
                list.add(obj);
            }
            return (List<T>) list;
        }
    }

    //如果没有加载的类会使用默认的类d
    public static <T> T get(Class<T> c, T d) {
        if (cache.get(c) != null) {
            return (T) cache.get(c);
        }
        synchronized (SpiFactory.class) {
            if (cache.get(c) != null) {
                return (T) cache.get(c);
            }

            ServiceLoader<T> load = ServiceLoader.load(c);
            for (T obj : load) {
                cache.put(c, obj);
                return obj;
            }
            return d;
        }
    }
}
