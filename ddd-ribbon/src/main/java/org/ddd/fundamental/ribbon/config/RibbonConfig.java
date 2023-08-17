package org.ddd.fundamental.ribbon.config;

import org.ddd.fundamental.ribbon.intercepter.SecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RibbonConfig {

    @Autowired
    private SecurityInterceptor securityInterceptor;

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        List<ClientHttpRequestInterceptor> interceptorList =  restTemplate.getInterceptors();
        if (CollectionUtils.isEmpty(interceptorList)) {
            interceptorList = new ArrayList<>();
        }
        interceptorList.add(securityInterceptor);
        restTemplate.setInterceptors(interceptorList);
        return restTemplate;
    }
}
