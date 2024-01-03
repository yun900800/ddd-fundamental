package org.ddd.fundamental.spring.core.getbean;

import org.ddd.fundamental.spring.core.getbean.config.MyConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class GetBeanApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfiguration.class);
        System.out.println("myServiceA = " + context.getBean("myServiceA"));
        System.out.println("myServiceB = " + context.getBean("myServiceB"));
    }

    public static void load(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfiguration.class);
        System.out.println("myServiceA = " + context.getBean("myServiceA"));
        System.out.println("myServiceB = " + context.getBean("myServiceB"));
    }
}
