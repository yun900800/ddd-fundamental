package org.ddd.fundamental.configuration;

import org.apache.activemq.artemis.jms.client.ActiveMQXAConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jta.atomikos.AtomikosConnectionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

@Configuration
@EnableJms
public class JMSConfiguration {

    @Value("${jms.url}")
    String jmsUrl;

    @Bean
    public JmsTemplate jmsTemplate() throws Throwable {
        return new JmsTemplate(connectionFactory());
    }

    @Bean(initMethod = "init", destroyMethod = "close")
    public ConnectionFactory connectionFactory() throws JMSException {
        ActiveMQXAConnectionFactory activeMQXAConnectionFactory = new
                ActiveMQXAConnectionFactory();
        activeMQXAConnectionFactory.setBrokerURL(jmsUrl);
        activeMQXAConnectionFactory.setUser("admin");
        activeMQXAConnectionFactory.setPassword("admin");
        AtomikosConnectionFactoryBean atomikosConnectionFactoryBean = new AtomikosConnectionFactoryBean();
        atomikosConnectionFactoryBean.setUniqueResourceName("xamq");
        atomikosConnectionFactoryBean.setLocalTransactionMode(false);
        atomikosConnectionFactoryBean.setXaConnectionFactory(activeMQXAConnectionFactory);
        return atomikosConnectionFactoryBean;
    }


}
