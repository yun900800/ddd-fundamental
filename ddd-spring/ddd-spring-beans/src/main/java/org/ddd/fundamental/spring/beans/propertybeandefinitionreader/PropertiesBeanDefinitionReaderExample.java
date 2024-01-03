package org.ddd.fundamental.spring.beans.propertybeandefinitionreader;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;

public class PropertiesBeanDefinitionReaderExample {

    public static void propertyRead() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        PropertiesBeanDefinitionReader reader = new PropertiesBeanDefinitionReader(beanFactory);

        // 从properties文件加载bean定义
        reader.loadBeanDefinitions(new ClassPathResource("bean-definitions.properties"));

        // 获取bean
        System.out.println("myBean = " + beanFactory.getBean("myBean"));
        System.out.println("myBean = " + beanFactory.getBean("myBean"));
    }
}
