package org.ddd.fundamental.app.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProducerConfig {
    private static final String MESSAGE_QUEUE = "pizza-message-queue";

    private static final String MESSAGE_QUEUE1 = "pizza-message-queue1";

    private static final String MESSAGE_QUEUE2 = "pizza-message-queue2";
    private static final String PUB_SUB_TOPIC = "notification-topic";
    private static final String PUB_SUB_EMAIL_QUEUE = "email-queue";
    private static final String PUB_SUB_TEXT_QUEUE = "text-queue";

    @Bean
    public Queue queue() {
        return new Queue(MESSAGE_QUEUE);
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
