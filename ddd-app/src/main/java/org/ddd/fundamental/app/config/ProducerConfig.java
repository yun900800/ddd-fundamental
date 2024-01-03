package org.ddd.fundamental.app.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ProducerConfig {

    private  static final Logger LOGGER = LoggerFactory.getLogger(ProducerConfig.class);
    public static final String MESSAGE_QUEUE = "pizza-message-queue";

    private static final String QUEUE_MESSAGES_DLQ = "pizza-message-queue.dlq";

    private static final String MESSAGE_QUEUE1 = "pizza-message-queue1";

    private static final String MESSAGE_QUEUE2 = "pizza-message-queue2";
    private static final String PUB_SUB_TOPIC = "notification-topic";
    private static final String PUB_SUB_EMAIL_QUEUE = "email-queue";
    private static final String PUB_SUB_TEXT_QUEUE = "text-queue";

    public static final String PUB_SUB_TEXT_ERROR_QUEUE = "text-error-queue";
    public static final String PUB_SUB_TEXT_ERROR_QUEUE_DLQ = "text-error-queue.dlq";

    public static final String QUEUE_PARKING_LOT = PUB_SUB_TEXT_ERROR_QUEUE + ".parking-lot";
    public static final String EXCHANGE_PARKING_LOT = PUB_SUB_TEXT_ERROR_QUEUE + "exchange.parking-lot";

    @Bean
    public Queue queue() {
        return QueueBuilder.durable(MESSAGE_QUEUE)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", QUEUE_MESSAGES_DLQ)
                .build();
    }
    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable(QUEUE_MESSAGES_DLQ).build();
    }

    @Bean
    public Queue queue1() {
        return new Queue(MESSAGE_QUEUE1);
    }

    @Bean
    public Queue queue2() {
        return new Queue(MESSAGE_QUEUE2);
    }

    @Bean
    public Queue emailQueue() {
        return new Queue(PUB_SUB_EMAIL_QUEUE);
    }

    @Bean
    public Queue textErrorQueue() {
        return QueueBuilder.durable(PUB_SUB_TEXT_ERROR_QUEUE)
                .withArgument("x-dead-letter-exchange", PUB_SUB_TEXT_ERROR_QUEUE_DLQ)
                .build();
    }



    @Bean
    public Queue textErrorDlqQueue() {
        return QueueBuilder.durable(PUB_SUB_TEXT_ERROR_QUEUE_DLQ).build();
    }

    @Bean
    FanoutExchange deadLetterExchange() {
        return new FanoutExchange(PUB_SUB_TEXT_ERROR_QUEUE_DLQ);
    }
    @Bean
    Binding deadLetterBinding() {
        return BindingBuilder.bind(textErrorDlqQueue()).to(deadLetterExchange());
    }
    @Bean
    public Binding textErrorBinding() {
        return BindingBuilder.bind(textErrorQueue()).to(exchange()).with("notification1");
    }

    @Bean
    FanoutExchange parkingLotExchange() {
        return new FanoutExchange(EXCHANGE_PARKING_LOT);
    }

    @Bean
    Queue parkingLotQueue() {
        return QueueBuilder.durable(QUEUE_PARKING_LOT).build();
    }

    @Bean
    Binding parkingLotBinding() {
        return BindingBuilder.bind(parkingLotQueue()).to(parkingLotExchange());
    }

    @Bean
    public Queue textQueue() {
        return new Queue(PUB_SUB_TEXT_QUEUE);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(PUB_SUB_TOPIC);
    }

    @Bean
    public Binding emailBinding(Queue emailQueue, TopicExchange exchange) {
        return BindingBuilder.bind(emailQueue).to(exchange).with("notification");
    }

    @Bean
    public Binding textBinding(Queue textQueue, TopicExchange exchange) {
        return BindingBuilder.bind(textQueue).to(exchange).with("notification");
    }

    @Bean
    public Publisher publisher(RabbitTemplate rabbitTemplate) {
        return new Publisher(rabbitTemplate, MESSAGE_QUEUE, PUB_SUB_TOPIC);
    }
}
