package org.ddd.fundamental.message.kafka.exactlyonce;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.TopicPartition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.time.Duration.ofSeconds;

public class TransactionalWordCount {

    private static final String CONSUMER_GROUP_ID = "my-group-id";
    private static final String OUTPUT_TOPIC = "output";
    private static final String INPUT_TOPIC = "input";

    private static boolean dataConsumed = false;

    public static void consumerCalculateSentence(KafkaProducer<String, String> producer,
                                                 KafkaConsumer<String, String> consumer) {

        producer.initTransactions();

        try {

            while (true) {
                //开始消费数据，并且转化以后交给下一个生产者来发送
                ConsumerRecords<String, String> records = consumer.poll(ofSeconds(2));

                Map<String, Integer> wordCountMap = records.records(new TopicPartition(INPUT_TOPIC, 0))
                        .stream()
                        .flatMap(record -> Stream.of(record.value()
                                .split(" ")))
                        .map(word -> Tuple.of(word, 1))
                        .collect(Collectors.toMap(tuple -> tuple.getKey(), t1 -> t1.getValue(), (v1, v2) -> v1 + v2));

                System.out.println("wordCountMap:"+wordCountMap);

                producer.beginTransaction();

                //这里可以使用多个线程来发送异步消息
                wordCountMap.forEach((key, value) -> producer.send(new ProducerRecord<>(OUTPUT_TOPIC, key, value.toString())));

                Map<TopicPartition, OffsetAndMetadata> offsetsToCommit = new HashMap<>();

                for (TopicPartition partition : records.partitions()) {
                    //获取一个主题分区对应的记录数目
                    List<ConsumerRecord<String, String>> partitionedRecords = records.records(partition);
                    //获取最后一个记录对应的分区
                    long offset = partitionedRecords.get(partitionedRecords.size() - 1)
                            .offset();
                    //即将到来的数据对应的偏移量
                    offsetsToCommit.put(partition, new OffsetAndMetadata(offset + 1));
                }
                //消费结束之后并且生产成功发送数据之后再发送偏移量
                //目的是为了保持事务的一致性
                producer.sendOffsetsToTransaction(offsetsToCommit, new ConsumerGroupMetadata(CONSUMER_GROUP_ID));
                producer.commitTransaction();
                if (!wordCountMap.isEmpty()) {
                    dataConsumed = true;
                }
                if (dataConsumed) {
                    break;
                }

            }

        } catch (KafkaException e) {

            producer.abortTransaction();

        }

    }
}
