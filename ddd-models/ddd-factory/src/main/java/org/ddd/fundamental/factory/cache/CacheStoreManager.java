package org.ddd.fundamental.factory.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.core.AbstractDTO;
import org.ddd.fundamental.core.DomainObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class CacheStoreManager {

    @Autowired
    @Qualifier(value = "newRedisTemplate")
    private RedisTemplate<String,Object> newRedisTemplate;

    @Autowired
    private ObjectMapper mapper;

    /**
     * 存储数据到缓存中
     * @param dataList
     * @param <T>
     * @param <ID>
     */
    public <T extends AbstractDTO<ID>,ID extends DomainObjectId> void storeDataToCache(List<T> dataList){
        for (T data: dataList) {
            newRedisTemplate.opsForValue().set(data.id().toUUID(),data,60*10, TimeUnit.SECONDS);
        }
    }

    public <T extends AbstractDTO<ID>,ID extends DomainObjectId> T fetchDataFromCache(ID id, Class<T> clazz){
        Object object = newRedisTemplate.opsForValue().get(id.toUUID());
        return mapper.convertValue(object,clazz);
    }
}
