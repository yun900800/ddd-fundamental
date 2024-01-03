package org.ddd.fundamental.app.importtest.importselector;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(MyImportSelector.class)
public class MainConfig1 {
    @Bean
    ClientBean clientBean () {
        return new ClientBean();
    }

    @Bean
    @ConditionalOnMissingBean
    AppBean appBean() {
        return new AppBean();
    }
}
