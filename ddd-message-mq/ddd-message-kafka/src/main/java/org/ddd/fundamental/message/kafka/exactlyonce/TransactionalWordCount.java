package org.ddd.fundamental.message.kafka.exactlyonce;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
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

                ConsumerRecords<String, String> records = consumer.poll(ofSeconds(2));

                Map<String, Integer> wordCountMap = records.records(new TopicPartition(INPUT_TOPIC, 0))
                        .stream()
                        .flatMap(record -> Stream.of(record.value()
                                .split(" ")))
                        .map(word -> Tuple.of(word, 1))
                        .collect(Collectors.toMap(tuple -> tuple.getKey(), t1 -> t1.getValue(), (v1, v2) -> v1 + v2));

                producer.beginTransaction();
                System.out.println("wordCountMap:"+wordCountMap);
                if (!wordCountMap.isEmpty()) {
                    dataConsumed = true;
                }
                if (dataConsumed) {
                    break;
                }

                wordCountMap.forEach((key, value) -> producer.send(new ProducerRecord<String, String>(OUTPUT_TOPIC, key, value.toString())));

                Map<TopicPartition, OffsetAndMetadata> offsetsToCommit = new HashMap<>();

                for (TopicPartition partition : records.partitions()) {
                    List<ConsumerRecord<String, String>> partitionedRecords = records.records(partition);
                    long offset = partitionedRecords.get(partitionedRecords.size() - 1)
                            .offset();

                    offsetsToCommit.put(partition, new OffsetAndMetadata(offset + 1));
                }

                producer.sendOffsetsToTransaction(offsetsToCommit, CONSUMER_GROUP_ID);
                producer.commitTransaction();

            }

        } catch (KafkaException e) {

            producer.abortTransaction();

        }

    }
}
