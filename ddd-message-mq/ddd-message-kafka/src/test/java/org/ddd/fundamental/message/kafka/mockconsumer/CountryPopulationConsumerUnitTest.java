package org.ddd.fundamental.message.kafka.mockconsumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.MockConsumer;
import org.apache.kafka.clients.consumer.OffsetResetStrategy;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 本测试需要理解两种不同的消费的区别
 * consumer.assign vs consumer.subscribe
 * https://stackoverflow.com/questions/53938125/kafkaconsumer-java-api-subscribe-vs-assign
 */
public class CountryPopulationConsumerUnitTest {

    /**
     *  定义一个主题
     */
    private static final String TOPIC = "topic";

    /**
     * 定义一个分区
     */
    private static final int PARTITION = 0;

    /**
     * 定义一个国家人口消费者
     */
    private CountryPopulationConsumer countryPopulationConsumer;

    /**
     * 定义国家人口数组
     */
    private List<CountryPopulation> updates;
    /**
     * 拉取数据消费的时候抛出的异常
     */
    private Throwable pollException;

    /**
     * mock出来的消费者
     */
    private MockConsumer<String, Integer> consumer;

    @BeforeEach
    void setUp() {
        consumer = new MockConsumer<>(OffsetResetStrategy.EARLIEST);
        updates = new ArrayList<>();
        countryPopulationConsumer = new CountryPopulationConsumer(consumer, ex -> this.pollException = ex, updates::add);
    }

    @Test
    void whenStartingByAssigningTopicPartition_thenExpectUpdatesAreConsumedCorrectly() {
        // GIVEN 假定了两次轮询 给主题topic和分区0添加了一条消费记录
        consumer.schedulePollTask(() -> consumer.addRecord(record(TOPIC, PARTITION,0, "Romania", 19_410_000)));
        consumer.schedulePollTask(() -> consumer.addRecord(record(TOPIC, PARTITION, 1,"French", 19_410_001)));
        //唤醒消费者
        consumer.schedulePollTask(() -> countryPopulationConsumer.stop());

        //设置了主题和分区的偏移为0
        HashMap<TopicPartition, Long> startOffsets = new HashMap<>();
        TopicPartition tp = new TopicPartition(TOPIC, PARTITION);
        startOffsets.put(tp, 0L);
        //更新消费者的偏移量
        consumer.updateBeginningOffsets(startOffsets);

        // WHEN
        countryPopulationConsumer.startByAssigning(TOPIC, PARTITION);

        // THEN
        assertThat(updates).hasSize(2);
        assertThat(consumer.closed()).isTrue();

    }

    @Test
    void whenStartingBySubscribingToTopic_thenExpectUpdatesAreConsumedCorrectly() {
        // GIVEN
        consumer.schedulePollTask(() -> {
            consumer.rebalance(Collections.singletonList(new TopicPartition(TOPIC, 0)));
            consumer.addRecord(record(TOPIC, PARTITION, "Romania", 19_410_000));
        });
        consumer.schedulePollTask(() -> countryPopulationConsumer.stop());

        HashMap<TopicPartition, Long> startOffsets = new HashMap<>();
        TopicPartition tp = new TopicPartition(TOPIC, PARTITION);
        startOffsets.put(tp, 0L);
        consumer.updateBeginningOffsets(startOffsets);

        // WHEN
        countryPopulationConsumer.startBySubscribing(TOPIC);

        // THEN
        assertThat(updates).hasSize(1);
        assertThat(consumer.closed()).isTrue();
    }

    /**
     * 测试消费者pool的时候抛出异常, 同时消费者正常关闭
     */
    @Test
    void whenStartingBySubscribingToTopicAndExceptionOccurs_thenExpectExceptionIsHandledCorrectly() {
        // GIVEN
        consumer.schedulePollTask(() -> consumer.setPollException(new KafkaException("poll exception")));
        consumer.schedulePollTask(() -> countryPopulationConsumer.stop());

        HashMap<TopicPartition, Long> startOffsets = new HashMap<>();
        TopicPartition tp = new TopicPartition(TOPIC, 0);
        startOffsets.put(tp, 0L);
        consumer.updateBeginningOffsets(startOffsets);

        // WHEN
        countryPopulationConsumer.startBySubscribing(TOPIC);

        // THEN
        assertThat(pollException).isInstanceOf(KafkaException.class).hasMessage("poll exception");
        assertThat(consumer.closed()).isTrue();
    }

    /**
     * 特定主题,特定分区某个国家的人口
     * @param topic
     * @param partition
     * @param country
     * @param population
     * @return
     */
    private ConsumerRecord<String, Integer> record(String topic, int partition, String country, int population) {
        return new ConsumerRecord<>(topic, partition, 0, country, population);
    }

    /**
     * 特定主题,特定分区,特定偏移量,某个国家的人口
     * @param topic
     * @param partition
     * @param offset
     * @param country
     * @param population
     * @return
     */
    private ConsumerRecord<String, Integer> record(String topic, int partition, int offset, String country, int population) {
        return new ConsumerRecord<>(topic, partition, offset, country, population);
    }
}
