package org.ddd.fundamental.visa.securityCheck.infrastructure.jms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ddd.fundamental.visa.securityCheck.infrastructure.jms.entity.SecurityCheckMessageEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class JmsSecurityCheckProducer {
    private final JmsTemplate jmsTemplate;


    public JmsSecurityCheckProducer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }


    public void send(SecurityCheckMessageEntity messageEntity, String destination){
        try {
            jmsTemplate.convertAndSend(destination,  new ObjectMapper().writeValueAsString(messageEntity));  ;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
