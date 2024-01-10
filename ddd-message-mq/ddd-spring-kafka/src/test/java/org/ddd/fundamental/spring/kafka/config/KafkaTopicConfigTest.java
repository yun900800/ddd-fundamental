package org.ddd.fundamental.spring.kafka.config;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
public class KafkaTopicConfigTest {

    @Autowired
    EmbeddedKafkaBroker embeddedKafkaBroker;
    @Autowired
    KafkaAdmin admin;

    private String topic = "embeddedKafkaBroker";

    @Test
    public void testKafkaAdminNotNull(){
        Assert.assertNull(admin);
    }

    @TestConfiguration
    class TestConfigurationClass{
        @Bean
        public ProducerFactory<String, String> producerFactory() {
            return new DefaultKafkaProducerFactory<>(KafkaTestUtils.producerProps(embeddedKafkaBroker));
        }

        @Bean
        public KafkaTemplate<String, String> kafkaTemplate() {
            KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<>(producerFactory());
            kafkaTemplate.setDefaultTopic(topic);
            return kafkaTemplate;
        }
    }
}
