package org.ddd.fundamental.message.kafka.header;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ProducerWithHeaderMessageTest {
    private static Logger logger = LoggerFactory.getLogger(ProducerWithHeaderMessageTest.class);

    private static String TOPIC = "header-with-message";
    private static String MESSAGE_KEY = "message";
    private static String MESSAGE_VALUE = "message-with-header";
    private static String HEADER_KEY = "header";
    private static String HEADER_VALUE = "header-with-message";

    private KafkaProducer<String, String> producer;
    private KafkaConsumer<String, String> consumer;

    @ClassRule
    public static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:5.4.3"));

    @Before
    public void setup() {
        Properties producerProperties = new Properties();
        producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers());
        producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        Properties consumerProperties = new Properties();
        consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers());
        consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, "ConsumerGroup1");
        consumerProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

        producer = new KafkaProducer<>(producerProperties);
        consumer = new KafkaConsumer<>(consumerProperties);
    }

    @Test
    public void testProducerSendMessageWithHeader() {

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
                publishMessageWithCustomHeaders();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        consumeMessageWithCustomHeaders();

    }

    private void consumeMessageWithCustomHeaders() {
        consumer.subscribe(Arrays.asList(TOPIC));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(4));
            for (ConsumerRecord<String, String> record : records) {
                Assert.assertEquals("message",record.key());
                Assert.assertEquals("message-with-header",record.value());
                logger.info(record.key());
                logger.info(record.value());
                Headers headers = record.headers();
                for (Header header : headers) {
                    Assert.assertEquals("header",header.key());
                    Assert.assertEquals("header-with-message",new String(header.value()));
                    logger.info(header.key());
                    logger.info(new String(header.value()));
                }
            }
            logger.info("consumer is read message");
            if (!records.isEmpty()) {
                break;
            }
        }

    }

    private void publishMessageWithCustomHeaders() {
        List<Header> headers = new ArrayList<>();
        headers.add(new RecordHeader(HEADER_KEY, HEADER_VALUE.getBytes()));

        ProducerRecord<String, String> record1 = new ProducerRecord<>(TOPIC, null, MESSAGE_KEY, MESSAGE_VALUE, headers);
        producer.send(record1);

        ProducerRecord<String, String> record2 = new ProducerRecord<>(TOPIC, null, System.currentTimeMillis(), MESSAGE_KEY, MESSAGE_VALUE, headers);
        producer.send(record2);
    }

}
