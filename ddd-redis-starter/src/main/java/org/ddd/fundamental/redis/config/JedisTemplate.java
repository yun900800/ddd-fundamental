package org.ddd.fundamental.redis.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class JedisTemplate {

    private static final Logger LOGGER = LoggerFactory.getLogger(JedisTemplate.class);
    private JedisPool jedisPool;

    private JedisSentinelPool jedisSentinelPool;

    public JedisSentinelPool getJedisSentinelPool() {
        return jedisSentinelPool;
    }

    public void setJedisSentinelPool(JedisSentinelPool jedisSentinelPool) {
        this.jedisSentinelPool = jedisSentinelPool;
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public Jedis getJedis() {
        if (jedisSentinelPool != null) {
            LOGGER.info("from jedisSentinelPool get Jedis");
            try(Jedis resource = jedisSentinelPool.getResource()) {
                return resource;
            } catch (Exception e){
                LOGGER.info("from jedisSentinelPool get Jedis failed, try jedisPool");
                throw e;
            }
        }
        if (jedisPool != null) {
            LOGGER.info("from jedisPool get Jedis");
            try(Jedis resource = jedisPool.getResource()) {
                return resource;
            } catch (JedisConnectionException exception) {
                LOGGER.info("connect redis failed, please check your configuration");
                throw exception;
            } catch (Exception e1) {
                e1.printStackTrace();
                throw e1;
            }
        }
        LOGGER.info("you should config a jedisSentinelPool or jedisPool");
        return null;
    }

    public Jedis getJedisOld() {
        try {
            if (jedisSentinelPool != null) {
                LOGGER.info("from jedisSentinelPool get Jedis");
                Jedis resource = jedisSentinelPool.getResource();
                return resource;
            } else if (jedisPool != null) {
                LOGGER.info("from jedisPool get Jedis");
                Jedis resource = jedisPool.getResource();
                return resource;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    /**
     * 释放jedis资源
     * @param jedis
     */
    public void close(final Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    public String getValue(String key) {
        return getJedis().get(key);
    }
}
