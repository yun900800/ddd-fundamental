package org.ddd.fundamental.spring.kafka.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    private static final Logger logger = LoggerFactory.getLogger(KafkaTopicConfig.class);

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress = "localhost:9092";

    @Value(value = "${message.topic.name}")
    private String topicName;

    @Value(value = "${long.message.topic.name}")
    private String longMsgTopicName;

    @Value(value = "${partitioned.topic.name}")
    private String partitionedTopicName;

    @Value(value = "${filtered.topic.name}")
    private String filteredTopicName;

    @Value(value = "${greeting.topic.name}")
    private String greetingTopicName;

    @Value(value = "${multi.type.topic.name}")
    private String multiTypeTopicName;

    /**
     * 配置一个创建topic的bean
     * @return KafkaAdmin
     */
    @Bean
    public KafkaAdmin kafkaAdmin() {
        logger.info("start create a bean:{}","kafkaAdmin");
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topic1() {
        logger.info("start create a topic with name:{}","topic1");
        return new NewTopic(topicName, 1, (short) 1);
    }

    @Bean
    public NewTopic topic2() {
        return new NewTopic(partitionedTopicName, 6, (short) 1);
    }

    @Bean
    public NewTopic topic3() {
        return new NewTopic(filteredTopicName, 1, (short) 1);
    }

    @Bean
    public NewTopic topic4() {
        return new NewTopic(greetingTopicName, 1, (short) 1);
    }

    @Bean
    public NewTopic topic5() {
        NewTopic newTopic = new NewTopic(longMsgTopicName, 1, (short) 1);
        Map<String, String> configs = new HashMap<>();
        configs.put("max.message.bytes", "20971520");
        newTopic.configs(configs);
        return newTopic;
    }

    @Bean
    public NewTopic multiTypeTopic() {
        return new NewTopic(multiTypeTopicName, 1, (short) 1);
    }
}
