package org.ddd.fundamental.spring.configurable.config;

import org.ddd.fundamental.spring.configurable.service.MyService;
import org.ddd.fundamental.spring.configurable.service.MyServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfiguration {
    @Bean
    public MyService myService() {
        return new MyServiceImpl();
    }
}
