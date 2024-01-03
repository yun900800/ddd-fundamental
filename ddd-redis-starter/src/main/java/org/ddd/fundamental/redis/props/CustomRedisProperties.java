package org.ddd.fundamental.redis.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ConfigurationProperties(prefix = "ddd.redis")
public class CustomRedisProperties {

    private String host = "localhost";

    private int port = 6379;

    private JedisSentinel sentinel;

    public JedisSentinel getSentinel() {
        return sentinel;
    }

    public void setSentinel(JedisSentinel sentinel) {
        this.sentinel = sentinel;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public static class JedisSentinel {

        private String master;

        /**
         * Comma-separated list of "host:port" pairs.
         */
        private List<String> nodes;

        public String getMaster() {
            return this.master;
        }

        public void setMaster(String master) {
            this.master = master;
        }

        public List<String> getNodes() {
            return this.nodes;
        }

        public void setNodes(List<String> nodes) {
            this.nodes = nodes;
        }
    }

    public JedisPool getJedisPool(){
        JedisPoolConfig config = new JedisPoolConfig();
        //省略具体设置
        JedisPool myJedisPool = new JedisPool(config, host, port);

        return myJedisPool;
    }

    public JedisSentinelPool getJedisSentinelPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        if (null!= this.sentinel) {
            String masterName = this.sentinel.getMaster();
            Set<String> sentinels = new HashSet<>(this.sentinel.getNodes());
            JedisSentinelPool myJedisPool = new JedisSentinelPool(masterName, sentinels, config);
            return myJedisPool;
        }
        return null;
    }
}
