package org.ddd.fundamental.geteway;

import com.netflix.hystrix.contrib.javanica.aop.aspectj.HystrixCommandAspect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Hello world!
 *
 */

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
@EnableHystrix
//@EnableTransactionManagement(
//        order=Ordered.LOWEST_PRECEDENCE,
//        mode= AdviceMode.ASPECTJ)
public class UserGatewayApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(UserGatewayApp.class,args);
    }

    @Bean
    @Primary
    @Order(value= Ordered.HIGHEST_PRECEDENCE)
    public HystrixCommandAspect hystrixAspect() {
        return new HystrixCommandAspect();
    }
}
