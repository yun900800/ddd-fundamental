package org.ddd.fundamental.app.listener;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsumerConfig {
    private static final String MESSAGE_QUEUE = "pizza-message-queue";

    private static final String QUEUE_MESSAGES_DLQ = "pizza-message-queue.dlq";

    private static final String MESSAGE_QUEUE1 = "pizza-message-queue1";

    private static final String MESSAGE_QUEUE2 = "pizza-message-queue2";

    public static final String PUB_SUB_TEXT_ERROR_QUEUE = "text-error-queue";
    public static final String PUB_SUB_TEXT_ERROR_QUEUE_DLQ = "text-error-queue.dlq";

//    @Bean
//    public Queue queueConsume() {
//        return new Queue(MESSAGE_QUEUE);
//    }

    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(new String[]{
                MESSAGE_QUEUE, MESSAGE_QUEUE1, MESSAGE_QUEUE2,
                QUEUE_MESSAGES_DLQ
        });
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    public OrderConsumer consumer() {
        return new OrderConsumer();
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(OrderConsumer consumer) {
        MessageListenerAdapter adapter =
                new MessageListenerAdapter(consumer, "receiveOrder");
        adapter.addQueueOrTagToMethodName("pizza-message-queue1","receiveOrder1");
        adapter.addQueueOrTagToMethodName("pizza-message-queue2","receiveOrder2");
        return adapter;
    }
}
