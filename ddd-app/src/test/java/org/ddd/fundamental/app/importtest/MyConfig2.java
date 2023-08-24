package org.ddd.fundamental.app.importtest;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig2 {
    @Bean
    //@ConditionalOnMissingBean
    AppBean appBean() {
        System.out.println("MyConfig2:appBean");
        return new AppBean("from config 2");
    }
}
