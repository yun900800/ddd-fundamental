package org.ddd.fundamental.app.importtest;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(MyImportSelector.class)
public class MainConfig {

    @Bean
    ClientBean clientBean () {
        return new ClientBean();
    }

    @Bean
    AppBean appBean () {
        System.out.println("appBean");
        return new AppBean("from main config ");
    }
}
