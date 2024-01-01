package org.ddd.fundamental.spring.beans.definitionholder;

import org.ddd.fundamental.spring.bean.MyBean;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;

public class BeanDefinitionHolderExample {

    public static Object registerBeanDefinitionHolder() {
        // 创建一个 DefaultListableBeanFactory，它是 BeanDefinitionRegistry 的一个实现
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 创建一个新的 BeanDefinition 对象
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(MyBean.class);

        // Bean名称
        String beanName = "myBean";
        // 设置别名（aliases）
        String[] aliases = {"myBeanX", "myBeanY"};
        // 创建一个 BeanDefinitionHolder，将 BeanDefinition 与名称关联起来
        BeanDefinitionHolder beanDefinitionHolder = new BeanDefinitionHolder(beanDefinition, beanName, aliases);

        // 使用 BeanDefinitionReaderUtils 注册 BeanDefinitionHolder
        BeanDefinitionReaderUtils.registerBeanDefinition(beanDefinitionHolder, beanFactory);


        return beanFactory;
    }
}
