package org.ddd.fundamental.app.importtest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig1 {
    @Bean
    AppBean appBean () {
        System.out.println("MyConfig1:appBean");
        return new AppBean("from config 1");
    }
}
