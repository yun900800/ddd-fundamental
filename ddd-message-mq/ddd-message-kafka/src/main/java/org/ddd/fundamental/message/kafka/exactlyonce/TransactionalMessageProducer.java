package org.ddd.fundamental.message.kafka.exactlyonce;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;

import java.util.stream.Stream;

public class TransactionalMessageProducer {

    private static final String DATA_MESSAGE_1 = "Put any space separated data here for count";
    private static final String DATA_MESSAGE_2 = "Output will contain count of every word in the message";

    private static final String DATA_MESSAGE_3 = "Output will contain count of every word in the message";

    private static final String DATA_MESSAGE_4 = "I want to test kafka send message enabled idempotence with transaction id";

    public static void producerSendSentence(KafkaProducer<String, String> producer) {
        producer.initTransactions();
        try {

            //开启事务，并且在事务中发送两个句子的数据
            producer.beginTransaction();
            System.out.println("send start");
            Stream.of(DATA_MESSAGE_1, DATA_MESSAGE_2,DATA_MESSAGE_3,DATA_MESSAGE_4)
                    .forEach(s -> producer.send(new ProducerRecord<String, String>("input", null, s)));
            System.out.println("send finished");
            producer.commitTransaction();

        } catch (KafkaException e) {
            producer.abortTransaction();
        }

    }

}
