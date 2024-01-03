package org.ddd.fundamental.spring.beans.classpathscan;

import org.ddd.fundamental.spring.bean.controller.MyController;
import org.ddd.fundamental.spring.bean.repository.MyRepository;
import org.ddd.fundamental.spring.bean.service.MyService;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

public class ClassPathBeanDefinitionScannerExample {

    public static void scanBeans() {
        // 创建一个 AnnotationConfigApplicationContext
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();

        // 创建 ClassPathBeanDefinitionScanner 并将其关联到容器
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(factory);

        // 使用 ClassPathBeanDefinitionScanner的scan方法扫描Bean对象
        scanner.scan("org.ddd.fundamental.spring.bean");

        System.out.println("MyController = " + factory.getBean(MyController.class));
        System.out.println("MyService = " + factory.getBean(MyService.class));
        System.out.println("MyRepository = " + factory.getBean(MyRepository.class));
    }
}
