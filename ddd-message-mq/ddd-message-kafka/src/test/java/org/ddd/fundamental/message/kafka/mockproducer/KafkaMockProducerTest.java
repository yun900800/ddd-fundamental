package org.ddd.fundamental.message.kafka.mockproducer;

import org.apache.kafka.clients.producer.MockProducer;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.Node;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static java.util.Collections.emptySet;
import static org.junit.jupiter.api.Assertions.*;

public class KafkaMockProducerTest {

    private final String TOPIC_NAME = KafkaMockProducer.DEFAULT_TOPIC_SPORTS_NEWS;

    private KafkaMockProducer kafkaMockProducer;
    private MockProducer<String, String> mockProducer;

    private void buildMockProducer(boolean autoComplete) {
        this.mockProducer = new MockProducer<>(autoComplete, new StringSerializer(), new StringSerializer());
    }

    @Test
    void givenKeyValue_whenSend_thenVerifyHistory() throws ExecutionException, InterruptedException {

        //构建自动flush的生产者,没有事务
        buildMockProducer(true);
        //when
        kafkaMockProducer = new KafkaMockProducer(mockProducer);
        Future<RecordMetadata> recordMetadataFuture = kafkaMockProducer.send("data", "{\"site\" : \"baeldung\"}");
        Future<RecordMetadata> recordMetadataFuture1 = kafkaMockProducer.send("data1", "{\"site1\" : \"devnote.pro\"}");
        //then
        assertTrue(mockProducer.history().size() == 2);
        assertTrue(mockProducer.history().get(0).key().equalsIgnoreCase("data"));
        assertTrue(mockProducer.history().get(1).key().equalsIgnoreCase("data1"));
        assertTrue(recordMetadataFuture.get().partition() == 0);
        assertTrue(recordMetadataFuture1.get().partition() == 0);

        assertTrue(recordMetadataFuture.isDone());

    }
    @Test
    void givenKeyValue_whenSend_thenSendOnlyAfterFlush() {
        //构建不是自动flush的生产者
        buildMockProducer(false);
        //when
        kafkaMockProducer = new KafkaMockProducer(mockProducer);
        Future<RecordMetadata> record = kafkaMockProducer.send("data", "{\"site\" : \"baeldung\"}");
        Future<RecordMetadata> record1 = kafkaMockProducer.send("data1", "{\"site1\" : \"devnote.pro\"}");
        assertFalse(record.isDone());
        assertFalse(record1.isDone());
        //then 这里需要手动flush
        kafkaMockProducer.flush();
        assertTrue(record.isDone());
        assertTrue(record1.isDone());
    }

    /**
     * kafka一旦生产者抛出了异常,这个发送记录也算完成
     */
    @Test
    void givenKeyValue_whenSend_thenReturnException() {
        buildMockProducer(false);
        //when
        kafkaMockProducer= new KafkaMockProducer(mockProducer);
        Future<RecordMetadata> record = kafkaMockProducer.send("data", "{\"site\" : \"baeldung\"}");
        RuntimeException e = new RuntimeException();
        mockProducer.errorNext(e);
        //then
        try {
            record.get();
        } catch (ExecutionException | InterruptedException ex) {
            assertEquals(e, ex.getCause());
        }
        assertTrue(record.isDone());
    }

    /**
     * 测试kafka的事务发送,没有commit之前发送成功,但是在commit之后才能看到值
     */
    @Test
    void givenKeyValue_whenSendWithTxn_thenSendOnlyOnTxnCommit() {
        //自动flush,有事务,send后没有值,一定要commit之后才有值
        buildMockProducer(true);
        //when
        kafkaMockProducer = new KafkaMockProducer(mockProducer);
        kafkaMockProducer.initTransaction();
        kafkaMockProducer.beginTransaction();
        Future<RecordMetadata> record = kafkaMockProducer.send("data", "{\"site\" : \"baeldung\"}");

        //then
        assertTrue(record.isDone());
        assertTrue(mockProducer.history().isEmpty());
        kafkaMockProducer.commitTransaction();
        assertTrue(mockProducer.history().size() == 1);
    }

    @Test
    void givenKeyValue_whenSendWithPartitioning_thenVerifyPartitionNumber() throws ExecutionException, InterruptedException {

        PartitionInfo partitionInfo0 = new PartitionInfo(TOPIC_NAME, 0, null, null, null);
        PartitionInfo partitionInfo1 = new PartitionInfo(TOPIC_NAME, 1, null, null, null);
        List<PartitionInfo> list = new ArrayList<>();
        list.add(partitionInfo0);
        list.add(partitionInfo1);
        Cluster cluster = new Cluster("kafka", new ArrayList<Node>(), list, emptySet(), emptySet());
        this.mockProducer = new MockProducer<>(cluster, true, new EvenOddPartitioner(), new StringSerializer(), new StringSerializer());
        //when
        kafkaMockProducer = new KafkaMockProducer(mockProducer);
        Future<RecordMetadata> recordMetadataFuture = kafkaMockProducer.send("partition", "{\"site\" : \"baeldung\"}");

        Future<RecordMetadata> recordMetadataFuture1 = kafkaMockProducer.send("yun900", "{\"site1\" : \"yun900\"}");

        //then
        assertTrue(recordMetadataFuture.get().partition() == 1);
        assertTrue(recordMetadataFuture1.get().partition() == 0);

    }
}
