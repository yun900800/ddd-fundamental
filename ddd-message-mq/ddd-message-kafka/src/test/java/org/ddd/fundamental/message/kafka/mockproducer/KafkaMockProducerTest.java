package org.ddd.fundamental.message.kafka.mockproducer;

import org.apache.kafka.clients.producer.MockProducer;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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

        //构建自动flush的生产者
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
}
