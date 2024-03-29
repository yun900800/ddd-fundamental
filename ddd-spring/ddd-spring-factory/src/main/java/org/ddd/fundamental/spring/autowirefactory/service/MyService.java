package org.ddd.fundamental.spring.autowirefactory.service;

import org.ddd.fundamental.spring.autowirefactory.repository.MyRepository;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class MyService implements BeanNameAware, InitializingBean, DisposableBean {

    @Autowired
    private MyRepository myRepository;

    @Value("${java.home}")
    private String javaHome;

    @Override
    public void setBeanName(String name) {
        System.out.println("MyService.setBeanName方法被调用了");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("MyService.afterPropertiesSet方法被调用了");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("MyService.destroy方法被调用了");
    }

    public void setJavaHome(String javaHome) {
        this.javaHome = javaHome;
    }

    @Override
    public String toString() {
        return "MyService{" +
                "myRepository=" + myRepository +
                ", javaHome='" + javaHome + '\'' +
                '}';
    }
}
