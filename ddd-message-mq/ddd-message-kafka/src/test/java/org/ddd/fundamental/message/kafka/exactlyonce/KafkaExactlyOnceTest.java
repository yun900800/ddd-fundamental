package org.ddd.fundamental.message.kafka.exactlyonce;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static java.util.Collections.singleton;
import static org.apache.kafka.clients.CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.CommonClientConfigs.GROUP_ID_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.*;
import static org.apache.kafka.clients.producer.ProducerConfig.*;
import static org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG;

public class KafkaExactlyOnceTest {

    @ClassRule
    public static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:5.4.3"));
    private static final String CONSUMER_GROUP_ID = "my-group-id";
    private static final String OUTPUT_TOPIC = "output";
    private static final String INPUT_TOPIC = "input";

    private static KafkaProducer<String, String> createKafkaProducer() {

        Properties props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers());
        props.put(ENABLE_IDEMPOTENCE_CONFIG, "true");
        props.put(TRANSACTIONAL_ID_CONFIG, "prod-0");
        props.put(KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        return new KafkaProducer(props);
    }

    private static KafkaProducer<String, String> createKafkaProducer1() {

        Properties props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers());
        props.put(ENABLE_IDEMPOTENCE_CONFIG, "true");
        props.put(TRANSACTIONAL_ID_CONFIG, "prod-1");
        props.put(KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        return new KafkaProducer(props);

    }

    private static KafkaConsumer<String, String> createKafkaConsumer() {
        Properties props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers());
        props.put(GROUP_ID_CONFIG, CONSUMER_GROUP_ID);
        props.put(ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ISOLATION_LEVEL_CONFIG, "read_committed");
        props.put(KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(singleton(INPUT_TOPIC));
        return consumer;
    }

    @BeforeEach
    public void setUp() {
        kafka.start();
    }

    @Test
    public void testKafkaExactlyOnce() throws InterruptedException {
        //消费者先读数据,然后生产者再4s以后发送数据
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(4);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            TransactionalMessageProducer.producerSendSentence(createKafkaProducer());
        }).start();
        TransactionalWordCount.consumerCalculateSentence(createKafkaProducer1(),createKafkaConsumer());


    }
}
