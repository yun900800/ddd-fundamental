package org.ddd.fundamental.message.kafka.streamsvsconsumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.util.Arrays;
import java.util.Locale;
import java.util.Properties;

import static org.apache.kafka.clients.CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG;

public class KafkaStreamsLiveTest {

    /**
     * 定义三个topic
     */
    private final String LEFT_TOPIC = "left-stream-topic";
    private final String RIGHT_TOPIC = "right-stream-topic";
    private final String LEFT_RIGHT_TOPIC = "left-right-stream-topic";

    static final String TEXT_LINES_TOPIC = "TextLinesTopic";

    /**
     * 创建一个发送字符串的生产者
     */
    private KafkaProducer<String, String> producer = createKafkaProducer();
    private Properties streamsConfiguration = new Properties();

    /**
     * 定义两个需要发送的字符串
     */
    private final String TEXT_EXAMPLE_1 = "test test and test";
    private final String TEXT_EXAMPLE_2 = "test filter filter this sentence";

    @ClassRule
    public static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:5.4.3"));

    @BeforeEach
    public void setUp() {
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers());
        streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        streamsConfiguration.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 1000);
        streamsConfiguration.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    }

    @Test
    public void shouldTestKafkaTableLatestWord() throws InterruptedException {
        String inputTopic = "topicTable";

        final StreamsBuilder builder = new StreamsBuilder();

        //这里是以表的形式来读取
        KTable<String, String> textLinesTable = builder.table(inputTopic,
                Consumed.with(Serdes.String(), Serdes.String()));
        //key对应的是生产者发送的key,而value对应的是具体的数据
        textLinesTable.toStream().foreach((word, count) -> System.out.println("Latest word: " + word + " -> " + count));

        final Topology topology = builder.build();
        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "latest-word-id");
        KafkaStreams streams = new KafkaStreams(topology, streamsConfiguration);

        streams.cleanUp();
        streams.start();
        producer.send(new ProducerRecord<>(inputTopic, "1", TEXT_EXAMPLE_1));
        producer.send(new ProducerRecord<>(inputTopic, "2", TEXT_EXAMPLE_2));

        Thread.sleep(2000);
        streams.close();
    }

    @Test
    public void shouldTestWordCountKafkaStreams() throws InterruptedException {
        String wordCountTopic = "wordCountTopic";

        final StreamsBuilder builder = new StreamsBuilder();
        //这里以流的形式来读取数据
        KStream<String, String> textLines = builder.stream(wordCountTopic,
                Consumed.with(Serdes.String(), Serdes.String()));

        //将流进行处理得到表的格式
        KTable<String, Long> wordCounts = textLines
                //展平为单词的流
                .flatMapValues(value -> Arrays.asList(value.toLowerCase(Locale.ROOT)
                        .split("\\W+")))
                .groupBy((key, word) -> word)
                .count(Materialized.<String, Long, KeyValueStore<Bytes, byte[]>> as("counts-store"));

        wordCounts.toStream().foreach((word, count) -> System.out.println("Word: " + word + " -> " + count));
        //将表数据转化为流然后发送到同样的集群当中
        wordCounts.toStream().to("outputTopic",
                Produced.with(Serdes.String(), Serdes.Long()));

        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount-stream-table-id");
        //构建拓扑图
        final Topology topology = builder.build();
        KafkaStreams streams = new KafkaStreams(topology, streamsConfiguration);

        streams.cleanUp();
        streams.start();

        producer.send(new ProducerRecord<String, String>(wordCountTopic, "1", TEXT_EXAMPLE_1));
        producer.send(new ProducerRecord<String, String>(wordCountTopic, "2", TEXT_EXAMPLE_2));

        Thread.sleep(2000);
        streams.close();
    }

    /**
     * 测试无状态的的转化
     * @throws InterruptedException
     */
    @Test
    public void shouldTestStatelessTransformations() throws InterruptedException {
        String wordCountTopic = "wordCountTopic";

        //when
        final StreamsBuilder builder = new StreamsBuilder();
        //构建流
        KStream<String, String> textLines = builder.stream(wordCountTopic,
                Consumed.with(Serdes.String(), Serdes.String()));

        //进行转化
        final KStream<String, String> textLinesUpperCase =
                textLines
                        .map((key, value) -> KeyValue.pair(value, value.toUpperCase()))
                        .filter((key, value) -> value.contains("FILTER"));
        //先过滤然后进行统计
        KTable<String, Long> wordCounts = textLinesUpperCase
                .flatMapValues(value -> Arrays.asList(value.split("\\W+")))
                .groupBy((key, word) -> word)
                .count(Materialized.<String, Long, KeyValueStore<Bytes, byte[]>> as("counts-store"));

        wordCounts.toStream().foreach((word, count) -> System.out.println("Word: " + word + " -> " + count));

        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount-filter-map-id");
        final Topology topology = builder.build();
        KafkaStreams streams = new KafkaStreams(topology, streamsConfiguration);

        streams.cleanUp();
        streams.start();

        producer.send(new ProducerRecord<String, String>(wordCountTopic, "1", TEXT_EXAMPLE_1));
        producer.send(new ProducerRecord<String, String>(wordCountTopic, "2", TEXT_EXAMPLE_2));

        Thread.sleep(2000);
        streams.close();

    }

    /**
     * 测试有状态的转换
     * @throws InterruptedException
     */
    @Test
    public void shouldTestAggregationStatefulTransformations() throws InterruptedException {
        String aggregationTopic = "aggregationTopic";

        final StreamsBuilder builder = new StreamsBuilder();
        /**
         * 构建流
         */
        final KStream<byte[], String> input = builder.stream(aggregationTopic,
                Consumed.with(Serdes.ByteArray(), Serdes.String()));
        //将流转换成表
        final KTable<String, Long> aggregated = input
                .groupBy((key, value) -> (value != null && value.length() > 0) ? value.substring(0, 2).toLowerCase() : "",
                        Grouped.with(Serdes.String(), Serdes.String()))
                //这里的三个值 aggKey是流中的key值, aggValue是上一个初始值,newValue是当前值
                .aggregate(() -> 0L, (aggKey, newValue, aggValue) ->aggValue + newValue.length(),
                        Materialized.with(Serdes.String(), Serdes.Long()));

        aggregated.toStream().foreach((word, count) -> System.out.println("Word: " + word + " -> " + count));

        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "aggregation-id");
        final Topology topology = builder.build();
        KafkaStreams streams = new KafkaStreams(topology, streamsConfiguration);

        streams.cleanUp();
        streams.start();

        producer.send(new ProducerRecord<String, String>(aggregationTopic, "1", "one"));
        producer.send(new ProducerRecord<String, String>(aggregationTopic, "2", "two"));
        producer.send(new ProducerRecord<String, String>(aggregationTopic, "3", "three"));
        producer.send(new ProducerRecord<String, String>(aggregationTopic, "4", "four"));
        producer.send(new ProducerRecord<String, String>(aggregationTopic, "5", "five"));
        producer.send(new ProducerRecord<String, String>(aggregationTopic, "6", "onHome"));

        Thread.sleep(5000);
        streams.close();

    }

    /**
     * 测试将两个流数据合并为一个流数据
     * @throws InterruptedException
     */
    @Test
    public void shouldTestWindowingJoinStatefulTransformations() throws InterruptedException {
        final StreamsBuilder builder = new StreamsBuilder();

        KStream<String, String> leftSource = builder.stream(LEFT_TOPIC);
        KStream<String, String> rightSource = builder.stream(RIGHT_TOPIC);
        //将两个流进行outerJoin并在5s内进项观察,然后按照key进行分组,最后返回最新的值
        KStream<String, String> leftRightSource = leftSource.outerJoin(rightSource,
                        (leftValue, rightValue) -> "left=" + leftValue + ", right=" + rightValue,
                        JoinWindows.of(Duration.ofSeconds(5)))
                .groupByKey()
                .reduce(((key, lastValue) -> lastValue))
                .toStream();

        leftRightSource.foreach((key, value) -> System.out.println("(key= " + key + ") -> (" + value + ")"));
        //构建拓扑图
        final Topology topology = builder.build();
        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "windowing-join-id");
        KafkaStreams streams = new KafkaStreams(topology, streamsConfiguration);

        streams.cleanUp();
        streams.start();

        producer.send(new ProducerRecord<String, String>(LEFT_TOPIC, "1", "left"));
        producer.send(new ProducerRecord<String, String>(RIGHT_TOPIC, "1", "top"));
        producer.send(new ProducerRecord<String, String>(RIGHT_TOPIC, "2", "right"));
        producer.send(new ProducerRecord<String, String>(LEFT_TOPIC, "2", "bottom"));

        Thread.sleep(2000);
        streams.close();
    }

    @Test
    public void shouldTestWordCountWithInteractiveQueries() throws InterruptedException {

        final Serde<String> stringSerde = Serdes.String();
        final StreamsBuilder builder = new StreamsBuilder();
        //构建stream
        final KStream<String, String>
                textLines = builder.stream(TEXT_LINES_TOPIC, Consumed.with(Serdes.String(), Serdes.String()));
        //将单词展平后进行分组
        final KGroupedStream<String, String> groupedByWord = textLines
                .flatMapValues(value -> Arrays.asList(value.toLowerCase().split("\\W+")))
                .groupBy((key, word) -> word, Grouped.with(stringSerde, stringSerde));
        //统计每组单词的长度,并且存储起来
        groupedByWord.count(Materialized.<String, Long, KeyValueStore<Bytes, byte[]>>as("WordCountsStore")
                .withValueSerde(Serdes.Long()));

        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount-interactive-queries");

        final KafkaStreams streams = new KafkaStreams(builder.build(), streamsConfiguration);
        streams.cleanUp();
        streams.start();

        producer.send(new ProducerRecord<String, String>(TEXT_LINES_TOPIC, "1", TEXT_EXAMPLE_1));
        producer.send(new ProducerRecord<String, String>(TEXT_LINES_TOPIC, "2", TEXT_EXAMPLE_2));

        Thread.sleep(2000);
        //从store中进行查询
        ReadOnlyKeyValueStore<String, Long> keyValueStore =
                streams.store(StoreQueryParameters.fromNameAndType(
                        "WordCountsStore", QueryableStoreTypes.keyValueStore()));

        KeyValueIterator<String, Long> range = keyValueStore.all();
        while (range.hasNext()) {
            KeyValue<String, Long> next = range.next();
            System.out.println("Count for " + next.key + ": " + next.value);
        }

        streams.close();
    }

    private static KafkaProducer<String, String> createKafkaProducer() {
        kafka.start();
        Properties props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers());
        props.put(KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        return new KafkaProducer(props);

    }

}
