package org.ddd.fundamental.spring.core.registerbean;

import org.ddd.fundamental.spring.core.bean.MyBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


/**
 * 这里只会加载必须的几个包
 */
public class RegisterBeanDefinitionApplication {
    public static void load() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        // 注册Bean
        context.register(MyBean.class);
        // 扫描包
        context.scan("org.ddd.fundamental.spring.core.registerbean");
        // 打印Bean定义
        for (String beanDefinitionName : context.getBeanDefinitionNames()) {
            System.out.println("beanDefinitionName = " + beanDefinitionName);
        }
    }
}
