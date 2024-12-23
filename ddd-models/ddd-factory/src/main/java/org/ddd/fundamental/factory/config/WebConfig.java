package org.ddd.fundamental.factory.config;

import org.ddd.fundamental.tenant.TenantInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 注册一个拦截器
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //This tells the InterceptorRegistry to register the provided class as the Interceptor
        registry.addInterceptor(new TenantInterceptor());
    }
}
