package org.ddd.fundamental.app.listener;


import org.ddd.fundamental.share.domain.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@Service
public class EmailListener {
    private  static final Logger LOGGER = LoggerFactory.getLogger(EmailListener.class);
    @RabbitListener(queues = "email-queue")
    public void consume(Message message) throws Exception{
        String msg = new String(message.getBody());
        LOGGER.info("message:{}",msg);
    }


}
