package org.ddd.fundamental.spring.core.resolvedependency.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("org.ddd.fundamental.spring.core.resolvedependency")
@PropertySource("classpath:application.properties")
public class MyConfiguration {
}
