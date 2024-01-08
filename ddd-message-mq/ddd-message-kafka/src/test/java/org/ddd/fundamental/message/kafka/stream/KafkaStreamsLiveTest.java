package org.ddd.fundamental.message.kafka.stream;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;
import org.ddd.fundamental.message.kafka.serdes.MessageDTO;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class KafkaStreamsLiveTest {

    @ClassRule
    public static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:5.4.3"));

    private static final String CONSUMER_APP_ID = "consumer_id";
    private Path stateDirectory;

    private final String TOPIC = "inputTopic";

    @BeforeEach
    public void setUp() {
        kafka.start();
    }

    private static KafkaProducer<String, String> createKafkaProducer() {

        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers());
        props.put(ProducerConfig.CLIENT_ID_CONFIG, CONSUMER_APP_ID);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        return new KafkaProducer(props);

    }

    @Test
    @Ignore("it needs to have kafka broker running on local")
    public void shouldTestKafkaStreams() throws InterruptedException {
        // given

        Properties streamsConfiguration = new Properties();
        //应用的名字
        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount-live-test");
        //配置kafka的地址
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers());
        //key的序列化类
        streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String()
                .getClass()
                .getName());
        //value的序列化类
        streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String()
                .getClass()
                .getName());
        //多久时间后提交
        streamsConfiguration.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 1000);
        //https://stackoverflow.com/questions/32390265/what-determines-kafka-consumer-offset
        streamsConfiguration.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // Use a temporary directory for storing state, which will be automatically removed after the test.
        try {
            this.stateDirectory = Files.createTempDirectory("kafka-streams");
            streamsConfiguration.put(StreamsConfig.STATE_DIR_CONFIG, this.stateDirectory.toAbsolutePath()
                    .toString());
        } catch (final IOException e) {
            throw new UncheckedIOException("Cannot create temporary directory", e);
        }

        // when 构建流拓扑图
        final StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> textLines = builder.stream(TOPIC);
        Pattern pattern = Pattern.compile("\\W+", Pattern.UNICODE_CHARACTER_CLASS);

        KTable<String, Long> wordCounts = textLines.flatMapValues(value -> Arrays.asList(pattern.split(value.toLowerCase())))
                .groupBy((key, word) -> word)
                .count();

        wordCounts.toStream()
                .foreach((word, count) -> System.out.println("word: " + word + " -> " + count));

        String outputTopic = "outputTopic";

        wordCounts.toStream()
                .to(outputTopic, Produced.with(Serdes.String(), Serdes.Long()));
        //构建拓扑图对象
        final Topology topology = builder.build();
        //构建kafkaStream对象，并且启动该对象
        KafkaStreams streams = new KafkaStreams(topology, streamsConfiguration);
        streams.start();
        sendMessage();
        // then
        TimeUnit.SECONDS.sleep(30);
        streams.close();
    }

    /**
     * 模拟多次发送消息,然后流会统计并计算消息
     * @throws InterruptedException
     */
    private void sendMessage() throws InterruptedException {
        KafkaProducer<String, String> producer = createKafkaProducer();
        producer.send(new ProducerRecord<>(TOPIC, "1", "this is a pony"));
        producer.send(new ProducerRecord<>(TOPIC, "1", "this is a horse and pony"));
        TimeUnit.SECONDS.sleep(2);
        producer.send(new ProducerRecord<>(TOPIC, "1", "this is a stream and test, you should access horse"));
        TimeUnit.SECONDS.sleep(5);
        producer.send(new ProducerRecord<>(TOPIC, "1", "this is haha"));
    }
}
