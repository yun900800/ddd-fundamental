package org.ddd.fundamental.redis.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.core.AbstractDTO;
import org.ddd.fundamental.core.DomainObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
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
        for (T data: dataList) {
            newRedisTemplate.opsForValue().set(data.id().toUUID(),data,60*10, TimeUnit.SECONDS);
        }
    }

    /**
     * 存储批量数据到缓存中
     * @param dataCollection
     * @param <T>
     * @param <ID>
     */
    public <T extends AbstractDTO<ID>,ID extends DomainObjectId> void storeDataCollectionToCache(Collection<T> dataCollection){
        for (T data: dataCollection) {
            newRedisTemplate.opsForValue().set(data.id().toUUID(),data,60*10, TimeUnit.SECONDS);
        }
    }


    /**
     * 存储单个数据到缓存
     * @param data
     * @param <T>
     * @param <ID>
     */
    public <T extends AbstractDTO<ID>,ID extends DomainObjectId> void storeDataToCache(T data){
        newRedisTemplate.opsForValue().set(data.id().toUUID(),data,60*10, TimeUnit.SECONDS);
    }

    public <T extends AbstractDTO<ID>,ID extends DomainObjectId> T fetchDataFromCache(ID id, Class<T> clazz){
        Object object = newRedisTemplate.opsForValue().get(id.toUUID());
        return mapper.convertValue(object,clazz);
    }

    public <T extends AbstractDTO<ID>,ID extends DomainObjectId> List<T> fetchDataListFromCache(List<ID> ids,Class<T> clazz){
        List<String> stringIds = ids.stream().map(v->v.toUUID()).collect(Collectors.toList());
        List<Object> list = newRedisTemplate.opsForValue().multiGet(stringIds);
        List<T> result = new ArrayList<>();
        for (Object obj: list) {
            T data = mapper.convertValue(obj,clazz);
            result.add(data);
        }
        return result;
    }
}
