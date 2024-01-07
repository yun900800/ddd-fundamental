package org.ddd.fundamental.message.kafka.serdes;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KafkaSerDesLiveTest {
    private static final String CONSUMER_APP_ID = "consumer_id";
    private static final String CONSUMER_GROUP_ID = "group_id";

    @ClassRule
    public static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:5.4.3"));
    private final String TOPIC = "serialization-des-topic";

    /**
     * 创建一个消费者,需要配置consumer_id和group_id
     * @return
     */
    private static KafkaConsumer<String, MessageDTO> createKafkaConsumer() {

        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers());
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, CONSUMER_APP_ID);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, CONSUMER_GROUP_ID);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.ddd.fundamental.message.kafka.serdes.CustomDeserializer");

        return new KafkaConsumer<>(props);

    }

    /**
     * 创建一个生产者
     * @return
     */
    private static KafkaProducer<String, MessageDTO> createKafkaProducer() {

        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers());
        props.put(ProducerConfig.CLIENT_ID_CONFIG, CONSUMER_APP_ID);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.ddd.fundamental.message.kafka.serdes.CustomSerializer");

        return new KafkaProducer(props);

    }

    @BeforeEach
    public void setUp() {
        kafka.start();
    }

    @Test
    public void givenKafkaClientShouldSerializeAndDeserialize() throws InterruptedException {

        MessageDTO msgProd = new MessageDTO("this is serialization-des test","1.0");

        KafkaProducer<String, MessageDTO> producer = createKafkaProducer();
        producer.send(new ProducerRecord<>(TOPIC, "1", msgProd));
        System.out.println("Message sent " + msgProd);
        producer.close();

        Thread.sleep(1000);

        AtomicReference<MessageDTO> msgCons = new AtomicReference<>();

        KafkaConsumer<String, MessageDTO> consumer = createKafkaConsumer();
        //通过订阅的方式进行消费
        consumer.subscribe(Arrays.asList(TOPIC));

        ConsumerRecords<String, MessageDTO> records = consumer.poll(Duration.ofSeconds(1));
        records.forEach(record -> {
            msgCons.set(record.value());
            System.out.println("Message received " + record.value());
        });

        consumer.close();

        assertEquals(msgProd.toString(), msgCons.get().toString());

    }
}
