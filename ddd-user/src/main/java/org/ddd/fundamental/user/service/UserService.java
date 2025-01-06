package org.ddd.fundamental.user.service;

import brave.Span;
import brave.Tracer;
import org.ddd.fundamental.redis.config.JedisTemplate;
import org.ddd.fundamental.user.repository.redis.CardShoppingRepository;
import org.ddd.fundamental.user.repository.redis.UserRepository;
import org.ddd.fundamental.user.repository.redis.model.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.Executor;

@Service
public class UserService {

    @Autowired
    private Tracer tracer;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CardShoppingRepository shoppingRepository;

    @Autowired
    private JedisTemplate jedisTemplate;

    @Autowired
    private Executor executor;

    public void addUserCount(String userId, String port, boolean success) {
        String key = userId + "_" +port;
        Optional<UserModel> optionalUserModel = userRepository.findById(key);
        UserModel userModel = null;
        if (optionalUserModel.isPresent()) {
            userModel = optionalUserModel.get();
            userModel.increment();
        } else {
            userModel = new UserModel(key,1);
        }
        if (success) {
            userModel.successIncrement();
        } else {
            userModel.failedIncrement();
        }
        userRepository.save(userModel);
    }

    public void addItemByUserId(String userId,String name,Integer count) {
        HashOperations<String,String,String> hashOperations = redisTemplate.opsForHash();
        if (hashOperations.hasKey(userId,name)) {
            hashOperations.increment(userId, name,count);
        } else {
            hashOperations.put(userId, name,count.toString());
        }
        Integer generateData = new Random().nextInt(100);
        LOGGER.info("use redisTemplate store data {}",generateData);
        String key = userId+ "jedis";
        redisTemplate.opsForValue().set(key,generateData.toString());
        Jedis jedis = jedisTemplate.getJedis();
        String data = jedis.get(key);
        LOGGER.info("use jedisTemplate fetch data {}",data);
    }

    public void doSomeWorkSameSpan() throws InterruptedException {
        Thread.sleep(10L);
        LOGGER.info("Doing some work");
    }

    public void doSomeWorkNewSpan() throws InterruptedException {
        LOGGER.info("这个日志在原始的旧的span中");

        Span newSpan = tracer.nextSpan().name("newSpan").start();
        try (Tracer.SpanInScope ws = tracer.withSpanInScope(newSpan.start())) {
            Thread.sleep(10L);
            LOGGER.info("这个日志记录在新的span中,记住，它一定在finish结束之前");
        } finally {
            newSpan.finish();
        }

        LOGGER.info("这个日志依旧在原始的旧的span中,因为finish方法已经结束啦");
    }

    public void threadSpanTest() {
        LOGGER.info("在线程外部的追踪信息是: new thread");
        executor.execute(()->{
            LOGGER.info("在线程内部的追踪信息是: new thread run");
        });
        LOGGER.info("在线程外部的追踪信息是: new thread finished");
    }
}
