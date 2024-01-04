package org.ddd.fundamental.message.kafka.mockproducer;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.concurrent.Future;

public class KafkaMockProducer {

    public static final String DEFAULT_TOPIC_SPORTS_NEWS = "topic_sports_news";
    private final Producer<String, String> producer;

    public KafkaMockProducer(Producer<String, String> producer) {
        this.producer = producer;
    }

    public Future<RecordMetadata> send(String key, String value) {
        ProducerRecord record = new ProducerRecord(DEFAULT_TOPIC_SPORTS_NEWS, key, value);
        return producer.send(record);
    }

    public void flush() {
        producer.flush();
    }

    public void beginTransaction() {
        producer.beginTransaction();
    }

    public void initTransaction() {
        producer.initTransactions();
    }

    public void commitTransaction() {
        producer.commitTransaction();
    }
}
