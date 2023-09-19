package org.ddd.fundamental.conditional.config;

import org.ddd.fundamental.conditional.on.annotation.AttributeAnnotation;
import org.ddd.fundamental.conditional.on.bean.AppBean;
import org.ddd.fundamental.conditional.on.bean.SpringService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    @Bean
    //@ConditionalOnBean(value = RequiredBean.class)
    //@ConditionalOnBean(name = {"requiredBean","anotherRequiredBean"})
    @ConditionalOnBean(type = "org.ddd.fundamental.conditional.on.bean.RequiredBean")
    //@MyAnnotation
    //下面这个注解有问题
    //@ConditionalOnBean(annotation = {AttributeAnnotation.class})
    public SpringService springService() {
        return new SpringService();
    }
}
