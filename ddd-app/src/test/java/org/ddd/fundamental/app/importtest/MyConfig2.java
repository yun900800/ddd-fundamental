package org.ddd.fundamental.app.importtest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig2 {
    @Bean
    AppBean appBean () {
        return new AppBean("from config 2");
    }
}
