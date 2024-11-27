package org.ddd.fundamental.kafka.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress = "localhost:9092";

    @Value(value = "${message.topic.name}")
    private String topicName;
}
