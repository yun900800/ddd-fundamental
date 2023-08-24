package org.ddd.fundamental.redis.config;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import redis.clients.jedis.Jedis;

public class JedisTemplateTest {

    private JedisTemplate jedisTemplate;

    @Before
    public void init() {
        ApplicationContext context = new AnnotationConfigApplicationContext(CustomRedisConfiguration.class);
        this.jedisTemplate = (JedisTemplate)context.getBean("jedisTemplate");
        Assert.assertNotNull(jedisTemplate);
    }

    @Test
    public void testConnectionNotRelease() {
        //JedisPool默认最大连接池是8个,只要数值超过8,就无法获取连接，除非手动释放
        for (int i = 0 ; i< 9;i++) {
            jedisTemplate.getJedisOld();
        }
    }

    @Test
    public void testConnectionManualRelease() {
        for (int i = 0 ; i< 20;i++) {
            Jedis jedis = jedisTemplate.getJedisOld();
            jedisTemplate.close(jedis);
        }
        Assert.assertTrue(true);
    }

    @Test
    public void testConnectionAutoRelease() {
        for (int i = 0 ; i< 20;i++) {
            jedisTemplate.getJedis();
        }
        Assert.assertTrue(true);
    }
}
