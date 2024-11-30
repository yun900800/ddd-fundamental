package org.ddd.fundamental.redis.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.core.AbstractDTO;
import org.ddd.fundamental.core.DomainObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
@Slf4j
public class RedisStoreManager {

    @Autowired
    @Qualifier(value = "newRedisTemplate")
    private RedisTemplate<String,Object> newRedisTemplate;

    @Autowired
    private ObjectMapper mapper;

    /**
     * 存储批量数据到缓存中
     * @param dataList
     * @param <T>
     * @param <ID>
     */
    public <T extends AbstractDTO<ID>,ID extends DomainObjectId> void storeDataListToCache(List<T> dataList){
        storeDataListToCache(dataList,"");
    }

    public <T extends AbstractDTO<ID>,ID extends DomainObjectId> void storeDataListToCache(List<T> dataList,String prefix){
        for (T data: dataList) {
            String key = generateStoreKey(prefix,data);
            newRedisTemplate.opsForValue().set(key,data,60*10, TimeUnit.SECONDS);
        }
    }

    private <T extends AbstractDTO<ID>, ID extends DomainObjectId> String generateStoreKey(String prefix,T data){
        if (!StringUtils.hasLength(prefix)) {
            prefix = data.getClass().getSimpleName();
        }
        return prefix + "_" + data.id().toUUID();
    }

    private <T extends AbstractDTO<ID>, ID extends DomainObjectId> String generateFetchKey(String prefix,Class<T> clazz,ID id){
        if (!StringUtils.hasLength(prefix)) {
            prefix = clazz.getSimpleName();
        }
        return prefix + "_" + id.toUUID();
    }


    /**
     * 存储批量数据到缓存中
     * @param dataCollection
     * @param <T>
     * @param <ID>
     */
    public <T extends AbstractDTO<ID>,ID extends DomainObjectId> void storeDataCollectionToCache(Collection<T> dataCollection){
        storeDataCollectionToCache(dataCollection,"");
    }

    public <T extends AbstractDTO<ID>,ID extends DomainObjectId> void storeDataCollectionToCache(Collection<T> dataCollection,String prefix){
        for (T data: dataCollection) {
            String key = generateStoreKey(prefix,data);
            newRedisTemplate.opsForValue().set(key,data,60*10, TimeUnit.SECONDS);
        }
    }


    /**
     * 存储单个数据到缓存
     * @param data
     * @param <T>
     * @param <ID>
     */
    public <T extends AbstractDTO<ID>,ID extends DomainObjectId> void storeDataToCache(T data){
        storeDataToCache(data,"");
    }

    public <T extends AbstractDTO<ID>,ID extends DomainObjectId> void storeDataToCache(T data,String prefix){
        String key = generateStoreKey(prefix,data);
        newRedisTemplate.opsForValue().set(key,data,60*10, TimeUnit.SECONDS);
    }

    public <T extends AbstractDTO<ID>,ID extends DomainObjectId> T fetchDataFromCache(ID id, Class<T> clazz){
        return fetchDataFromCache(id,clazz,"");
    }

    public <T extends AbstractDTO<ID>,ID extends DomainObjectId> T fetchDataFromCache(ID id, Class<T> clazz,String prefix){
        String key  = generateFetchKey(prefix, clazz,id);
        Object object = newRedisTemplate.opsForValue().get(key);
        return mapper.convertValue(object,clazz);
    }

    public <T extends AbstractDTO<ID>,ID extends DomainObjectId> List<T> fetchDataListFromCache(List<ID> ids,Class<T> clazz){
        return fetchDataListFromCache(ids,clazz,"");
    }
    public <T extends AbstractDTO<ID>,ID extends DomainObjectId> List<T> fetchDataListFromCache(List<ID> ids,Class<T> clazz,String prefix){
        List<String> stringIds = ids.stream().map(v->generateFetchKey(prefix, clazz,v)).collect(Collectors.toList());
        List<Object> list = newRedisTemplate.opsForValue().multiGet(stringIds);
        List<T> result = new ArrayList<>();
        for (Object obj: list) {
            T data = mapper.convertValue(obj,clazz);
            result.add(data);
        }
        return result;
    }
}
