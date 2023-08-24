package org.ddd.fundamental.app.importtest.importclass;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

public interface ServiceInterface {
    String test();
}

class ServiceA implements ServiceInterface {

    @Override
    public String test() {
        return "ServiceA";
    }
}

class ServiceB implements ServiceInterface {

    @Override
    public String test() {
        return "ServiceB";
    }
}

@Import(ConfigB.class)
@Configuration
class ConfigA {
    @Bean
    @ConditionalOnMissingBean
    public ServiceInterface getServiceA() {
        return new ServiceA();
    }
}

@Configuration
class ConfigB {
    @Bean
    @ConditionalOnMissingBean
    public ServiceInterface getServiceB() {
        return new ServiceB();
    }
}

@Configuration
@Import(ConfigD.class)
class ConfigC {
    @Bean
    public ServiceInterface getServiceA() {
        return new ServiceA();
    }
}
@Configuration
class ConfigD {
    @Bean
    public ServiceInterface getServiceB() {
        return new ServiceB();
    }
}

@Configuration
@Import(ConfigF.class)
class ConfigE {
    @Bean
    @ConditionalOnMissingBean
    public ServiceInterface getServiceA() {
        return new ServiceA();
    }
}
@Configuration
class ConfigF {
    @Bean
    public ServiceInterface getServiceB() {
        return new ServiceB();
    }
}


