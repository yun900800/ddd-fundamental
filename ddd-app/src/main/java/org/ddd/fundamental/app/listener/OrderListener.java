package org.ddd.fundamental.app.listener;

import org.ddd.fundamental.share.domain.Service;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@Service
public class OrderListener {

    @RabbitListener(queues = "pizza-message-queue")
    public void receiveOrder(String message) {
        System.out.printf("receiveOrder not in listenerContainerOrder received: %s%n", message);
        throw new RuntimeException("error msg");
    }

    @RabbitListener(queues = "pizza-message-queue.dlq")
    public void errorOrder(String message) {
        System.out.printf("error message not in listenerContainerOrder received: %s%n", message);
    }
}
