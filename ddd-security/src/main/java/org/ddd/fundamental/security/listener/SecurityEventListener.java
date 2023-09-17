package org.ddd.fundamental.security.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.stereotype.Component;

@Component
public class SecurityEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityEventListener.class);

    public void handleEvent(AbstractAuthenticationEvent e) {
        LOGGER.info("event:{}",e);
    }
}
