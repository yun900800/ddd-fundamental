package org.ddd.fundamental.app.lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;

@Component
public class RedisDistributeLock {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    /**
     * 使用redis特性实现互斥锁（setnx）
     *
     * @param lockKey
     * @param requestId
     * @param expireTime 锁过期时间，即超过该时间仍然未被解锁，则自动解锁，防止死锁
     * @return
     */
    public boolean lock(String lockKey, String requestId, int expireTime) {
        Boolean res = redisTemplate.opsForValue().setIfAbsent(lockKey, requestId, Duration.ofSeconds(expireTime));
        return res != null && res;
    }

    /**
     * 释放锁，使用Lua脚本，确保原子性
     *
     * @param lockKey
     * @param requestId
     * @return
     */
    public boolean unLock(String lockKey, String requestId) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>(script, Boolean.class);
        Boolean res = redisTemplate.execute(redisScript, Collections.singletonList(lockKey), requestId);
        return res != null && res;
    }
}
