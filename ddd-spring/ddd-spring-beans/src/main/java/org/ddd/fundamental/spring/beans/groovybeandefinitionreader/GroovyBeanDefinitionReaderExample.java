package org.ddd.fundamental.spring.beans.groovybeandefinitionreader;

import org.ddd.fundamental.spring.bean.MyService;
import org.springframework.beans.factory.groovy.GroovyBeanDefinitionReader;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.core.io.ClassPathResource;

public class GroovyBeanDefinitionReaderExample {

    public static void groovyRead() {
        // 创建一个 Spring IOC 容器
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();

        // 创建一个 GroovyBeanDefinitionReader
        GroovyBeanDefinitionReader reader = new GroovyBeanDefinitionReader(factory);

        // 加载 Groovy 文件并注册 Bean 定义
        reader.loadBeanDefinitions(new ClassPathResource("my-beans.groovy"));

        // 获取MyService
        MyService myService = factory.getBean(MyService.class);
        // 打印消息
        myService.showMessage();
    }
}
