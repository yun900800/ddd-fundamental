package org.ddd.fundamental.spring.configurablelistable.config;

import org.ddd.fundamental.spring.configurablelistable.service.MyService;
import org.ddd.fundamental.spring.configurablelistable.service.MyServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfiguration {

    @Bean
    public MyService myService() {
        return new MyServiceImpl();
    }
}
