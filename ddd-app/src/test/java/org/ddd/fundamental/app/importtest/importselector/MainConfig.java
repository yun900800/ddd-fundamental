package org.ddd.fundamental.app.importtest.importselector;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainConfig {
    @Bean
    ClientBean clientBean () {
        return new ClientBean();
    }

    @Bean
    AppBean appBean() {
        return new AppBean();
    }
}
