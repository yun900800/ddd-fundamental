package org.ddd.fundamental.redis.config;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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

    @Test
    public void testInsertString() {
        Jedis jedis = jedisTemplate.getJedis();
        String result = jedis.set("key1","10");
        Assert.assertEquals("OK",result);
    }

    @Test
    public void testInsertStringWithParams() {
        Jedis jedis = jedisTemplate.getJedis();
        SetParams setParams = SetParams.setParams();
        setParams.px(200);
        setParams.nx();
        String result = jedis.set("key2","11",setParams);
        Assert.assertEquals("OK",result);
        jedis.incrBy("key2",5);
        result = jedis.get("key2");
        Assert.assertEquals("16",result);
        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        result = jedis.get("key2");
        Assert.assertNull(result);
    }

    @Test
    public void testInsertHash() {
        Jedis jedis = jedisTemplate.getJedis();
        Long result = jedis.hset("key3","name","hekai");
        Assert.assertEquals(1, result,0);
        result = jedis.hset("key3","age","18");
        Assert.assertEquals(1, result,0);
        jedis.hincrBy("key3","age",3);
        Map<String,String> map = jedis.hgetAll("key3");
        Assert.assertEquals(2,map.size(),0);
        Assert.assertEquals("hekai", map.get("name"));
        Assert.assertEquals("21", map.get("age"));
        jedis.del("key3");
    }

    @Test
    public void testInsertHashMultiple() {
        Jedis jedis = jedisTemplate.getJedis();
        Map<String,String> params = new HashMap<>();
        params.put("name","yun900900");
        params.put("age","18");
        long result = jedis.hset("key5",params);
        Assert.assertEquals(2, result,0);
        jedis.del("key5");
    }

    @Test
    public void testInsertLists() {
        Jedis jedis = jedisTemplate.getJedis();
        jedis.lpush("keyList",new String[]{"-1","a"});
        jedis.lpush("keyList","b");
        jedis.lpush("keyList","c");
        jedis.lpush("keyList","d");
        String res = jedis.rpop("keyList");
        Assert.assertEquals("-1",res);
        res = jedis.rpop("keyList");
        Assert.assertEquals("a",res);
        jedis.del("keyList");
    }

    @Test
    public void testInsertSet() {
        Jedis jedis = jedisTemplate.getJedis();
        Long result = jedis.sadd("key4",new String[]{"yun900800","yun900900"});
        Assert.assertEquals(2, result,0);
        Set<String> values = jedis.smembers("key4");
        Assert.assertEquals(2, values.size());
        boolean exists = jedis.sismember("key4","yun900800");
        Assert.assertTrue(exists);
        jedis.del("key4");
    }

}
