package org.ddd.fundamental.spring.beans.annotatedbeandefinitionreader;

import org.ddd.fundamental.spring.bean.MyBean;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;

public class AnnotatedBeanDefinitionReaderExample {

    public static void annotatedRead() {
        // 创建一个 AnnotationConfigApplicationContext
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();

        // 创建 AnnotatedBeanDefinitionReader 并将其关联到容器
        AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(factory);

        // 使用 AnnotatedBeanDefinitionReader 注册Bean对象
        reader.registerBean(MyBean.class);

        // 获取并打印 MyBean
        System.out.println("MyBean = " + factory.getBean(MyBean.class));
    }
}
