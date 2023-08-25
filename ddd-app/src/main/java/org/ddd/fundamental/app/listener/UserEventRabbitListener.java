package org.ddd.fundamental.app.listener;

import org.ddd.fundamental.app.model.UserEvent;
import org.ddd.fundamental.share.domain.Service;
import org.ddd.fundamental.share.domain.bus.event.DomainEventSubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;

@DomainEventSubscriber({UserEvent.class})
@Service
public class UserEventRabbitListener {

    private  static final Logger LOGGER = LoggerFactory.getLogger(UserEventRabbitListener.class);
    @EventListener
    public void on(UserEvent event){
        LOGGER.info("UserEventRabbitListener UserEvent:{}",event);
    }
}
