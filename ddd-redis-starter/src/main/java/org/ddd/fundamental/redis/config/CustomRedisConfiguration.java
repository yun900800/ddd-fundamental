package org.ddd.fundamental.redis.config;

import org.ddd.fundamental.redis.props.CustomRedisProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisSentinelPool;

@Configuration
@ConditionalOnClass(CustomRedisProperties.class)
@EnableConfigurationProperties(CustomRedisProperties.class)
public class CustomRedisConfiguration {
    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    @ConditionalOnMissingBean(name="redisTemplate")
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        //key序列化方式
        template.setKeySerializer(redisSerializer);
        //value序列化
        template.setValueSerializer(redisSerializer);
        //value hashmap序列化
        template.setHashValueSerializer(redisSerializer);
        //key hashmap序列化
        template.setHashKeySerializer(redisSerializer);
        return template;
    }

    @Bean
    @ConditionalOnMissingBean(name="jedisTemplate")
    public JedisTemplate jedisTemplate(CustomRedisProperties customRedisProperties) {
        JedisTemplate jedisTemplate = new JedisTemplate();
        jedisTemplate.setJedisPool(customRedisProperties.getJedisPool());
        JedisSentinelPool jedisSentinelPool = customRedisProperties.getJedisSentinelPool();
        if (null != jedisSentinelPool) {
            jedisTemplate.setJedisSentinelPool(jedisSentinelPool);
        }

        return jedisTemplate;
    }
}
