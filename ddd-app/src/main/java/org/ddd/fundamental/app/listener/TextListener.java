package org.ddd.fundamental.app.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TextListener {
    private  static final Logger LOGGER = LoggerFactory.getLogger(TextListener.class);
    @RabbitListener(queues = "text-queue")
    public void consume(Message message) throws Exception{
        String msg = new String(message.getBody());
        LOGGER.info("message:{}",msg);

    }
}
