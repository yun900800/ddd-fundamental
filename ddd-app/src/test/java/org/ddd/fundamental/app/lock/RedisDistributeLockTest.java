package org.ddd.fundamental.app.lock;


import org.ddd.fundamental.app.AppApplicationTest;
import org.ddd.fundamental.share.infrastructure.JavaUuidGenerator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest(classes = AppApplicationTest.class)
@RunWith(SpringRunner.class)
public class RedisDistributeLockTest {

    private final  static Logger LOGGER = LoggerFactory.getLogger("RedisDistributeLockTest");

    @Autowired
    private RedisDistributeLock redisDistributeLock;

    private ExecutorService service = Executors.newFixedThreadPool(10);

    private AtomicInteger lockCount = new AtomicInteger();

    private AtomicInteger lockFailedCount = new AtomicInteger();

    @Test
    public void testDistributeLock() {
        String key = "productId";
        for (int i = 0 ; i< 10; i++) {
            String requestId = new UUID(100,1000).toString();
            boolean res = redisDistributeLock.lock(key,requestId,1);
            System.out.println("res:"+res);
            if (res) {
                lockCount.getAndIncrement();
                LOGGER.info("requestId:{} 获得了分布式锁",requestId);
            } else {
                lockFailedCount.getAndIncrement();
                LOGGER.info("requestId:{} 获取分布式锁失败",requestId);
            }
        }
        Assert.assertEquals(lockCount.doubleValue(),1.0,1);
        Assert.assertEquals(lockFailedCount.doubleValue(),9.0,1);
    }

    @Test
    public void testDistributeLockAndRelease() {
        String key = "productId";
        for (int i = 0 ; i< 10; i++) {
            String requestId = new UUID(100,1000).toString();
            boolean res = redisDistributeLock.lock(key,requestId,1);
            System.out.println("res:"+res);
            if (res) {
                lockCount.getAndIncrement();
                LOGGER.info("requestId:{} 获得了分布式锁",requestId);
                redisDistributeLock.unLock(key, requestId);
            } else {
                lockFailedCount.getAndIncrement();
                LOGGER.info("requestId:{} 获取分布式锁失败",requestId);
            }
        }
        Assert.assertEquals(lockCount.doubleValue(),10.0,1);
        Assert.assertEquals(lockFailedCount.doubleValue(),0.0,1);
    }
}
