package org.ddd.fundamental.spring.kafka.config;

import org.ddd.fundamental.spring.kafka.KafkaApplication;
import org.ddd.fundamental.spring.kafka.embedded.KafkaConsumer;
import org.ddd.fundamental.spring.kafka.embedded.KafkaProducer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertTrue;

@SpringBootTest(classes = {KafkaApplication.class})
@RunWith(SpringRunner.class)
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:29092", "port=29092" })
public class KafkaTopicConfigTest {

    @Autowired
    EmbeddedKafkaBroker embeddedKafkaBroker;
    @Autowired
    KafkaAdmin admin;

    @Value("${test.topic}")
    private String topic;

    @Autowired
    public KafkaTemplate<String, String> template;

    @Autowired
    private KafkaConsumer consumer;

    @Autowired
    private KafkaProducer producer;

//    @BeforeEach
//    public void setup() {
//        consumer.resetLatch();
//    }

    @Test
    public void testKafkaAdminNotNull(){

        Assert.assertNotNull(admin);
    }

    @Test
    public void givenEmbeddedKafkaBroker_whenSendingWithDefaultTemplate_thenMessageReceived() throws Exception {
        String data = "Sending with default template";
        consumer.resetLatch();
        template.send(topic, data);

        boolean messageConsumed = consumer.getLatch()
                .await(10, TimeUnit.SECONDS);
        assertTrue(messageConsumed);
        assertThat(consumer.getPayload(), containsString(data));
    }

    @Test
    public void givenEmbeddedKafkaBroker_whenSendingWithSimpleProducer_thenMessageReceived() throws Exception {
        String data = "Sending with our own simple KafkaProducer";
        consumer.resetLatch();
        producer.send(topic, data);

        boolean messageConsumed = consumer.getLatch()
                .await(10, TimeUnit.SECONDS);
        assertTrue(messageConsumed);
        assertThat(consumer.getPayload(), containsString(data));
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
