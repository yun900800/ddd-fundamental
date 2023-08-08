package org.ddd.fundamental.app.listener;

import org.ddd.fundamental.app.config.ProducerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

import static org.ddd.fundamental.app.config.ProducerConfig.QUEUE_PARKING_LOT;

@Configuration
public class TextListener {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public static final int MAX_RETRIES_COUNT = 5;

    public static final String HEADER_X_RETRIES_COUNT = "x-retries-count";

    private  static final Logger LOGGER = LoggerFactory.getLogger(TextListener.class);
    @RabbitListener(queues = "text-queue")
    public void consume(Message message) throws Exception{
        String msg = new String(message.getBody());
        LOGGER.info("message:{}",msg);

    }
    @RabbitListener(queues = "text-error-queue")
    public void consume1(Message message) throws Exception{
        String msg = new String(message.getBody());
        LOGGER.info("consume1 message:{}",msg);
        //throw new RuntimeException("error handler!");
    }

    @RabbitListener(queues = "text-error-queue.dlq")
    public void consume2(Message failedMessage) throws Exception{
        //version1 直接处理消息
//        String msg = new String(failedMessage.getBody());
//        LOGGER.info("consume2 message:{}",msg);
        // version2 重复发送一直到成功
//        LOGGER.info("Received failed message, requeueing: {}", failedMessage.toString());
//        rabbitTemplate.send("notification-topic",
//                failedMessage.getMessageProperties().getReceivedRoutingKey(), failedMessage);

        //version3 设置一个限定的值
        Integer retriesCnt = (Integer) failedMessage.getMessageProperties()
                .getHeaders().get(HEADER_X_RETRIES_COUNT);
        if (retriesCnt == null)  {
            retriesCnt = 1;
        };
        if (retriesCnt > MAX_RETRIES_COUNT) {
            LOGGER.info("Discarding message");
            LOGGER.info("Sending message to the parking lot queue");
            rabbitTemplate.send(ProducerConfig.EXCHANGE_PARKING_LOT,
                    failedMessage.getMessageProperties().getReceivedRoutingKey(), failedMessage);
            return;
        }
        if (retriesCnt <=5 ) {
            TimeUnit.MILLISECONDS.sleep(500);
        }
        LOGGER.info("Retrying message for the {} time", retriesCnt);
        failedMessage.getMessageProperties()
                .getHeaders().put(HEADER_X_RETRIES_COUNT, ++retriesCnt);
        rabbitTemplate.send("notification-topic",
                failedMessage.getMessageProperties().getReceivedRoutingKey(), failedMessage);

    }

    @RabbitListener(queues = QUEUE_PARKING_LOT)
    public void processParkingLotQueue(Message failedMessage) {
        LOGGER.info("Received message in parking lot queue");
        // Save to DB or send a notification.
    }
}
