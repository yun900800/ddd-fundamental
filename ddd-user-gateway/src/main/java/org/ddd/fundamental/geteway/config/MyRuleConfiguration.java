package org.ddd.fundamental.geteway.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyRuleConfiguration {
    @Bean
    public IRule myRule(){
        return new RoundRobinRule();
    }

}
